package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ComparisonItem
import com.example.data.Concept
import com.example.data.CurriculumRepository
import com.example.data.Language
import com.example.data.NavigationTab
import com.example.data.QuizQuestion
import com.example.data.db.AppDatabase
import com.example.data.db.BookmarkedConceptEntity
import com.example.data.db.CompletedConceptEntity
import com.example.data.db.QuizRecordEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CodeCompareViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val dao = db.progressDao()

    // Navigation & Current Screen State
    private val _currentTab = MutableStateFlow(NavigationTab.LEARN)
    val currentTab: StateFlow<NavigationTab> = _currentTab.asStateFlow()

    // Selected Language for Dashboard & Detail
    private val _selectedLanguage = MutableStateFlow(CurriculumRepository.languages[0]) // default Python
    val selectedLanguage: StateFlow<Language> = _selectedLanguage.asStateFlow()

    // Selected Concept for Detail View
    private val _selectedConcept = MutableStateFlow<Concept?>(null)
    val selectedConcept: StateFlow<Concept?> = _selectedConcept.asStateFlow()

    // Dashboard Search & Filtering
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Room Persistent Progress
    val completedConcepts: StateFlow<Set<Pair<String, String>>> = dao.getAllCompleted()
        .combine(MutableStateFlow(Unit)) { list, _ ->
            list.map { it.conceptId to it.languageId }.toSet()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val bookmarkedConcepts: StateFlow<Set<Pair<String, String>>> = dao.getAllBookmarks()
        .combine(MutableStateFlow(Unit)) { list, _ ->
            list.map { it.conceptId to it.languageId }.toSet()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val quizRecords: StateFlow<List<QuizRecordEntity>> = dao.getAllQuizRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Comparison Screen State
    private val _selectedComparisonIndex = MutableStateFlow(0)
    val selectedComparisonIndex: StateFlow<Int> = _selectedComparisonIndex.asStateFlow()

    private val _isComparisonStacked = MutableStateFlow(false)
    val isComparisonStacked: StateFlow<Boolean> = _isComparisonStacked.asStateFlow()

    private val _comparisonActiveLanguages = MutableStateFlow(setOf("python", "java", "cpp", "c"))
    val comparisonActiveLanguages: StateFlow<Set<String>> = _comparisonActiveLanguages.asStateFlow()

    // Quiz State
    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex.asStateFlow()

    private val _selectedOption = MutableStateFlow<Int?>(null)
    val selectedOption: StateFlow<Int?> = _selectedOption.asStateFlow()

    private val _isAnswerSubmitted = MutableStateFlow(false)
    val isAnswerSubmitted: StateFlow<Boolean> = _isAnswerSubmitted.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> = _isQuizFinished.asStateFlow()

    // Actions
    fun setTab(tab: NavigationTab) {
        _currentTab.value = tab
    }

    fun selectLanguage(language: Language) {
        _selectedLanguage.value = language
        _searchQuery.value = ""
        _selectedCategory.value = "All"
    }

    fun selectConcept(concept: Concept?) {
        _selectedConcept.value = concept
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun toggleConceptCompletion(conceptId: String, languageId: String) {
        viewModelScope.launch {
            val key = conceptId to languageId
            if (completedConcepts.value.contains(key)) {
                dao.unmarkCompleted(conceptId, languageId)
            } else {
                dao.markCompleted(CompletedConceptEntity(conceptId, languageId))
            }
        }
    }

    fun toggleBookmark(conceptId: String, languageId: String) {
        viewModelScope.launch {
            val key = conceptId to languageId
            if (bookmarkedConcepts.value.contains(key)) {
                dao.removeBookmark(conceptId, languageId)
            } else {
                dao.addBookmark(BookmarkedConceptEntity(conceptId, languageId))
            }
        }
    }

    fun setComparisonIndex(index: Int) {
        _selectedComparisonIndex.value = index
    }

    fun toggleComparisonLayout() {
        _isComparisonStacked.value = !_isComparisonStacked.value
    }

    fun toggleLanguageInComparison(langId: String) {
        val current = _comparisonActiveLanguages.value.toMutableSet()
        if (current.contains(langId)) {
            if (current.size > 1) { // keep at least one
                current.remove(langId)
            }
        } else {
            current.add(langId)
        }
        _comparisonActiveLanguages.value = current
    }

    fun selectQuizOption(index: Int) {
        if (!_isAnswerSubmitted.value) {
            _selectedOption.value = index
        }
    }

    fun submitQuizAnswer() {
        val selected = _selectedOption.value ?: return
        val currentQ = CurriculumRepository.quizzes.getOrNull(_currentQuizIndex.value) ?: return
        _isAnswerSubmitted.value = true
        if (selected == currentQ.correctIndex) {
            _quizScore.value += 1
        }
    }

    fun nextQuizQuestion() {
        val nextIdx = _currentQuizIndex.value + 1
        if (nextIdx < CurriculumRepository.quizzes.size) {
            _currentQuizIndex.value = nextIdx
            _selectedOption.value = null
            _isAnswerSubmitted.value = false
        } else {
            _isQuizFinished.value = true
            // Save quiz record
            viewModelScope.launch {
                dao.insertQuizRecord(
                    QuizRecordEntity(
                        quizTitle = "Multi-Language Fundamentals Quiz",
                        languageId = "all",
                        score = _quizScore.value,
                        total = CurriculumRepository.quizzes.size
                    )
                )
            }
        }
    }

    fun restartQuiz() {
        _currentQuizIndex.value = 0
        _selectedOption.value = null
        _isAnswerSubmitted.value = false
        _quizScore.value = 0
        _isQuizFinished.value = false
    }

    fun openComparisonForConcept(conceptTitle: String) {
        // Find matching comparison or default to loops
        val idx = CurriculumRepository.comparisons.indexOfFirst {
            conceptTitle.contains(it.title, ignoreCase = true) || it.title.contains(conceptTitle, ignoreCase = true)
        }.coerceAtLeast(0)
        _selectedComparisonIndex.value = idx
        _currentTab.value = NavigationTab.COMPARE
    }
}
