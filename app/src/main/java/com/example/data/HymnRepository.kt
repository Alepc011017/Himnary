package com.example.data

import kotlinx.coroutines.flow.Flow

class HymnRepository(
    private val hymnDao: HymnDao,
    private val categoryDao: CategoryDao
) {

    val allHymns: Flow<List<Hymn>> = hymnDao.getAllHymns()
    val favoriteHymns: Flow<List<Hymn>> = hymnDao.getFavoriteHymns()
    val recentlyReadHymns: Flow<List<Hymn>> = hymnDao.getRecentlyReadHymns(12)
    val allCategories: Flow<List<HymnCategory>> = categoryDao.getAllCategories()

    suspend fun recordHymnRead(id: Int) {
        hymnDao.updateLastRead(id, System.currentTimeMillis())
    }

    suspend fun clearReadingHistory() {
        hymnDao.clearAllRecent()
    }

    fun getHymnById(id: Int): Flow<Hymn?> = hymnDao.getHymnById(id)

    suspend fun getHymnByNumber(number: Int): Hymn? = hymnDao.getHymnByNumber(number)

    fun searchHymns(query: String): Flow<List<Hymn>> = hymnDao.searchHymns(query)

    suspend fun getNextHymnNumber(): Int {
        val maxNumber = hymnDao.getMaxNumber() ?: 0
        return maxNumber + 1
    }

    suspend fun insertHymn(hymn: Hymn): Long {
        return hymnDao.insertHymn(hymn.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun updateHymn(hymn: Hymn) {
        hymnDao.updateHymn(hymn.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun deleteHymn(hymn: Hymn) {
        hymnDao.deleteHymn(hymn)
    }

    suspend fun deleteHymnById(id: Int) {
        hymnDao.deleteHymnById(id)
    }

    suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        hymnDao.updateFavorite(id, isFavorite)
    }

    suspend fun addCategory(name: String, colorHex: String = "#1B365D"): Long {
        val cleanName = name.trim()
        val existing = categoryDao.getCategoryByName(cleanName)
        if (existing != null) return existing.id.toLong()
        return categoryDao.insertCategory(
            HymnCategory(
                name = cleanName,
                isDefault = false,
                colorHex = colorHex
            )
        )
    }

    suspend fun deleteCategory(category: HymnCategory) {
        categoryDao.deleteCategory(category)
    }

    suspend fun checkAndSeedInitialData() {
        if (hymnDao.getCount() == 0) {
            hymnDao.insertAll(SampleHymns.initialHymns)
        }
        if (categoryDao.getCount() == 0) {
            categoryDao.insertAll(SampleHymns.defaultCategories)
        }
    }

    suspend fun resetToSampleData() {
        for (hymn in SampleHymns.initialHymns) {
            hymnDao.insertHymn(hymn)
        }
        categoryDao.insertAll(SampleHymns.defaultCategories)
    }
}
