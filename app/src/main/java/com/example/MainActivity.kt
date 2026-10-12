package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.HymnViewModel
import com.example.ui.Screen
import com.example.ui.screens.HymnDetailScreen
import com.example.ui.screens.HymnEditScreen
import com.example.ui.screens.HymnListScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: HymnViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                HymnalApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun HymnalApp(viewModel: HymnViewModel) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // User feedback messages snackbar
    LaunchedEffect(uiState.userFeedbackMessage) {
        val msg = uiState.userFeedbackMessage
        if (msg != null) {
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            viewModel.clearFeedback()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = screenState,
                label = "ScreenTransition"
            ) { currentScreen ->
                when (currentScreen) {
                    is Screen.List -> {
                        HymnListScreen(
                            uiState = uiState,
                            onSearchChange = { viewModel.setSearchQuery(it) },
                            onCategorySelect = { viewModel.selectCategory(it) },
                            onViewModeChange = { viewModel.setViewMode(it) },
                            onToggleCategoryCollapse = { viewModel.toggleCategoryCollapsed(it) },
                            onHymnClick = { hymnId ->
                                viewModel.navigateTo(Screen.Detail(hymnId))
                            },
                            onFavoriteToggle = { hymn ->
                                viewModel.toggleFavorite(hymn)
                            },
                            onAddNewHymn = {
                                viewModel.navigateTo(Screen.Edit(null))
                            },
                            onCreateCategory = { name, colorHex ->
                                viewModel.createCategory(name, colorHex)
                            },
                            onDeleteCategory = { cat ->
                                viewModel.deleteCategory(cat)
                            },
                            onClearRecentHistory = {
                                viewModel.clearReadingHistory()
                            }
                        )
                    }

                    is Screen.Detail -> {
                        val hymn = uiState.hymns.find { it.id == currentScreen.hymnId }
                            ?: uiState.currentHymn
                        HymnDetailScreen(
                            hymn = hymn,
                            uiState = uiState,
                            onBack = { viewModel.navigateBack() },
                            onEditHymn = {
                                if (hymn != null) {
                                    viewModel.navigateTo(Screen.Edit(hymn.id))
                                }
                            },
                            onFavoriteToggle = {
                                if (hymn != null) viewModel.toggleFavorite(hymn)
                            },
                            onPreviousHymn = { viewModel.navigateToAdjacentHymn(-1) },
                            onNextHymn = { viewModel.navigateToAdjacentHymn(1) },
                            onSetFontSize = { viewModel.setFontSize(it) },
                            onResetFontSize = { viewModel.resetFontSize() },
                            onIncreaseFontSize = { viewModel.increaseFontSize() },
                            onDecreaseFontSize = { viewModel.decreaseFontSize() },
                            onSetReadingTheme = { viewModel.setReadingTheme(it) },
                            onSetReaderFont = { viewModel.setReaderFont(it) },
                            onToggleAutoScroll = { viewModel.toggleAutoScroll() },
                            onSetAutoScrollSpeed = { viewModel.setAutoScrollSpeed(it) }
                        )
                    }

                    is Screen.Edit -> {
                        val hymnToEdit = currentScreen.hymnId?.let { id ->
                            uiState.hymns.find { it.id == id }
                        }
                        HymnEditScreen(
                            hymnToEdit = hymnToEdit,
                            suggestedNextNumber = uiState.nextSuggestedNumber,
                            availableCategories = uiState.categories,
                            onBack = { viewModel.navigateBack() },
                            onCreateCategory = { name, colorHex ->
                                viewModel.createCategory(name, colorHex)
                            },
                            onSave = { id, number, title, categories, tone, author, chorus, lyrics, scriptureRef, notes ->
                                viewModel.saveHymn(
                                    id = id,
                                    number = number,
                                    title = title,
                                    categories = categories,
                                    tone = tone,
                                    author = author,
                                    chorus = chorus,
                                    lyrics = lyrics,
                                    scriptureRef = scriptureRef,
                                    notes = notes
                                )
                            },
                            onDelete = { hymn ->
                                viewModel.deleteHymn(hymn)
                            }
                        )
                    }

                    is Screen.Favorites -> {
                        HymnListScreen(
                            uiState = uiState.copy(selectedCategory = "Favoritos"),
                            onSearchChange = { viewModel.setSearchQuery(it) },
                            onCategorySelect = { viewModel.selectCategory(it) },
                            onViewModeChange = { viewModel.setViewMode(it) },
                            onToggleCategoryCollapse = { viewModel.toggleCategoryCollapsed(it) },
                            onHymnClick = { hymnId ->
                                viewModel.navigateTo(Screen.Detail(hymnId))
                            },
                            onFavoriteToggle = { hymn ->
                                viewModel.toggleFavorite(hymn)
                            },
                            onAddNewHymn = {
                                viewModel.navigateTo(Screen.Edit(null))
                            },
                            onCreateCategory = { name, colorHex ->
                                viewModel.createCategory(name, colorHex)
                            },
                            onDeleteCategory = { cat ->
                                viewModel.deleteCategory(cat)
                            },
                            onClearRecentHistory = {
                                viewModel.clearReadingHistory()
                            }
                        )
                    }
                }
            }
        }
    }
}
