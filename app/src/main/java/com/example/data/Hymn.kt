package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hymns")
data class Hymn(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: Int,
    val title: String,
    val category: String = "Alabanza", // Comma-separated categories e.g. "Alabanza, Adoración"
    val tone: String = "",
    val author: String = "",
    val chorus: String = "",
    val lyrics: String = "",
    val scriptureRef: String = "",
    val notes: String = "",
    val isFavorite: Boolean = false,
    val lastReadAt: Long = 0L, // Timestamp of last time user opened/read this hymn
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Checks if this hymn already has text or is waiting for lyrics.
     */
    val hasText: Boolean
        get() = lyrics.isNotBlank() || chorus.isNotBlank()

    /**
     * Returns list of assigned categories.
     */
    fun getCategories(): List<String> {
        if (category.isBlank()) return listOf("General")
        return category.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    /**
     * Checks if this hymn belongs to a given category.
     */
    fun hasCategory(targetCategory: String): Boolean {
        return getCategories().any { it.equals(targetCategory.trim(), ignoreCase = true) }
    }

    /**
     * Splits lyrics into stanzas by double line breaks or clear verse headers.
     */
    fun getStanzas(): List<String> {
        if (lyrics.isBlank()) return emptyList()
        return lyrics.split(Regex("\n\\s*\n"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    /**
     * Obtains the first line actually sung in this hymn ("lo que se va a cantar").
     * Strips stanza numbering, labels, and opening punctuation marks.
     * Example: for "1. ¡Santo! ¡Santo!..." -> returns "Santo! ¡Santo!..."
     * Example: for "1. Señor mi Dios, al contemplar..." -> returns "Señor mi Dios, al contemplar..."
     */
    fun getFirstSungLine(): String {
        val text = lyrics.ifBlank { chorus }.ifBlank { title }
        for (line in text.lines()) {
            val trimmed = line.trim()
            if (trimmed.isEmpty()) continue
            // Strip stanza prefixes like "1.", "1.-", "1)", "Estrofa 1:", "Coro:"
            val withoutNum = trimmed.replace(
                Regex("^(\\d+[.\\-)]|\\(?[0-9]+\\)?|Estrofa\\s*\\d+:?|Coro:?)\\s*", RegexOption.IGNORE_CASE),
                ""
            ).trim()
            // Strip leading quotes or punctuation like ¡, ¿, ", ', «, etc.
            val cleaned = withoutNum.replace(
                Regex("^[«“\"'¿¡(\\[{\\s.,;:\\-]+"),
                ""
            ).trim()
            if (cleaned.isNotEmpty()) {
                return cleaned
            }
        }
        return title.replace(Regex("^[«“\"'¿¡(\\[{\\s.,;:\\-]+"), "").trim()
    }

    /**
     * Obtains the initial letter (A-Z, Ñ, or #) of what is sung ("la primera letra de lo que se va a cantar").
     * Accented vowels (Á, É, Í, Ó, Ú) are normalized to their base vowel for proper Spanish alphabetical indexing.
     */
    fun getFirstSungLetter(): Char {
        val line = getFirstSungLine()
        if (line.isEmpty()) return '#'
        val firstRaw = line.first().uppercaseChar()
        return when (firstRaw) {
            'Á', 'À', 'Â', 'Ä' -> 'A'
            'É', 'È', 'Ê', 'Ë' -> 'E'
            'Í', 'Ì', 'Î', 'Ï' -> 'I'
            'Ó', 'Ò', 'Ô', 'Ö' -> 'O'
            'Ú', 'Ù', 'Û', 'Ü' -> 'U'
            in 'A'..'Z', 'Ñ' -> firstRaw
            else -> if (firstRaw.isLetter()) firstRaw else '#'
        }
    }
}
