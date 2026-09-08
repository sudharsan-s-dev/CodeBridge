package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_concepts", primaryKeys = ["conceptId", "languageId"])
data class CompletedConceptEntity(
    val conceptId: String,
    val languageId: String,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarked_concepts", primaryKeys = ["conceptId", "languageId"])
data class BookmarkedConceptEntity(
    val conceptId: String,
    val languageId: String,
    val bookmarkedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_records")
data class QuizRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val quizTitle: String,
    val languageId: String,
    val score: Int,
    val total: Int,
    val timestamp: Long = System.currentTimeMillis()
)
