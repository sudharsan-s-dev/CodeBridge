package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Language
import com.example.data.NavigationTab
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.ComparisonScreen
import com.example.ui.screens.ConceptDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LanguageDashboardScreen
import com.example.ui.screens.LoadingScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.theme.CodeCompareTheme
import com.example.ui.theme.ElectricCobalt
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.viewmodel.CodeCompareViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: CodeCompareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeCompareTheme {
                CodeCompareApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CodeCompareApp(viewModel: CodeCompareViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val selectedConcept by viewModel.selectedConcept.collectAsState()
    val completedConcepts by viewModel.completedConcepts.collectAsState()
    val bookmarkedConcepts by viewModel.bookmarkedConcepts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val comparisonIndex by viewModel.selectedComparisonIndex.collectAsState()
    val isComparisonStacked by viewModel.isComparisonStacked.collectAsState()
    val activeLanguages by viewModel.comparisonActiveLanguages.collectAsState()

    val currentQuizIndex by viewModel.currentQuizIndex.collectAsState()
    val selectedOption by viewModel.selectedOption.collectAsState()
    val isAnswerSubmitted by viewModel.isAnswerSubmitted.collectAsState()
    val quizScore by viewModel.quizScore.collectAsState()
    val isQuizFinished by viewModel.isQuizFinished.collectAsState()

    // Screen state within Learn tab: Home vs Language Dashboard
    var isViewingDashboard by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(true) }

    AnimatedContent(
        targetState = isLoading,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "loading_transition"
    ) { loading ->
        if (loading) {
            LoadingScreen(
                onLoadingFinished = { isLoading = false }
            )
        } else {
            // Intercept back presses for nested navigation
            BackHandler(enabled = selectedConcept != null || (currentTab == NavigationTab.LEARN && isViewingDashboard)) {
                if (selectedConcept != null) {
                    viewModel.selectConcept(null)
                } else if (isViewingDashboard) {
                    isViewingDashboard = false
                }
            }

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("code_compare_root_scaffold"),
                contentWindowInsets = WindowInsets.safeDrawing,
                bottomBar = {
                    // Tab bar/bottom navigation for "Learn", "Compare", and "Practice"
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 8.dp,
                        modifier = Modifier.testTag("main_bottom_nav_bar")
                    ) {
                // Tab 1: Learn
                NavigationBarItem(
                    selected = currentTab == NavigationTab.LEARN,
                    onClick = {
                        viewModel.setTab(NavigationTab.LEARN)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "Learn",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Learn",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (currentTab == NavigationTab.LEARN) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricCobalt,
                        selectedTextColor = ElectricCobalt,
                        indicatorColor = ElectricCobalt.copy(alpha = 0.12f),
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate500
                    ),
                    modifier = Modifier.testTag("nav_tab_learn")
                )

                // Tab 2: Compare
                NavigationBarItem(
                    selected = currentTab == NavigationTab.COMPARE,
                    onClick = {
                        viewModel.setTab(NavigationTab.COMPARE)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.CompareArrows,
                            contentDescription = "Compare",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Compare",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (currentTab == NavigationTab.COMPARE) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricCobalt,
                        selectedTextColor = ElectricCobalt,
                        indicatorColor = ElectricCobalt.copy(alpha = 0.12f),
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate500
                    ),
                    modifier = Modifier.testTag("nav_tab_compare")
                )

                // Tab 3: Practice
                NavigationBarItem(
                    selected = currentTab == NavigationTab.PRACTICE,
                    onClick = {
                        viewModel.setTab(NavigationTab.PRACTICE)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = "Practice",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Practice",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (currentTab == NavigationTab.PRACTICE) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricCobalt,
                        selectedTextColor = ElectricCobalt,
                        indicatorColor = ElectricCobalt.copy(alpha = 0.12f),
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate500
                    ),
                    modifier = Modifier.testTag("nav_tab_practice")
                )

                // Tab 4: Saved / Bookmarks
                NavigationBarItem(
                    selected = currentTab == NavigationTab.BOOKMARKS,
                    onClick = {
                        viewModel.setTab(NavigationTab.BOOKMARKS)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Saved",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Saved",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (currentTab == NavigationTab.BOOKMARKS) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricCobalt,
                        selectedTextColor = ElectricCobalt,
                        indicatorColor = ElectricCobalt.copy(alpha = 0.12f),
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate500
                    ),
                    modifier = Modifier.testTag("nav_tab_saved")
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "tab_transition"
            ) { tab ->
                when (tab) {
                    NavigationTab.LEARN -> {
                        if (selectedConcept != null) {
                            // Concept Detail & Learning Screen
                            val concept = selectedConcept!!
                            ConceptDetailScreen(
                                concept = concept,
                                language = selectedLanguage,
                                onBack = { viewModel.selectConcept(null) },
                                isCompleted = completedConcepts.contains(concept.id to selectedLanguage.id),
                                isBookmarked = bookmarkedConcepts.contains(concept.id to selectedLanguage.id),
                                onToggleCompleted = {
                                    viewModel.toggleConceptCompletion(concept.id, selectedLanguage.id)
                                },
                                onToggleBookmarked = {
                                    viewModel.toggleBookmark(concept.id, selectedLanguage.id)
                                },
                                onCompareWithOtherLanguages = { conceptTitle ->
                                    viewModel.openComparisonForConcept(conceptTitle)
                                }
                            )
                        } else if (isViewingDashboard) {
                            // Language Dashboard Screen
                            LanguageDashboardScreen(
                                language = selectedLanguage,
                                onBackToHome = { isViewingDashboard = false },
                                onSelectConcept = { concept ->
                                    viewModel.selectConcept(concept)
                                },
                                onSwitchLanguage = { newLang ->
                                    viewModel.selectLanguage(newLang)
                                },
                                completedConcepts = completedConcepts,
                                searchQuery = searchQuery,
                                onSearchQueryChange = { viewModel.setSearchQuery(it) },
                                selectedCategory = selectedCategory,
                                onSelectCategory = { viewModel.setSelectedCategory(it) }
                            )
                        } else {
                            // Home / Language Selection Screen
                            HomeScreen(
                                onSelectLanguage = { lang ->
                                    viewModel.selectLanguage(lang)
                                    isViewingDashboard = true
                                },
                                onNavigateTab = { targetTab ->
                                    viewModel.setTab(targetTab)
                                },
                                completedConcepts = completedConcepts
                            )
                        }
                    }

                    NavigationTab.COMPARE -> {
                        ComparisonScreen(
                            selectedIndex = comparisonIndex,
                            onSelectComparisonIndex = { viewModel.setComparisonIndex(it) },
                            isStacked = isComparisonStacked,
                            onToggleStacked = { viewModel.toggleComparisonLayout() },
                            activeLanguages = activeLanguages,
                            onToggleLanguage = { viewModel.toggleLanguageInComparison(it) }
                        )
                    }

                    NavigationTab.PRACTICE -> {
                        PracticeScreen(
                            currentQuizIndex = currentQuizIndex,
                            selectedOption = selectedOption,
                            isAnswerSubmitted = isAnswerSubmitted,
                            score = quizScore,
                            isQuizFinished = isQuizFinished,
                            onSelectOption = { viewModel.selectQuizOption(it) },
                            onSubmitAnswer = { viewModel.submitQuizAnswer() },
                            onNextQuestion = { viewModel.nextQuizQuestion() },
                            onRestartQuiz = { viewModel.restartQuiz() }
                        )
                    }

                    NavigationTab.BOOKMARKS -> {
                        BookmarksScreen(
                            bookmarkedKeys = bookmarkedConcepts,
                            completedKeys = completedConcepts,
                            onSelectConcept = { concept, language ->
                                viewModel.selectLanguage(language)
                                viewModel.selectConcept(concept)
                                viewModel.setTab(NavigationTab.LEARN)
                            }
                        )
                    }
                }
            }
        }
    }
}
}
}

