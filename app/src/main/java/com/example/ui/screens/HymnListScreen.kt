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
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
    onToggleCategoryCollapse: (String) -> Unit,
    onHymnClick: (Int) -> Unit,
    onFavoriteToggle: (Hymn) -> Unit,
    onAddNewHymn: () -> Unit,
    onCreateCategory: (String, String) -> Unit,
    onDeleteCategory: (HymnCategory) -> Unit
) {
    var showQuickNumberDialog by remember { mutableStateOf(false) }
    var showManageCategoriesDialog by remember { mutableStateOf(false) }

    val filterChips = remember(uiState.categories) {
        listOf("Todos", "Favoritos", "Sin Letra") + uiState.categories.map { it.name }
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
                                    text = "HIMNARIO CRISTIANO",
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

                        // Stats pills
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
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
                            text = "Buscar por título, número, categoría...",
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

            // View Mode Selector (Agrupados por Categoría vs Todos los Himnos)
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
                    // Grouped Tab
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
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Agrupados por Categoría",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isGrouped) FontWeight.Bold else FontWeight.Normal,
                                color = if (isGrouped) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Flat List Tab
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
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Lista General",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isFlat) FontWeight.Bold else FontWeight.Normal,
                                color = if (isFlat) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Category Chips Row with "+ Nueva Categoría" Button
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Manage Categories button
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
                            } else {
                                Text(text = category)
                            }
                        },
                        leadingIcon = if (category == "Favoritos") {
                            {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = if (isSelected) HymnalBurgundy else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else if (category == "Sin Letra") {
                            {
                                Icon(
                                    imageVector = Icons.Default.EditNote,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = if (isSelected) HymnalGold else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("filter_chip_$category")
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Main Content: Grouped by Category vs Flat List
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
                                    "No hay himnos en esta categoría.",
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
            } else if (uiState.viewMode == HymnViewMode.GROUPED_BY_CATEGORY && uiState.selectedCategory == "Todos") {
                // GROUPED VIEW
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
                // FLAT LIST VIEW
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
