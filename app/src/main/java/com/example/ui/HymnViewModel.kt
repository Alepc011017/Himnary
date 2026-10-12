package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.Hymn
import com.example.data.HymnCategory
import com.example.data.HymnRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ReadingTheme {
    LIGHT, PARCHMENT, DARK
}

enum class ReaderFont {
    SERIF, SANS
}

enum class HymnViewMode {
    GROUPED_BY_CATEGORY,
    ALL_HYMNS
}

sealed class Screen {
    object List : Screen()
    data class Detail(val hymnId: Int) : Screen()
    data class Edit(val hymnId: Int?) : Screen() // null means new hymn
    object Favorites : Screen()
}

data class CategoryGroup(
    val category: HymnCategory,
    val hymns: List<Hymn>,
    val isCollapsed: Boolean = false
)

data class HymnUiState(
    val hymns: List<Hymn> = emptyList(),
    val filteredHymns: List<Hymn> = emptyList(),
    val favoriteHymns: List<Hymn> = emptyList(),
    val categories: List<HymnCategory> = emptyList(),
    val categoryGroups: List<CategoryGroup> = emptyList(),
    val viewMode: HymnViewMode = HymnViewMode.GROUPED_BY_CATEGORY,
    val collapsedCategories: Set<String> = emptySet(),
    val currentHymn: Hymn? = null,
    val searchQuery: String = "",
    val selectedCategory: String = "Todos",
    val readingFontSize: Float = 18f,
    val readingTheme: ReadingTheme = ReadingTheme.PARCHMENT,
    val readerFont: ReaderFont = ReaderFont.SERIF,
    val isAutoScrolling: Boolean = false,
    val autoScrollSpeed: Float = 1f,
    val isFullScreenReading: Boolean = false,
    val nextSuggestedNumber: Int = 1,
    val userFeedbackMessage: String? = null
)

class HymnViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HymnRepository

    private val _screenState = MutableStateFlow<Screen>(Screen.List)
    val screenState: StateFlow<Screen> = _screenState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow("Todos")
    private val _viewMode = MutableStateFlow(HymnViewMode.GROUPED_BY_CATEGORY)
    private val _collapsedCategories = MutableStateFlow<Set<String>>(emptySet())
    private val _readingFontSize = MutableStateFlow(18f)
    private val _readingTheme = MutableStateFlow(ReadingTheme.PARCHMENT)
    private val _readerFont = MutableStateFlow(ReaderFont.SERIF)
    private val _isAutoScrolling = MutableStateFlow(false)
    private val _autoScrollSpeed = MutableStateFlow(1f)
    private val _isFullScreen = MutableStateFlow(false)
    private val _currentHymnId = MutableStateFlow<Int?>(null)
    private val _feedbackMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HymnUiState>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = HymnRepository(database.hymnDao(), database.categoryDao())

        // Ensure database has initial hymns and categories
        viewModelScope.launch {
            repository.checkAndSeedInitialData()
        }

        uiState = combine(
            repository.allHymns,
            repository.favoriteHymns,
            repository.allCategories,
            _searchQuery,
            _selectedCategory,
            _viewMode,
            _collapsedCategories,
            _readingFontSize,
            _readingTheme,
            _readerFont,
            _currentHymnId,
            _isAutoScrolling,
            _autoScrollSpeed,
            _isFullScreen,
            _feedbackMessage
        ) { values ->
            @Suppress("UNCHECKED_CAST")
            val allHymns = values[0] as List<Hymn>
            @Suppress("UNCHECKED_CAST")
            val favHymns = values[1] as List<Hymn>
            @Suppress("UNCHECKED_CAST")
            val allCategories = values[2] as List<HymnCategory>
            val query = values[3] as String
            val categoryFilter = values[4] as String
            val viewMode = values[5] as HymnViewMode
            @Suppress("UNCHECKED_CAST")
            val collapsedCats = values[6] as Set<String>
            val fontSize = values[7] as Float
            val readingTheme = values[8] as ReadingTheme
            val readerFont = values[9] as ReaderFont
            val currentHymnId = values[10] as Int?
            val isAutoScrolling = values[11] as Boolean
            val autoScrollSpeed = values[12] as Float
            val isFullScreen = values[13] as Boolean
            val feedback = values[14] as String?

            // Filter hymns by query and quick category chip
            val filtered = allHymns.filter { hymn ->
                val matchesCategory = when (categoryFilter) {
                    "Todos" -> true
                    "Favoritos" -> hymn.isFavorite
                    "Sin Letra" -> !hymn.hasText
                    else -> hymn.hasCategory(categoryFilter)
                }

                val matchesQuery = if (query.isBlank()) {
                    true
                } else {
                    val cleanQuery = query.trim()
                    hymn.number.toString().contains(cleanQuery) ||
                    hymn.title.contains(cleanQuery, ignoreCase = true) ||
                    hymn.lyrics.contains(cleanQuery, ignoreCase = true) ||
                    hymn.chorus.contains(cleanQuery, ignoreCase = true) ||
                    hymn.author.contains(cleanQuery, ignoreCase = true) ||
                    hymn.getCategories().any { it.contains(cleanQuery, ignoreCase = true) }
                }

                matchesCategory && matchesQuery
            }

            // Build Groups by Category
            val categoryGroups = mutableListOf<CategoryGroup>()
            val processedCategoryNames = mutableSetOf<String>()

            // 1. First add defined categories from DB
            for (cat in allCategories) {
                processedCategoryNames.add(cat.name.lowercase())
                val hymnsInCat = filtered.filter { it.hasCategory(cat.name) }
                // Only show category in grouped view if it has hymns (or if no search filter is active)
                if (hymnsInCat.isNotEmpty() || (query.isBlank() && categoryFilter == "Todos")) {
                    categoryGroups.add(
                        CategoryGroup(
                            category = cat,
                            hymns = hymnsInCat,
                            isCollapsed = collapsedCats.contains(cat.name)
                        )
                    )
                }
            }

            // 2. Discover any extra custom categories written in hymns that are not yet in allCategories
            val extraCategoryNames = filtered.flatMap { it.getCategories() }
                .distinct()
                .filter { it.lowercase() !in processedCategoryNames }

            for (extraName in extraCategoryNames) {
                val hymnsInExtra = filtered.filter { it.hasCategory(extraName) }
                if (hymnsInExtra.isNotEmpty()) {
                    categoryGroups.add(
                        CategoryGroup(
                            category = HymnCategory(
                                name = extraName,
                                isDefault = false,
                                colorHex = "#7D2338"
                            ),
                            hymns = hymnsInExtra,
                            isCollapsed = collapsedCats.contains(extraName)
                        )
                    )
                }
            }

            val current = allHymns.find { it.id == currentHymnId }
            val nextNumber = (allHymns.maxOfOrNull { it.number } ?: 0) + 1

            HymnUiState(
                hymns = allHymns,
                filteredHymns = filtered,
                favoriteHymns = favHymns,
                categories = allCategories,
                categoryGroups = categoryGroups,
                viewMode = viewMode,
                collapsedCategories = collapsedCats,
                currentHymn = current,
                searchQuery = query,
                selectedCategory = categoryFilter,
                readingFontSize = fontSize,
                readingTheme = readingTheme,
                readerFont = readerFont,
                isAutoScrolling = isAutoScrolling,
                autoScrollSpeed = autoScrollSpeed,
                isFullScreenReading = isFullScreen,
                nextSuggestedNumber = nextNumber,
                userFeedbackMessage = feedback
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HymnUiState()
        )
    }

    fun navigateTo(screen: Screen) {
        _isAutoScrolling.value = false
        if (screen is Screen.Detail) {
            _currentHymnId.value = screen.hymnId
        }
        _screenState.value = screen
    }

    fun navigateBack() {
        _isAutoScrolling.value = false
        _screenState.value = when (_screenState.value) {
            is Screen.Detail -> Screen.List
            is Screen.Edit -> {
                val currentId = _currentHymnId.value
                if (currentId != null) Screen.Detail(currentId) else Screen.List
            }
            is Screen.Favorites -> Screen.List
            Screen.List -> Screen.List
        }
    }

    fun setViewMode(mode: HymnViewMode) {
        _viewMode.value = mode
    }

    fun toggleCategoryCollapsed(categoryName: String) {
        val current = _collapsedCategories.value.toMutableSet()
        if (current.contains(categoryName)) {
            current.remove(categoryName)
        } else {
            current.add(categoryName)
        }
        _collapsedCategories.value = current
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun openHymnByNumber(number: Int): Boolean {
        val hymn = uiState.value.hymns.find { it.number == number }
        return if (hymn != null) {
            navigateTo(Screen.Detail(hymn.id))
            true
        } else {
            false
        }
    }

    fun toggleFavorite(hymn: Hymn) {
        viewModelScope.launch {
            repository.toggleFavorite(hymn.id, !hymn.isFavorite)
        }
    }

    fun createCategory(name: String, colorHex: String = "#1B365D") {
        val cleanName = name.trim()
        if (cleanName.isBlank()) return
        viewModelScope.launch {
            repository.addCategory(cleanName, colorHex)
            showFeedback("Categoría \"$cleanName\" creada")
        }
    }

    fun deleteCategory(category: HymnCategory) {
        viewModelScope.launch {
            repository.deleteCategory(category)
            showFeedback("Categoría \"${category.name}\" eliminada")
        }
    }

    fun saveHymn(
        id: Int = 0,
        number: Int,
        title: String,
        categories: List<String>,
        tone: String,
        author: String,
        chorus: String,
        lyrics: String,
        scriptureRef: String,
        notes: String
    ) {
        viewModelScope.launch {
            // Also ensure any newly typed category is stored in the database categories
            for (cat in categories) {
                if (cat.isNotBlank()) {
                    repository.addCategory(cat)
                }
            }

            val categoryString = if (categories.isEmpty()) "Alabanza" else categories.joinToString(", ")

            val hymn = Hymn(
                id = id,
                number = number,
                title = title.trim(),
                category = categoryString,
                tone = tone.trim(),
                author = author.trim(),
                chorus = chorus.trim(),
                lyrics = lyrics.trim(),
                scriptureRef = scriptureRef.trim(),
                notes = notes.trim(),
                updatedAt = System.currentTimeMillis()
            )

            if (id == 0) {
                val newId = repository.insertHymn(hymn).toInt()
                _currentHymnId.value = newId
                _screenState.value = Screen.Detail(newId)
                showFeedback("Himno #${hymn.number} guardado con éxito")
            } else {
                repository.updateHymn(hymn)
                _currentHymnId.value = id
                _screenState.value = Screen.Detail(id)
                showFeedback("Himno #${hymn.number} actualizado")
            }
        }
    }

    fun deleteHymn(hymn: Hymn) {
        viewModelScope.launch {
            repository.deleteHymn(hymn)
            _screenState.value = Screen.List
            showFeedback("Himno #${hymn.number} eliminado")
        }
    }

    fun resetToDefaults() {
        viewModelScope.launch {
            repository.resetToSampleData()
            showFeedback("Himnario restaurado a valores iniciales")
        }
    }

    fun navigateToAdjacentHymn(direction: Int) {
        val current = uiState.value.currentHymn ?: return
        val hymns = uiState.value.hymns
        val currentIndex = hymns.indexOfFirst { it.id == current.id }
        if (currentIndex != -1) {
            val targetIndex = currentIndex + direction
            if (targetIndex in hymns.indices) {
                val targetHymn = hymns[targetIndex]
                _currentHymnId.value = targetHymn.id
                _screenState.value = Screen.Detail(targetHymn.id)
            }
        }
    }

    fun setReadingTheme(theme: ReadingTheme) {
        _readingTheme.value = theme
    }

    fun setReaderFont(font: ReaderFont) {
        _readerFont.value = font
    }

    fun increaseFontSize() {
        if (_readingFontSize.value < 34f) {
            _readingFontSize.value += 2f
        }
    }

    fun decreaseFontSize() {
        if (_readingFontSize.value > 14f) {
            _readingFontSize.value -= 2f
        }
    }

    fun toggleAutoScroll() {
        _isAutoScrolling.value = !_isAutoScrolling.value
    }

    fun setAutoScrollSpeed(speed: Float) {
        _autoScrollSpeed.value = speed
    }

    fun toggleFullScreen() {
        _isFullScreen.value = !_isFullScreen.value
    }

    fun clearFeedback() {
        _feedbackMessage.value = null
    }

    private fun showFeedback(message: String) {
        _feedbackMessage.value = message
    }
}
