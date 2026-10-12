package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.Hymn
import com.example.data.HymnCategory
import com.example.ui.AlphabeticalHymnEntry
import com.example.ui.AlphabeticalSection
import com.example.ui.CategoryGroup
import com.example.ui.HymnUiState
import com.example.ui.HymnViewMode
import com.example.ui.theme.HymnalBurgundy
import com.example.ui.theme.HymnalGold
import com.example.ui.theme.HymnalGoldDark
import com.example.ui.theme.HymnalGoldLight
import com.example.ui.theme.HymnalNavy
import com.example.ui.theme.HymnalNavyDark

@Composable
fun HymnListScreen(
    uiState: HymnUiState,
    onSearchChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    onViewModeChange: (HymnViewMode) -> Unit,
    onSelectAlphabetLetter: (Char?) -> Unit = {},
    onToggleCategoryCollapse: (String) -> Unit,
    onHymnClick: (Int) -> Unit,
    onFavoriteToggle: (Hymn) -> Unit,
    onAddNewHymn: () -> Unit,
    onCreateCategory: (String, String) -> Unit,
    onDeleteCategory: (HymnCategory) -> Unit,
    onClearRecentHistory: () -> Unit
) {
    var showQuickNumberDialog by remember { mutableStateOf(false) }
    var showManageCategoriesDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    val filterChips = remember(uiState.categories) {
        listOf("Todos", "Recientes", "Favoritos", "Sin Letra") + uiState.categories.map { it.name }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddNewHymn,
                modifier = Modifier.testTag("add_hymn_fab"),
                containerColor = HymnalGold,
                contentColor = Color.Black,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar Himno"
                    )
                },
                text = {
                    Text(
                        text = "Nuevo Himno",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Hero Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hymnal_banner),
                    contentDescription = "Banner del Himnario",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    HymnalNavyDark.copy(alpha = 0.5f),
                                    HymnalNavyDark.copy(alpha = 0.9f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = HymnalGoldLight,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "CANTEMOS NUETRA FE",
                                    style = MaterialTheme.typography.labelMedium,
                                    letterSpacing = 2.sp,
                                    color = HymnalGoldLight,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Alabanzas y Cánticos",
                                style = MaterialTheme.typography.headlineSmall,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        // Stats pills & Info
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = HymnalNavy.copy(alpha = 0.85f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "${uiState.hymns.size} himnos",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HymnalGoldLight,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = HymnalGold.copy(alpha = 0.25f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "${uiState.categories.size} cat.",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HymnalGoldLight,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Surface(
                                onClick = { showAboutDialog = true },
                                shape = RoundedCornerShape(12.dp),
                                color = HymnalGold.copy(alpha = 0.25f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold.copy(alpha = 0.5f)),
                                modifier = Modifier.testTag("open_about_dialog_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = "Información y Licencia Apache 2.0",
                                    tint = HymnalGoldLight,
                                    modifier = Modifier
                                        .padding(horizontal = 6.dp, vertical = 4.dp)
                                        .size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search Bar & Dialpad button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("search_hymns_input"),
                    placeholder = {
                        Text(
                            text = if (uiState.viewMode == HymnViewMode.ALPHABETICAL_DICTIONARY)
                                "Buscar por primer verso o letra a cantar..."
                            else
                                "Buscar por título, número, categoría...",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { onSearchChange("") },
                                modifier = Modifier.testTag("clear_search_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpiar búsqueda"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Quick number jump dialpad button
                Surface(
                    onClick = { showQuickNumberDialog = true },
                    modifier = Modifier
                        .size(54.dp)
                        .testTag("open_dialpad_button"),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    tonalElevation = 2.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Dialpad,
                            contentDescription = "Marcador numérico",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 3-Way View Mode Selector: Categorías | Numérico | Diccionario A-Z
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val isGrouped = uiState.viewMode == HymnViewMode.GROUPED_BY_CATEGORY
                    Surface(
                        onClick = { onViewModeChange(HymnViewMode.GROUPED_BY_CATEGORY) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("view_mode_grouped"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isGrouped) HymnalNavy else Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.GridView,
                                contentDescription = null,
                                tint = if (isGrouped) HymnalGoldLight else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Categorías",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isGrouped) FontWeight.Bold else FontWeight.Normal,
                                color = if (isGrouped) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    val isFlat = uiState.viewMode == HymnViewMode.ALL_HYMNS
                    Surface(
                        onClick = { onViewModeChange(HymnViewMode.ALL_HYMNS) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("view_mode_flat"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isFlat) HymnalNavy else Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatListNumbered,
                                contentDescription = null,
                                tint = if (isFlat) HymnalGoldLight else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Numérico",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isFlat) FontWeight.Bold else FontWeight.Normal,
                                color = if (isFlat) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    val isDict = uiState.viewMode == HymnViewMode.ALPHABETICAL_DICTIONARY
                    Surface(
                        onClick = { onViewModeChange(HymnViewMode.ALPHABETICAL_DICTIONARY) },
                        modifier = Modifier
                            .weight(1.1f)
                            .testTag("view_mode_dictionary"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isDict) HymnalNavy else Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.SortByAlpha,
                                contentDescription = null,
                                tint = if (isDict) HymnalGoldLight else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Diccionario A-Z",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isDict) FontWeight.Bold else FontWeight.Normal,
                                color = if (isDict) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Category Chips Row with "+ Categoría" Button (shown in general and category modes)
            if (uiState.viewMode != HymnViewMode.ALPHABETICAL_DICTIONARY) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        Surface(
                            onClick = { showManageCategoriesDialog = true },
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold),
                            modifier = Modifier.testTag("manage_categories_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "+ Categoría",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    items(filterChips) { category ->
                        val isSelected = uiState.selectedCategory.equals(category, ignoreCase = true)
                        val missingCount = remember(uiState.hymns) {
                            uiState.hymns.count { !it.hasText }
                        }

                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategorySelect(category) },
                            label = {
                                if (category == "Sin Letra" && missingCount > 0) {
                                    Text(text = "Sin Letra ($missingCount)")
                                } else if (category == "Recientes" && uiState.recentlyReadHymns.isNotEmpty()) {
                                    Text(text = "Recientes (${uiState.recentlyReadHymns.size})")
                                } else {
                                    Text(text = category)
                                }
                            },
                            leadingIcon = when (category) {
                                "Recientes" -> {
                                    {
                                        Icon(
                                            imageVector = Icons.Default.History,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = if (isSelected) HymnalGold else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                "Favoritos" -> {
                                    {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = if (isSelected) HymnalGold else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                "Sin Letra" -> {
                                    {
                                        Icon(
                                            imageVector = Icons.Default.EditNote,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = if (isSelected) HymnalGold else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                else -> null
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("filter_chip_$category")
                        )
                    }
                }
            }

            // RECENTLY READ HYMNS CAROUSEL SECTION
            if (uiState.recentlyReadHymns.isNotEmpty() && uiState.searchQuery.isBlank() &&
                uiState.selectedCategory == "Todos" && uiState.viewMode != HymnViewMode.ALPHABETICAL_DICTIONARY
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp)
                        .testTag("recently_read_section")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = HymnalGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Leídos Recientemente",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = HymnalGold.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${uiState.recentlyReadHymns.size}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = HymnalGoldDark,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        TextButton(
                            onClick = onClearRecentHistory,
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            modifier = Modifier.testTag("clear_recent_button")
                        ) {
                            Text("Limpiar", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(uiState.recentlyReadHymns, key = { "recent_${it.id}" }) { hymn ->
                            RecentHymnCard(
                                hymn = hymn,
                                onClick = { onHymnClick(hymn.id) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Main Content Area
            if (uiState.filteredHymns.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No se encontraron himnos",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (uiState.searchQuery.isNotEmpty())
                                    "No hay resultados para \"${uiState.searchQuery}\"."
                                else
                                    "No hay himnos en esta categoría o letra.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            ExtendedFloatingActionButton(
                                onClick = onAddNewHymn,
                                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                                text = { Text("Crear este himno") },
                                containerColor = HymnalGold,
                                contentColor = Color.Black
                            )
                        }
                    }
                }
            } else if (uiState.viewMode == HymnViewMode.ALPHABETICAL_DICTIONARY) {
                // ALPHABETICAL DICTIONARY VIEW (DICCIONARIO DE PRIMEROS VERSOS A-Z)
                AlphabeticalDictionaryView(
                    uiState = uiState,
                    onSelectLetter = onSelectAlphabetLetter,
                    onHymnClick = onHymnClick,
                    onFavoriteToggle = onFavoriteToggle
                )
            } else if (uiState.viewMode == HymnViewMode.GROUPED_BY_CATEGORY && uiState.selectedCategory == "Todos") {
                // GROUPED BY CATEGORY VIEW
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(uiState.categoryGroups, key = { it.category.name }) { group ->
                        CategoryGroupSection(
                            group = group,
                            onToggleCollapse = { onToggleCategoryCollapse(group.category.name) },
                            onHymnClick = onHymnClick,
                            onFavoriteToggle = onFavoriteToggle
                        )
                    }
                }
            } else {
                // FLAT NUMERICAL LIST VIEW
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.filteredHymns, key = { it.id }) { hymn ->
                        HymnCardItem(
                            hymn = hymn,
                            onClick = { onHymnClick(hymn.id) },
                            onFavoriteToggle = { onFavoriteToggle(hymn) }
                        )
                    }
                }
            }
        }
    }

    if (showQuickNumberDialog) {
        QuickNumberDialog(
            hymns = uiState.hymns,
            onDismiss = { showQuickNumberDialog = false },
            onHymnSelected = { hymnId ->
                showQuickNumberDialog = false
                onHymnClick(hymnId)
            }
        )
    }

    if (showManageCategoriesDialog) {
        ManageCategoriesDialog(
            categories = uiState.categories,
            onDismiss = { showManageCategoriesDialog = false },
            onCreateCategory = { name, colorHex ->
                onCreateCategory(name, colorHex)
            },
            onDeleteCategory = { cat ->
                onDeleteCategory(cat)
            }
        )
    }

    if (showAboutDialog) {
        AboutLicenseDialog(
            onDismiss = { showAboutDialog = false }
        )
    }
}

/**
 * Alphabetical Dictionary View (Diccionario Alfabético de Primeros Versos).
 * Indexes hymns by the first line actually sung ("lo que se va a cantar"),
 * allowing users to look up hymns by how they start singing (e.g. "Señor mi Dios..." under S).
 */
@Composable
fun AlphabeticalDictionaryView(
    uiState: HymnUiState,
    onSelectLetter: (Char?) -> Unit,
    onHymnClick: (Int) -> Unit,
    onFavoriteToggle: (Hymn) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Explanatory Header Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(HymnalNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SortByAlpha,
                        contentDescription = null,
                        tint = HymnalGoldLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Diccionario de Primeros Versos",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Ordenado por la primera frase que se canta, no por el título",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = HymnalGold.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "${uiState.filteredHymns.size} cantos",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = HymnalGoldDark,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        // Alphabet Quick-Scrubber / Letter chips row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 6.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // "Todas" chip
            item {
                val isAllSelected = uiState.selectedAlphabetLetter == null
                Surface(
                    onClick = { onSelectLetter(null) },
                    shape = RoundedCornerShape(10.dp),
                    color = if (isAllSelected) HymnalNavy else MaterialTheme.colorScheme.surfaceVariant,
                    border = if (isAllSelected) androidx.compose.foundation.BorderStroke(1.dp, HymnalGold) else null,
                    modifier = Modifier.testTag("dict_letter_all")
                ) {
                    Text(
                        text = "Todas",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isAllSelected) HymnalGoldLight else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            // Available letter chips
            items(uiState.availableAlphabetLetters) { letter ->
                val isSelected = uiState.selectedAlphabetLetter == letter
                Surface(
                    onClick = { onSelectLetter(letter) },
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) HymnalNavy else MaterialTheme.colorScheme.surfaceVariant,
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, HymnalGold) else null,
                    modifier = Modifier.testTag("dict_letter_$letter")
                ) {
                    Text(
                        text = letter.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) HymnalGoldLight else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Alphabetical List by Letters
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.alphabeticalSections, key = { it.letter }) { section ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Section Letter Header
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = HymnalNavy.copy(alpha = 0.08f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.linearGradient(
                                                colors = listOf(HymnalNavy, HymnalNavyDark)
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = section.letter.toString(),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = HymnalGoldLight
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Letra ${section.letter}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = "${section.entries.size} himnos",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Entries under this letter
                    section.entries.forEach { entry ->
                        AlphabeticalHymnCardItem(
                            entry = entry,
                            onClick = { onHymnClick(entry.hymn.id) },
                            onFavoriteToggle = { onFavoriteToggle(entry.hymn) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Card representing a hymn in the Alphabetical Dictionary.
 * Gives primary prominence to the first sung line ("lo que se va a cantar").
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlphabeticalHymnCardItem(
    entry: AlphabeticalHymnEntry,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    val hymn = entry.hymn

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("dict_hymn_card_${hymn.number}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hymn Number Badge
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(HymnalNavy, HymnalNavyDark)
                        )
                    )
                    .border(1.dp, HymnalGold.copy(alpha = 0.6f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${hymn.number}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HymnalGoldLight
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Text info
            Column(modifier = Modifier.weight(1f)) {
                // Primary: First sung line prominent in quotes
                Text(
                    text = "“${entry.firstSungLine}”",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                // Secondary: Official Hymn Title
                Text(
                    text = "Título: ${hymn.title}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Badges: Tone and Categories
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    hymn.getCategories().forEach { catName ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = catName,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    if (hymn.tone.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = hymn.tone,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // Actions (favorite + arrow)
            IconButton(
                onClick = onFavoriteToggle,
                modifier = Modifier
                    .size(40.dp)
                    .testTag("dict_favorite_button_${hymn.number}")
            ) {
                Icon(
                    imageVector = if (hymn.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (hymn.isFavorite) "Quitar de favoritos" else "Marcar como favorito",
                    tint = if (hymn.isFavorite) HymnalBurgundy else MaterialTheme.colorScheme.outline
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun RecentHymnCard(
    hymn: Hymn,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(190.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("recent_hymn_card_${hymn.number}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = HymnalNavy,
                    border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "#${hymn.number}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = HymnalGoldLight,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                if (hymn.isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorito",
                        tint = HymnalBurgundy,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = hymn.title,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Relative time indicator
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                val timeLabel = remember(hymn.lastReadAt) {
                    val diff = System.currentTimeMillis() - hymn.lastReadAt
                    when {
                        diff < 60_000L -> "Hace un momento"
                        diff < 3600_000L -> "Hace ${diff / 60_000L} min"
                        diff < 86400_000L -> "Hoy"
                        diff < 172800_000L -> "Ayer"
                        else -> "Reciente"
                    }
                }
                Text(
                    text = timeLabel,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun CategoryGroupSection(
    group: CategoryGroup,
    onToggleCollapse: () -> Unit,
    onHymnClick: (Int) -> Unit,
    onFavoriteToggle: (Hymn) -> Unit
) {
    val catColor = try {
        Color(android.graphics.Color.parseColor(group.category.colorHex))
    } catch (e: Exception) {
        HymnalNavy
    }

    val arrowRotation by animateFloatAsState(
        targetValue = if (group.isCollapsed) -90f else 0f,
        label = "ArrowRotation"
    )

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, catColor.copy(alpha = 0.25f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("category_group_${group.category.name}")
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Category Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onToggleCollapse)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(catColor)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = group.category.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = catColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "${group.hymns.size}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = catColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onToggleCollapse,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = if (group.isCollapsed) "Expandir" else "Colapsar",
                        modifier = Modifier.rotate(arrowRotation)
                    )
                }
            }

            // Hymns inside category
            AnimatedVisibility(visible = !group.isCollapsed) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (group.hymns.isEmpty()) {
                        Text(
                            text = "No hay himnos asignados a esta categoría todavía.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    } else {
                        group.hymns.forEach { hymn ->
                            HymnCardItem(
                                hymn = hymn,
                                onClick = { onHymnClick(hymn.id) },
                                onFavoriteToggle = { onFavoriteToggle(hymn) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HymnCardItem(
    hymn: Hymn,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("hymn_card_${hymn.number}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hymn Number badge
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(HymnalNavy, HymnalNavyDark)
                        )
                    )
                    .border(1.dp, HymnalGold.copy(alpha = 0.6f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${hymn.number}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HymnalGoldLight
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = hymn.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Sung line subtitle if different from title
                val sungLine = remember(hymn) { hymn.getFirstSungLine() }
                if (sungLine.isNotBlank() && !sungLine.equals(hymn.title, ignoreCase = true)) {
                    Text(
                        text = "“$sungLine”",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Multiple categories pills
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    hymn.getCategories().forEach { catName ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = catName,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    if (hymn.tone.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = hymn.tone,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Snippet or empty status
                if (!hymn.hasText) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(HymnalGoldLight.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = null,
                            tint = HymnalGoldDark,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Toca para agregar texto",
                            style = MaterialTheme.typography.labelSmall,
                            color = HymnalGoldDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    val snippet = if (hymn.lyrics.isNotBlank()) {
                        hymn.lyrics.lines().firstOrNull { it.isNotBlank() } ?: ""
                    } else {
                        hymn.chorus.lines().firstOrNull { it.isNotBlank() } ?: ""
                    }
                    Text(
                        text = snippet,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Actions (favorite + arrow)
            IconButton(
                onClick = onFavoriteToggle,
                modifier = Modifier
                    .size(40.dp)
                    .testTag("favorite_button_${hymn.number}")
            ) {
                Icon(
                    imageVector = if (hymn.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (hymn.isFavorite) "Quitar de favoritos" else "Marcar como favorito",
                    tint = if (hymn.isFavorite) HymnalBurgundy else MaterialTheme.colorScheme.outline
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
