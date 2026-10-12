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
}
