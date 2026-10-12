package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Hymn
import com.example.data.HymnCategory
import com.example.ui.theme.HymnalBurgundy
import com.example.ui.theme.HymnalGold
import com.example.ui.theme.HymnalGoldDark
import com.example.ui.theme.HymnalGoldLight
import com.example.ui.theme.HymnalNavy

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HymnEditScreen(
    hymnToEdit: Hymn?,
    suggestedNextNumber: Int,
    availableCategories: List<HymnCategory>,
    onBack: () -> Unit,
    onCreateCategory: (String, String) -> Unit,
    onSave: (
        id: Int,
        number: Int,
        title: String,
        categories: List<String>,
        tone: String,
        author: String,
        chorus: String,
        lyrics: String,
        scriptureRef: String,
        notes: String
    ) -> Unit,
    onDelete: ((Hymn) -> Unit)? = null
) {
    BackHandler(onBack = onBack)

    val clipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()

    var numberText by remember(hymnToEdit) {
        mutableStateOf(hymnToEdit?.number?.toString() ?: suggestedNextNumber.toString())
    }
    var titleText by remember(hymnToEdit) {
        mutableStateOf(hymnToEdit?.title ?: "")
    }

    // Multi-category state: set of selected category names
    var selectedCategories by remember(hymnToEdit) {
        mutableStateOf(
            hymnToEdit?.getCategories()?.toSet() ?: setOf("Alabanza")
        )
    }

    var toneText by remember(hymnToEdit) {
        mutableStateOf(hymnToEdit?.tone ?: "")
    }
    var authorText by remember(hymnToEdit) {
        mutableStateOf(hymnToEdit?.author ?: "")
    }
    var chorusText by remember(hymnToEdit) {
        mutableStateOf(hymnToEdit?.chorus ?: "")
    }
    var lyricsText by remember(hymnToEdit) {
        mutableStateOf(hymnToEdit?.lyrics ?: "")
    }
    var scriptureRefText by remember(hymnToEdit) {
        mutableStateOf(hymnToEdit?.scriptureRef ?: "")
    }
    var notesText by remember(hymnToEdit) {
        mutableStateOf(hymnToEdit?.notes ?: "")
    }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Form, 1: Live Preview
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showQuickCreateCategoryDialog by remember { mutableStateOf(false) }
    var quickNewCatName by remember { mutableStateOf("") }
    var quickNewCatError by remember { mutableStateOf<String?>(null) }
    var titleError by remember { mutableStateOf(false) }

    fun doSave() {
        val cleanTitle = titleText.trim()
        if (cleanTitle.isEmpty()) {
            titleError = true
            selectedTab = 0
            return
        }
        val number = numberText.toIntOrNull() ?: suggestedNextNumber
        val catList = if (selectedCategories.isEmpty()) listOf("Alabanza") else selectedCategories.toList()

        onSave(
            hymnToEdit?.id ?: 0,
            number,
            cleanTitle,
            catList,
            toneText,
            authorText,
            chorusText,
            lyricsText,
            scriptureRefText,
            notesText
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (hymnToEdit == null) "Nuevo Himno" else "Editar Himno #${hymnToEdit.number}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("edit_cancel_button")) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cancelar")
                    }
                },
                actions = {
                    if (hymnToEdit != null && onDelete != null) {
                        IconButton(
                            onClick = { showDeleteConfirmDialog = true },
                            modifier = Modifier.testTag("edit_delete_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar himno",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    Button(
                        onClick = { doSave() },
                        colors = ButtonDefaults.buttonColors(containerColor = HymnalGold),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("edit_save_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Guardar", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Mode Tabs (Editor vs Vista Previa)
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    text = { Text("Editor de Texto", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_editor")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    text = { Text("Vista Previa", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_preview")
                )
            }

            if (selectedTab == 0) {
                // FORM VIEW
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Section 1: Basic Info
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Información del Himno",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            // Number and Title Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedTextField(
                                    value = numberText,
                                    onValueChange = { numberText = it },
                                    label = { Text("Número") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier
                                        .width(110.dp)
                                        .testTag("edit_number_input"),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = titleText,
                                    onValueChange = {
                                        titleText = it
                                        if (it.isNotBlank()) titleError = false
                                    },
                                    label = { Text("Título del Himno *") },
                                    placeholder = { Text("Ej: Castillo Fuerte") },
                                    isError = titleError,
                                    supportingText = if (titleError) {
                                        { Text("El título es obligatorio", color = MaterialTheme.colorScheme.error) }
                                    } else null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("edit_title_input"),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }

                            // MULTI-CATEGORY SELECTOR
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Categorías asignadas (puedes elegir varias)",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (selectedCategories.isEmpty()) "Selecciona al menos una"
                                        else "${selectedCategories.size} seleccionada(s): ${selectedCategories.joinToString(", ")}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (selectedCategories.isEmpty()) MaterialTheme.colorScheme.error else HymnalGoldDark
                                    )
                                }

                                TextButton(
                                    onClick = { showQuickCreateCategoryDialog = true },
                                    modifier = Modifier.testTag("quick_create_category_button")
                                ) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("+ Nueva", fontWeight = FontWeight.Bold)
                                }
                            }

                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                availableCategories.forEach { cat ->
                                    val isSelected = selectedCategories.contains(cat.name)
                                    val catColor = try {
                                        Color(android.graphics.Color.parseColor(cat.colorHex))
                                    } catch (e: Exception) {
                                        HymnalNavy
                                    }

                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            selectedCategories = if (isSelected) {
                                                selectedCategories - cat.name
                                            } else {
                                                selectedCategories + cat.name
                                            }
                                        },
                                        label = { Text(cat.name, fontSize = 12.sp) },
                                        leadingIcon = if (isSelected) {
                                            {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        } else {
                                            {
                                                Box(
                                                    modifier = Modifier
                                                        .size(10.dp)
                                                        .clip(CircleShape)
                                                        .background(catColor)
                                                )
                                            }
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = catColor,
                                            selectedLabelColor = Color.White,
                                            selectedLeadingIconColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.testTag("chip_cat_${cat.name}")
                                    )
                                }
                            }

                            // Tone and Author
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedTextField(
                                    value = toneText,
                                    onValueChange = { toneText = it },
                                    label = { Text("Tono musical") },
                                    placeholder = { Text("Ej: Sol (G)") },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("edit_tone_input"),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = authorText,
                                    onValueChange = { authorText = it },
                                    label = { Text("Autor / Origen") },
                                    placeholder = { Text("Ej: John Newton") },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("edit_author_input"),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }

                            // Scripture Reference
                            OutlinedTextField(
                                value = scriptureRefText,
                                onValueChange = { scriptureRefText = it },
                                label = { Text("Referencia bíblica") },
                                placeholder = { Text("Ej: Salmos 100:1-5") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("edit_scripture_input"),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }

                    // Section 2: Coro (Chorus)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = HymnalGoldDark,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Coro del Himno (Opcional)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = HymnalGoldDark
                                )
                            }
                            Text(
                                text = "El coro se resaltará en un marco dorado e itálica en la vista de canto.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            OutlinedTextField(
                                value = chorusText,
                                onValueChange = { chorusText = it },
                                placeholder = {
                                    Text("Escribe aquí el coro o estribillo del himno...")
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .testTag("edit_chorus_input"),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = HymnalGold
                                )
                            )
                        }
                    }

                    // Section 3: Estrofas / Letra (Verses & Lyrics)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.FormatListNumbered,
                                        contentDescription = null,
                                        tint = HymnalNavy,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Estrofas / Letra del Himno",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                // Paste clipboard button
                                OutlinedButton(
                                    onClick = {
                                        clipboardManager.getText()?.let { clip ->
                                            if (clip.text.isNotBlank()) {
                                                lyricsText = if (lyricsText.isBlank()) {
                                                    clip.text
                                                } else {
                                                    lyricsText + "\n\n" + clip.text
                                                }
                                            }
                                        }
                                    },
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("paste_clipboard_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentPaste,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Pegar", fontSize = 12.sp)
                                }
                            }

                            Text(
                                text = "Consejo: Separa cada estrofa dejando una línea en blanco (presiona Enter dos veces). El lector numerará cada estrofa de forma automática.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            // Quick Stanza Templates
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf(1, 2, 3, 4).forEach { stanzaNum ->
                                    Surface(
                                        onClick = {
                                            val prefix = "\n\n$stanzaNum. "
                                            lyricsText = if (lyricsText.isBlank()) "$stanzaNum. " else lyricsText + prefix
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "+ Estrofa $stanzaNum",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(vertical = 6.dp),
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
                                    }
                                }
                            }

                            // Large lyrics text box
                            OutlinedTextField(
                                value = lyricsText,
                                onValueChange = { lyricsText = it },
                                placeholder = {
                                    Text("1. Escribe la primera estrofa aquí...\n\n2. Segunda estrofa...\n\n3. Tercera estrofa...")
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .testTag("edit_lyrics_input"),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }



                    // Bottom Save Button
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { doSave() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("bottom_save_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = HymnalNavy),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = HymnalGoldLight,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (hymnToEdit == null) "Guardar Nuevo Himno" else "Guardar Cambios del Himno",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            } else {
                // LIVE PREVIEW VIEW
                val previewHymn = remember(numberText, titleText, selectedCategories, toneText, authorText, chorusText, lyricsText, scriptureRefText, notesText) {
                    Hymn(
                        number = numberText.toIntOrNull() ?: suggestedNextNumber,
                        title = titleText.ifBlank { "Título de Ejemplo" },
                        category = selectedCategories.joinToString(", "),
                        tone = toneText,
                        author = authorText,
                        chorus = chorusText,
                        lyrics = lyricsText,
                        scriptureRef = scriptureRefText,
                        notes = notesText
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF6F0E2))
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    // Preview Header
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = HymnalGold,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Text(
                                text = "VISTA PREVIA",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = "Himno #${previewHymn.number}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = HymnalGoldDark
                        )

                        Text(
                            text = previewHymn.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C2216),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        // Multi-category preview pills
                        if (previewHymn.getCategories().isNotEmpty() || previewHymn.tone.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                previewHymn.getCategories().forEach { catName ->
                                    Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFEDE4D0)) {
                                        Text(
                                            text = catName,
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                if (previewHymn.tone.isNotBlank()) {
                                    Surface(shape = RoundedCornerShape(6.dp), color = HymnalGold.copy(alpha = 0.2f)) {
                                        Text(
                                            text = "Tono: ${previewHymn.tone}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = HymnalGoldDark,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }

                        if (previewHymn.author.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Autor: ${previewHymn.author}",
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = HymnalGoldDark.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.width(100.dp))
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // Preview Stanzas & Chorus
                    val stanzas = previewHymn.getStanzas()
                    if (stanzas.isEmpty() && previewHymn.chorus.isBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "(Aún no has escrito coro ni estrofas. Cambia a la pestaña 'Editor de Texto' para agregarlos).",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    } else {
                        // Stanza 1
                        if (stanzas.isNotEmpty()) {
                            PreviewStanza(1, stanzas[0])
                        }

                        // Chorus
                        if (previewHymn.chorus.isNotBlank()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE4D0)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, HymnalGold)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = "CORO",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = HymnalGoldDark,
                                        letterSpacing = 2.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = previewHymn.chorus,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontFamily = FontFamily.Serif,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF2C2216)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // Stanzas 2+
                        if (stanzas.size > 1) {
                            for (i in 1 until stanzas.size) {
                                PreviewStanza(i + 1, stanzas[i])
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = { doSave() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HymnalNavy),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Confirmar y Guardar", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // Quick New Category Dialog
    if (showQuickCreateCategoryDialog) {
        AlertDialog(
            onDismissRequest = {
                showQuickCreateCategoryDialog = false
                quickNewCatName = ""
                quickNewCatError = null
            },
            title = { Text("Nueva Categoría Personalizada") },
            text = {
                Column {
                    Text(
                        text = "Ingresa el nombre de la categoría para agregarla y seleccionarla automáticamente.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = quickNewCatName,
                        onValueChange = {
                            quickNewCatName = it
                            if (it.isNotBlank()) quickNewCatError = null
                        },
                        placeholder = { Text("Ej: Culto de Misiones, Santa Cena...") },
                        singleLine = true,
                        isError = quickNewCatError != null,
                        supportingText = quickNewCatError?.let {
                            { Text(it, color = MaterialTheme.colorScheme.error) }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quick_new_category_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val clean = quickNewCatName.trim()
                        if (clean.isBlank()) {
                            quickNewCatError = "El nombre no puede estar vacío"
                        } else {
                            onCreateCategory(clean, "#1B365D")
                            selectedCategories = selectedCategories + clean
                            showQuickCreateCategoryDialog = false
                            quickNewCatName = ""
                            quickNewCatError = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HymnalNavy)
                ) {
                    Text("Crear y Seleccionar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showQuickCreateCategoryDialog = false
                    quickNewCatName = ""
                    quickNewCatError = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteConfirmDialog && hymnToEdit != null && onDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = { Text("¿Eliminar este himno?") },
            text = { Text("Se eliminará definitivamente el himno #${hymnToEdit.number} \"${hymnToEdit.title}\".") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirmDialog = false
                        onDelete(hymnToEdit)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun PreviewStanza(number: Int, text: String) {
    val clean = text.replaceFirst(Regex("^\\s*\\d+[.\\-)]\\s*"), "")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(HymnalGoldDark.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$number",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = HymnalGoldDark
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = clean,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF2C2216),
            modifier = Modifier.weight(1f)
        )
    }
}
