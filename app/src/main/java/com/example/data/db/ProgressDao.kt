package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM completed_concepts")
    fun getAllCompleted(): Flow<List<CompletedConceptEntity>>

    @Query("SELECT * FROM completed_concepts WHERE languageId = :languageId")
    fun getCompletedForLanguage(languageId: String): Flow<List<CompletedConceptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markCompleted(entity: CompletedConceptEntity)

    @Query("DELETE FROM completed_concepts WHERE conceptId = :conceptId AND languageId = :languageId")
    suspend fun unmarkCompleted(conceptId: String, languageId: String)

    @Query("SELECT * FROM bookmarked_concepts")
    fun getAllBookmarks(): Flow<List<BookmarkedConceptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(entity: BookmarkedConceptEntity)

    @Query("DELETE FROM bookmarked_concepts WHERE conceptId = :conceptId AND languageId = :languageId")
    suspend fun removeBookmark(conceptId: String, languageId: String)

    @Query("SELECT * FROM quiz_records ORDER BY timestamp DESC")
    fun getAllQuizRecords(): Flow<List<QuizRecordEntity>>

    @Insert
    suspend fun insertQuizRecord(record: QuizRecordEntity)
}
