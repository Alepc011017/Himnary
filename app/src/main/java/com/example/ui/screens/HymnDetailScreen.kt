package com.example.ui.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Hymn
import com.example.ui.HymnUiState
import com.example.ui.ReaderFont
import com.example.ui.ReadingTheme
import com.example.ui.theme.HymnalBurgundy
import com.example.ui.theme.HymnalGold
import com.example.ui.theme.HymnalGoldDark
import com.example.ui.theme.HymnalGoldLight
import com.example.ui.theme.HymnalNavy
import com.example.ui.theme.HymnalNavyDark
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HymnDetailScreen(
    hymn: Hymn?,
    uiState: HymnUiState,
    onBack: () -> Unit,
    onEditHymn: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onPreviousHymn: () -> Unit,
    onNextHymn: () -> Unit,
    onSetFontSize: (Float) -> Unit,
    onResetFontSize: () -> Unit,
    onIncreaseFontSize: () -> Unit,
    onDecreaseFontSize: () -> Unit,
    onSetReadingTheme: (ReadingTheme) -> Unit,
    onSetReaderFont: (ReaderFont) -> Unit,
    onToggleAutoScroll: () -> Unit,
    onSetAutoScrollSpeed: (Float) -> Unit
) {
    BackHandler(onBack = onBack)

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    // Panel visibility states
    var showFontSizePanel by remember { mutableStateOf(false) }
    var showThemePanel by remember { mutableStateOf(false) }
    var copiedFeedback by remember { mutableStateOf(false) }

    // Pinch-to-zoom HUD feedback
    var zoomFeedbackCounter by remember { mutableIntStateOf(0) }
    var showZoomHud by remember { mutableStateOf(false) }

    LaunchedEffect(zoomFeedbackCounter) {
        if (zoomFeedbackCounter > 0) {
            showZoomHud = true
            delay(1300)
            showZoomHud = false
        }
    }

    val scrollState = rememberScrollState()

    // Smooth auto-scroll loop
    LaunchedEffect(uiState.isAutoScrolling, uiState.autoScrollSpeed) {
        if (uiState.isAutoScrolling) {
            val stepDelay = when (uiState.autoScrollSpeed) {
                0.5f -> 60L
                1.5f -> 25L
                2.0f -> 15L
                else -> 40L
            }
            while (uiState.isAutoScrolling && scrollState.value < scrollState.maxValue) {
                delay(stepDelay)
                scrollState.scrollTo((scrollState.value + 2).coerceAtMost(scrollState.maxValue))
            }
        }
    }

    if (hymn == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Himno no encontrado")
        }
        return
    }

    // Colors according to reading theme
    val (themeBg, themeCardBg, themeTextColor, themeAccentColor) = when (uiState.readingTheme) {
        ReadingTheme.PARCHMENT -> Quad(
            Color(0xFFF6F0E2),
            Color(0xFFEDE4D0),
            Color(0xFF2C2216),
            HymnalGoldDark
        )
        ReadingTheme.LIGHT -> Quad(
            Color(0xFFFAFAFA),
            Color(0xFFF1F3F5),
            Color(0xFF1A1A1A),
            HymnalNavy
        )
        ReadingTheme.DARK -> Quad(
            Color(0xFF0F172A),
            Color(0xFF1E293B),
            Color(0xFFF1F5F9),
            HymnalGoldLight
        )
    }

    val chosenFontFamily = if (uiState.readerFont == ReaderFont.SERIF) FontFamily.Serif else FontFamily.Default

    // Proportional font presets
    val fontPresets = listOf(
        14f to "Compacto (14 sp)",
        18f to "Normal (18 sp)",
        22f to "Grande (22 sp)",
        28f to "Muy Grande (28 sp)",
        34f to "Púlpito / Atril (34 sp)"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = HymnalGold,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = "#${hymn.number}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = hymn.title,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("detail_back_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    // Quick Font Size Action Button
                    IconButton(
                        onClick = {
                            showFontSizePanel = !showFontSizePanel
                            if (showFontSizePanel) showThemePanel = false
                        },
                        modifier = Modifier.testTag("detail_font_size_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = "Ajustar tamaño de fuente",
                            tint = if (showFontSizePanel) HymnalGold else themeTextColor
                        )
                    }

                    // Reading Preferences (Theme & AutoScroll) bar toggle
                    IconButton(
                        onClick = {
                            showThemePanel = !showThemePanel
                            if (showThemePanel) showFontSizePanel = false
                        },
                        modifier = Modifier.testTag("detail_toggle_controls")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Ajustes de lectura",
                            tint = if (showThemePanel) HymnalGold else themeTextColor
                        )
                    }

                    // Favorite toggle
                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier.testTag("detail_favorite_toggle")
                    ) {
                        Icon(
                            imageVector = if (hymn.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (hymn.isFavorite) HymnalBurgundy else themeTextColor
                        )
                    }

                    // Edit button
                    IconButton(
                        onClick = onEditHymn,
                        modifier = Modifier.testTag("detail_edit_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar texto del himno"
                        )
                    }

                    // Share button
                    IconButton(
                        onClick = {
                            val shareBody = buildString {
                                appendLine("Himno #${hymn.number}: ${hymn.title}")
                                if (hymn.scriptureRef.isNotBlank()) appendLine("Ref: ${hymn.scriptureRef}")
                                if (hymn.author.isNotBlank()) appendLine("Autor: ${hymn.author}")
                                appendLine()
                                if (hymn.chorus.isNotBlank()) {
                                    appendLine("[CORO]")
                                    appendLine(hymn.chorus)
                                    appendLine()
                                }
                                if (hymn.lyrics.isNotBlank()) {
                                    appendLine(hymn.lyrics)
                                }
                            }
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Himno #${hymn.number} - ${hymn.title}")
                                putExtra(Intent.EXTRA_TEXT, shareBody)
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Compartir himno")
                            context.startActivity(shareIntent)
                        },
                        modifier = Modifier.testTag("detail_share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir himno"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeBg,
                    titleContentColor = themeTextColor,
                    navigationIconContentColor = themeTextColor,
                    actionIconContentColor = themeTextColor
                )
            )
        },
        bottomBar = {
            Surface(
                color = themeBg,
                tonalElevation = 6.dp,
                border = androidx.compose.foundation.BorderStroke(0.5.dp, themeAccentColor.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous button
                    OutlinedButton(
                        onClick = onPreviousHymn,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("prev_hymn_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Anterior")
                    }

                    // Direct Font Size Pill indicator in bottom bar (tap to open panel)
                    Surface(
                        onClick = { showFontSizePanel = !showFontSizePanel },
                        shape = RoundedCornerShape(12.dp),
                        color = themeCardBg,
                        border = androidx.compose.foundation.BorderStroke(1.dp, themeAccentColor.copy(alpha = 0.4f)),
                        modifier = Modifier.testTag("bottom_font_size_indicator")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatSize,
                                contentDescription = null,
                                tint = themeAccentColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${uiState.readingFontSize.toInt()} sp",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = themeTextColor
                            )
                        }
                    }

                    // Auto-scroll toggle
                    FilledTonalButton(
                        onClick = onToggleAutoScroll,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (uiState.isAutoScrolling) HymnalGold else themeCardBg,
                            contentColor = if (uiState.isAutoScrolling) Color.Black else themeTextColor
                        ),
                        modifier = Modifier.testTag("toggle_autoscroll_button")
                    ) {
                        Icon(
                            imageVector = if (uiState.isAutoScrolling) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (uiState.isAutoScrolling) "Pausa" else "Auto-Scroll")
                    }

                    // Next button
                    OutlinedButton(
                        onClick = onNextHymn,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("next_hymn_button")
                    ) {
                        Text("Siguiente")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(themeBg)
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                // DYNAMIC FONT SIZE CONTROLLER PANEL
                AnimatedVisibility(
                    visible = showFontSizePanel,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = themeCardBg),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, themeAccentColor.copy(alpha = 0.5f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Header of font panel
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.FormatSize,
                                        contentDescription = null,
                                        tint = themeAccentColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Tamaño de Fuente Dinámico",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = themeTextColor
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    TextButton(
                                        onClick = {
                                            onResetFontSize()
                                            zoomFeedbackCounter++
                                        },
                                        modifier = Modifier.testTag("reset_font_size_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.RestartAlt,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Restablecer", fontSize = 12.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Interactive Continuous Slider with - and + step buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        onDecreaseFontSize()
                                        zoomFeedbackCounter++
                                    },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .testTag("decrease_font_step")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Reducir tamaño",
                                        tint = themeTextColor
                                    )
                                }

                                Slider(
                                    value = uiState.readingFontSize,
                                    onValueChange = { newSize ->
                                        onSetFontSize(newSize)
                                        zoomFeedbackCounter++
                                    },
                                    valueRange = 14f..36f,
                                    steps = 21,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 6.dp)
                                        .testTag("font_size_slider"),
                                    colors = SliderDefaults.colors(
                                        thumbColor = themeAccentColor,
                                        activeTrackColor = themeAccentColor,
                                        inactiveTrackColor = themeAccentColor.copy(alpha = 0.2f)
                                    )
                                )

                                IconButton(
                                    onClick = {
                                        onIncreaseFontSize()
                                        zoomFeedbackCounter++
                                    },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .testTag("increase_font_step")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Aumentar tamaño",
                                        tint = themeTextColor
                                    )
                                }
                            }

                            // Current size badge & percentage indicator
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val percentage = ((uiState.readingFontSize / 18f) * 100).toInt()
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = themeBg,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, themeAccentColor.copy(alpha = 0.3f))
                                ) {
                                    Text(
                                        text = "${uiState.readingFontSize.toInt()} sp  •  $percentage% de escala",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = themeAccentColor,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Quick Presets Row
                            Text(
                                text = "Ajustes rápidos para cantar y predicar:",
                                style = MaterialTheme.typography.labelSmall,
                                color = themeTextColor.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                fontPresets.forEach { (presetSp, label) ->
                                    val isSelected = uiState.readingFontSize.toInt() == presetSp.toInt()
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            onSetFontSize(presetSp)
                                            zoomFeedbackCounter++
                                        },
                                        label = {
                                            Text(
                                                text = label,
                                                fontSize = 11.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = themeAccentColor,
                                            selectedLabelColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.testTag("preset_${presetSp.toInt()}")
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "💡 También puedes pellizcar con dos dedos la pantalla en cualquier momento para hacer zoom dinámico.",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = themeTextColor.copy(alpha = 0.6f)
                            )
                        }
                    }
                }

                // THEME & READING PREFERENCES PANEL
                AnimatedVisibility(
                    visible = showThemePanel,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Surface(
                        color = themeCardBg,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(16.dp),
                        tonalElevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Font Family toggle
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Estilo de Tipografía:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = themeTextColor
                                )

                                Surface(
                                    onClick = {
                                        onSetReaderFont(
                                            if (uiState.readerFont == ReaderFont.SERIF) ReaderFont.SANS else ReaderFont.SERIF
                                        )
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = themeBg,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, themeAccentColor.copy(alpha = 0.5f))
                                ) {
                                    Text(
                                        text = if (uiState.readerFont == ReaderFont.SERIF) "Serif Clásico" else "Sans Limpio",
                                        fontFamily = chosenFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = themeTextColor,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Theme Mode Selector
                            Text(
                                text = "Tema Visual de Lectura:",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = themeTextColor
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                ThemeButton(
                                    label = "Pergamino",
                                    isSelected = uiState.readingTheme == ReadingTheme.PARCHMENT,
                                    onClick = { onSetReadingTheme(ReadingTheme.PARCHMENT) },
                                    modifier = Modifier.weight(1f)
                                )
                                ThemeButton(
                                    label = "Luz",
                                    isSelected = uiState.readingTheme == ReadingTheme.LIGHT,
                                    onClick = { onSetReadingTheme(ReadingTheme.LIGHT) },
                                    modifier = Modifier.weight(1f)
                                )
                                ThemeButton(
                                    label = "Noche",
                                    isSelected = uiState.readingTheme == ReadingTheme.DARK,
                                    onClick = { onSetReadingTheme(ReadingTheme.DARK) },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            // Auto-scroll speed selector if scrolling active
                            if (uiState.isAutoScrolling) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Velocidad de avance:",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = themeTextColor
                                    )
                                    listOf(0.5f to "0.5x", 1.0f to "1.0x", 1.5f to "1.5x", 2.0f to "2.0x").forEach { (spd, label) ->
                                        Surface(
                                            onClick = { onSetAutoScrollSpeed(spd) },
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (uiState.autoScrollSpeed == spd) HymnalGold else themeBg
                                        ) {
                                            Text(
                                                text = label,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (uiState.autoScrollSpeed == spd) Color.Black else themeTextColor,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // MAIN READER CONTENT with PINCH-TO-ZOOM SUPPORT
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, _, zoom, _ ->
                                if (zoom != 1f) {
                                    val delta = (zoom - 1f) * 12f
                                    val newSize = (uiState.readingFontSize + delta).coerceIn(14f, 36f)
                                    onSetFontSize(newSize)
                                    zoomFeedbackCounter++
                                }
                            }
                        }
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    // Header with Title & Metadata
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Himno #${hymn.number}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = themeAccentColor,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Scaled Title
                        Text(
                            text = hymn.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = (uiState.readingFontSize + 6).sp,
                                lineHeight = ((uiState.readingFontSize + 6) * 1.3).sp
                            ),
                            fontFamily = chosenFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = themeTextColor,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Pills: Categories, Tone, Scripture
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            hymn.getCategories().forEach { catName ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = themeCardBg
                                ) {
                                    Text(
                                        text = catName,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = themeTextColor,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            if (hymn.tone.isNotBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = themeAccentColor.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "Tono: ${hymn.tone}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = themeAccentColor,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            if (hymn.scriptureRef.isNotBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = themeCardBg
                                ) {
                                    Text(
                                        text = hymn.scriptureRef,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = themeTextColor.copy(alpha = 0.8f),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }

                        if (hymn.author.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Autor: ${hymn.author}",
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic,
                                color = themeTextColor.copy(alpha = 0.7f)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(
                            color = themeAccentColor.copy(alpha = 0.25f),
                            thickness = 1.dp,
                            modifier = Modifier.width(120.dp)
                        )
                    }

                    // If hymn has NO text yet: Highlighting Banner
                    if (!hymn.hasText) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = HymnalGold.copy(alpha = 0.15f)
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, HymnalGold)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = HymnalGoldDark,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Este himno aún no tiene letra guardada",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = themeTextColor,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Puedes redactar o pegar las estrofas y el coro fácilmente ahora mismo.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = themeTextColor.copy(alpha = 0.8f),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = onEditHymn,
                                    colors = ButtonDefaults.buttonColors(containerColor = HymnalNavy),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.testTag("add_lyrics_now_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = HymnalGoldLight,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("✍️ Agregar Letra del Himno", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    // Stanzas and Chorus rendering with dynamic font scaling
                    val stanzas = hymn.getStanzas()

                    // Render Stanza 1
                    if (stanzas.isNotEmpty()) {
                        StanzaItem(
                            index = 1,
                            text = stanzas[0],
                            fontSize = uiState.readingFontSize,
                            fontFamily = chosenFontFamily,
                            textColor = themeTextColor,
                            accentColor = themeAccentColor
                        )
                    }

                    // Dedicated Chorus Card (Coro) with dynamic font scaling
                    if (hymn.chorus.isNotBlank()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = themeCardBg
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, themeAccentColor.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.MusicNote,
                                        contentDescription = null,
                                        tint = themeAccentColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "CORO",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 2.sp,
                                        color = themeAccentColor
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = hymn.chorus,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = (uiState.readingFontSize + 1).sp,
                                        lineHeight = ((uiState.readingFontSize + 1) * 1.55).sp
                                    ),
                                    fontFamily = chosenFontFamily,
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.SemiBold,
                                    color = themeTextColor
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // Render Remaining Stanzas (2, 3, 4...)
                    if (stanzas.size > 1) {
                        for (i in 1 until stanzas.size) {
                            StanzaItem(
                                index = i + 1,
                                text = stanzas[i],
                                fontSize = uiState.readingFontSize,
                                fontFamily = chosenFontFamily,
                                textColor = themeTextColor,
                                accentColor = themeAccentColor
                            )
                        }
                    }

                    // If there were no segmented stanzas but lyrics is not blank, display as block
                    if (stanzas.isEmpty() && hymn.lyrics.isNotBlank()) {
                        Text(
                            text = hymn.lyrics,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = uiState.readingFontSize.sp,
                                lineHeight = (uiState.readingFontSize * 1.55).sp
                            ),
                            fontFamily = chosenFontFamily,
                            color = themeTextColor
                        )
                    }



                    // Quick copy & edit footer actions
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = {
                                val allLyrics = buildString {
                                    appendLine("Himno #${hymn.number}: ${hymn.title}")
                                    if (hymn.chorus.isNotBlank()) appendLine("\n[CORO]\n${hymn.chorus}")
                                    if (hymn.lyrics.isNotBlank()) appendLine("\n${hymn.lyrics}")
                                }
                                clipboardManager.setText(AnnotatedString(allLyrics))
                                copiedFeedback = true
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("copy_hymn_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (copiedFeedback) "¡Copiado!" else "Copiar Letra")
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = onEditHymn,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = HymnalNavy),
                            modifier = Modifier.testTag("edit_lyrics_footer_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = HymnalGoldLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Editar Texto", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            // FLOATING ZOOM HUD INDICATOR
            AnimatedVisibility(
                visible = showZoomHud,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = HymnalNavy.copy(alpha = 0.92f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold),
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = null,
                            tint = HymnalGoldLight,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tamaño de letra: ${uiState.readingFontSize.toInt()} sp",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StanzaItem(
    index: Int,
    text: String,
    fontSize: Float,
    fontFamily: FontFamily,
    textColor: Color,
    accentColor: Color
) {
    val cleanText = text.replaceFirst(Regex("^\\s*\\d+[.\\-)]\\s*"), "")
    // Badge size scales gently with font size
    val badgeSize = (fontSize * 1.5f).coerceIn(26f, 40f).dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = (fontSize * 0.4f).coerceIn(6f, 14f).dp)
    ) {
        Box(
            modifier = Modifier
                .size(badgeSize)
                .clip(CircleShape)
                .background(accentColor.copy(alpha = 0.15f))
                .border(1.dp, accentColor.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$index",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = (fontSize * 0.65f).coerceIn(10f, 18f).sp
                ),
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }

        Spacer(modifier = Modifier.width((fontSize * 0.65f).coerceIn(10f, 20f).dp))

        Text(
            text = cleanText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = fontSize.sp,
                lineHeight = (fontSize * 1.55f).sp
            ),
            fontFamily = fontFamily,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ThemeButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) HymnalNavy else MaterialTheme.colorScheme.surface,
        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, HymnalGold) else null,
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) HymnalGoldLight else MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
