package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CurriculumRepository
import com.example.data.Language
import com.example.data.NavigationTab
import com.example.ui.components.AppLogo
import com.example.ui.components.LanguageCard
import com.example.ui.theme.ElectricCobalt
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.PythonEmerald
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate800
import com.example.ui.theme.SoftMintBg

@Composable
fun HomeScreen(
    onSelectLanguage: (Language) -> Unit,
    onNavigateTab: (NavigationTab) -> Unit,
    completedConcepts: Set<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    val totalLanguages = CurriculumRepository.languages.size
    val totalConcepts = CurriculumRepository.concepts.size
    val totalCompleted = completedConcepts.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate50)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Welcoming Hero Header with Deep Midnight Blue Container
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(MidnightBlue, Slate800)
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 28.dp)
            ) {
                Column {
                    // Top App Pill Badge with Logo
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = ElectricCobalt.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                ElectricCobalt.copy(alpha = 0.4f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AppLogo(
                                    size = 22.dp,
                                    cornerRadius = 6.dp,
                                    elevation = 0.dp,
                                    showGlowBorder = false
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "CodeBridge • v1.0",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        // Completed Progress Capsule
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = SoftMintBg
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(PythonEmerald)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "$totalCompleted/$totalConcepts Done",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFF065F46),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Main Welcoming Title with CodeBridge Logo Highlight
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AppLogo(
                            size = 56.dp,
                            cornerRadius = 16.dp,
                            elevation = 6.dp,
                            showGlowBorder = true
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "CodeBridge",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 28.sp
                                )
                            )
                            Text(
                                text = "Language Comparison & Learning",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = ElectricCobalt.copy(alpha = 0.9f),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "A Programming Language Comparison and Learning Platform bridging C, C++, Java, and Python syntax, paradigms, and execution.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Slate400,
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }

        // Section Title: Core Languages Grid
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp, bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CORE LANGUAGES",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Slate500,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "4 Tracks",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ElectricCobalt,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // Dynamic 2x2 Grid of Gorgeous Floating Card Modules
        item {
            val languages = CurriculumRepository.languages
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Row 1: C and C++
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    val langC = languages.first { it.id == "c" }
                    val completedC = completedConcepts.count { it.second == "c" }
                    val totalC = CurriculumRepository.concepts.count { it.languageId == "c" }
                    LanguageCard(
                        language = langC,
                        completedCount = completedC,
                        totalCount = totalC,
                        onClick = { onSelectLanguage(langC) },
                        modifier = Modifier.weight(1f)
                    )

                    val langCpp = languages.first { it.id == "cpp" }
                    val completedCpp = completedConcepts.count { it.second == "cpp" }
                    val totalCpp = CurriculumRepository.concepts.count { it.languageId == "cpp" }
                    LanguageCard(
                        language = langCpp,
                        completedCount = completedCpp,
                        totalCount = totalCpp,
                        onClick = { onSelectLanguage(langCpp) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 2: Java and Python
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    val langJava = languages.first { it.id == "java" }
                    val completedJava = completedConcepts.count { it.second == "java" }
                    val totalJava = CurriculumRepository.concepts.count { it.languageId == "java" }
                    LanguageCard(
                        language = langJava,
                        completedCount = completedJava,
                        totalCount = totalJava,
                        onClick = { onSelectLanguage(langJava) },
                        modifier = Modifier.weight(1f)
                    )

                    val langPython = languages.first { it.id == "python" }
                    val completedPython = completedConcepts.count { it.second == "python" }
                    val totalPython = CurriculumRepository.concepts.count { it.languageId == "python" }
                    LanguageCard(
                        language = langPython,
                        completedCount = completedPython,
                        totalCount = totalPython,
                        onClick = { onSelectLanguage(langPython) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Quick Feature Cards: Compare & Practice
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "EXPLORE & PRACTICE",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Slate500,
                        letterSpacing = 1.sp
                    )
                )

                // Feature Banner 1: Side-by-Side Comparison
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onNavigateTab(NavigationTab.COMPARE) }
                        .testTag("home_compare_banner"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(ElectricCobalt.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.CompareArrows,
                                contentDescription = null,
                                tint = ElectricCobalt,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Side-by-Side Comparison",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MidnightBlue
                                )
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Inspect Loops, Memory, and Classes directly across all 4 languages.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Slate500,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }
                }

                // Feature Banner 2: Interactive Quiz Practice
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onNavigateTab(NavigationTab.PRACTICE) }
                        .testTag("home_quiz_banner"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(PythonEmerald.copy(alpha = 0.14f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = null,
                                tint = PythonEmerald,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Interactive Syntax Quiz",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MidnightBlue
                                    )
                                )
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = Color(0xFFF59E0B),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Test your grasp with code puzzle challenges and explanation trays.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Slate500,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
