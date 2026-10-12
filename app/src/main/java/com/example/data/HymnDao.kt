package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HymnDao {
    @Query("SELECT * FROM hymns ORDER BY number ASC")
    fun getAllHymns(): Flow<List<Hymn>>

    @Query("SELECT * FROM hymns WHERE isFavorite = 1 ORDER BY number ASC")
    fun getFavoriteHymns(): Flow<List<Hymn>>

    @Query("SELECT * FROM hymns WHERE id = :id LIMIT 1")
    fun getHymnById(id: Int): Flow<Hymn?>

    @Query("SELECT * FROM hymns WHERE number = :number LIMIT 1")
    suspend fun getHymnByNumber(number: Int): Hymn?

    @Query("SELECT * FROM hymns WHERE number = :number LIMIT 1")
    fun getHymnByNumberFlow(number: Int): Flow<Hymn?>

    @Query("SELECT MAX(number) FROM hymns")
    suspend fun getMaxNumber(): Int?

    @Query("SELECT COUNT(*) FROM hymns")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHymn(hymn: Hymn): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hymns: List<Hymn>)

    @Update
    suspend fun updateHymn(hymn: Hymn)

    @Delete
    suspend fun deleteHymn(hymn: Hymn)

    @Query("DELETE FROM hymns WHERE id = :id")
    suspend fun deleteHymnById(id: Int)

    @Query("UPDATE hymns SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("""
        SELECT * FROM hymns 
        WHERE title LIKE '%' || :query || '%' 
           OR CAST(number AS TEXT) LIKE '%' || :query || '%' 
           OR lyrics LIKE '%' || :query || '%' 
           OR chorus LIKE '%' || :query || '%'
           OR category LIKE '%' || :query || '%'
        ORDER BY number ASC
    """)
    fun searchHymns(query: String): Flow<List<Hymn>>
}
