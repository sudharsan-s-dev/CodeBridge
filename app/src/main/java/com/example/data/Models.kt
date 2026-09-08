package com.example.data

data class Language(
    val id: String,
    val name: String,
    val tagline: String,
    val iconSymbol: String,
    val brandColorHex: Long,
    val lightBgColorHex: Long,
    val description: String,
    val paradigm: String,
    val releaseYear: String,
    val typingSystem: String,
    val memoryModel: String
)

data class Concept(
    val id: String,
    val languageId: String,
    val title: String,
    val category: String, // "Fundamentals", "Control Flow", "Data Structures", "OOP & Memory"
    val difficulty: String, // "Beginner", "Intermediate", "Advanced"
    val estimatedMinutes: Int,
    val shortSummary: String,
    val detailedExplanation: String,
    val coreTakeaways: List<String>,
    val codeSnippet: String,
    val expectedOutput: String,
    val gotchas: List<String>
)

data class ComparisonItem(
    val conceptId: String,
    val title: String,
    val description: String,
    val cSnippet: String,
    val cppSnippet: String,
    val javaSnippet: String,
    val pythonSnippet: String,
    val similarities: List<String>,
    val differences: List<String>,
    val paradigmVerdict: String
)

data class QuizQuestion(
    val id: String,
    val title: String,
    val languageScope: String, // "c", "cpp", "java", "python", "multi"
    val question: String,
    val codeSnippet: String? = null,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

enum class NavigationTab {
    LEARN,
    COMPARE,
    PRACTICE,
    BOOKMARKS
}
