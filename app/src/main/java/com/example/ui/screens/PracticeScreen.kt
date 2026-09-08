package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.theme.CodeSmallTextStyle
import com.example.ui.theme.CodeSurface
import com.example.ui.theme.ElectricCobalt
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.MintText
import com.example.ui.theme.PythonEmerald
import com.example.ui.theme.RoseText
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate800
import com.example.ui.theme.SoftMintBg
import com.example.ui.theme.SoftRoseBg
import com.example.ui.theme.SuccessGreen

@Composable
fun PracticeScreen(
    currentQuizIndex: Int,
    selectedOption: Int?,
    isAnswerSubmitted: Boolean,
    score: Int,
    isQuizFinished: Boolean,
    onSelectOption: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions = CurriculumRepository.quizzes
    val totalQuestions = questions.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate50)
            .testTag("practice_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Header Bar
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
                    .padding(horizontal = 20.dp, vertical = 22.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = PythonEmerald.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                PythonEmerald.copy(alpha = 0.4f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = null,
                                    tint = PythonEmerald,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "SYNTAX LAB",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                            }
                        }

                        // Score Pill
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "Score: $score pts",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Interactive Practice Quiz",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Test your mental model of syntax, memory lifetimes, and language nuances.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Slate400,
                            lineHeight = 18.sp
                        )
                    )

                    if (!isQuizFinished) {
                        Spacer(modifier = Modifier.height(16.dp))

                        // Question Counter & Linear Progress
                        val currentProgress = (currentQuizIndex + 1).toFloat() / totalQuestions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Question ${currentQuizIndex + 1} of $totalQuestions",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Text(
                                text = "${(currentProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = PythonEmerald,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { currentProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = PythonEmerald,
                            trackColor = Color.White.copy(alpha = 0.15f)
                        )
                    }
                }
            }
        }

        if (isQuizFinished) {
            // Celebratory End-of-Quiz Score Modal / Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quiz_celebration_card"),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Trophy Icon Container
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFEF3C7)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = "Quiz Completed",
                                    tint = Color(0xFFF59E0B),
                                    modifier = Modifier.size(38.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Quiz Complete!",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MidnightBlue
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            val percentage = (score * 100) / totalQuestions
                            val badgeTitle = when {
                                percentage >= 80 -> "Polyglot Master 🏆"
                                percentage >= 60 -> "Syntax Architect ⚡"
                                else -> "Learning Explorer 🚀"
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = ElectricCobalt.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = badgeTitle,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = ElectricCobalt,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Score Circle / Pill
                            Text(
                                text = "$score / $totalQuestions",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MidnightBlue
                                )
                            )

                            Text(
                                text = "You scored $percentage% across multi-language questions.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Slate500
                                )
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Action Button: Retake
                            Button(
                                onClick = onRestartQuiz,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .testTag("quiz_retake_button"),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ElectricCobalt,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Replay,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Retake Quiz",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Active Quiz Question View
            val currentQ = questions[currentQuizIndex]

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    // Question Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quiz_question_card"),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            // Question Tag / Scope
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Slate100
                                ) {
                                    Text(
                                        text = currentQ.title.uppercase(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Slate600,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.5.sp
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = currentQ.question,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MidnightBlue,
                                    lineHeight = 24.sp
                                )
                            )

                            // Code Puzzle snippet if present
                            if (!currentQ.codeSnippet.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = CodeSurface,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = currentQ.codeSnippet,
                                        style = CodeSmallTextStyle.copy(
                                            color = Color(0xFFF1F5F9),
                                            lineHeight = 18.sp
                                        ),
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "CHOOSE YOUR ANSWER",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Slate500,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Clean Radio-Option Tiles with Animated Selection States
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        currentQ.options.forEachIndexed { index, optionText ->
                            val isSelected = selectedOption == index
                            val isCorrectAnswer = index == currentQ.correctIndex

                            val targetBorderColor = when {
                                !isAnswerSubmitted && isSelected -> ElectricCobalt
                                isAnswerSubmitted && isCorrectAnswer -> SuccessGreen
                                isAnswerSubmitted && isSelected && !isCorrectAnswer -> ErrorRed
                                else -> Slate200
                            }

                            val targetBgColor = when {
                                !isAnswerSubmitted && isSelected -> ElectricCobalt.copy(alpha = 0.08f)
                                isAnswerSubmitted && isCorrectAnswer -> SoftMintBg
                                isAnswerSubmitted && isSelected && !isCorrectAnswer -> SoftRoseBg
                                else -> Color.White
                            }

                            val animatedBorderColor by animateColorAsState(
                                targetValue = targetBorderColor,
                                label = "border_color"
                            )
                            val animatedBgColor by animateColorAsState(
                                targetValue = targetBgColor,
                                label = "bg_color"
                            )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .border(
                                        width = if (isSelected || (isAnswerSubmitted && isCorrectAnswer)) 2.dp else 1.dp,
                                        color = animatedBorderColor,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable(enabled = !isAnswerSubmitted) {
                                        onSelectOption(index)
                                    }
                                    .testTag("quiz_option_$index"),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = animatedBgColor)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Radio State Indicator Icon
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .border(
                                                width = 2.dp,
                                                color = when {
                                                    isAnswerSubmitted && isCorrectAnswer -> SuccessGreen
                                                    isAnswerSubmitted && isSelected && !isCorrectAnswer -> ErrorRed
                                                    isSelected -> ElectricCobalt
                                                    else -> Slate300
                                                },
                                                shape = CircleShape
                                            )
                                            .background(
                                                when {
                                                    isAnswerSubmitted && isCorrectAnswer -> SuccessGreen
                                                    isAnswerSubmitted && isSelected && !isCorrectAnswer -> ErrorRed
                                                    isSelected -> ElectricCobalt
                                                    else -> Color.Transparent
                                                }
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isAnswerSubmitted && isCorrectAnswer) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Correct",
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        } else if (isAnswerSubmitted && isSelected && !isCorrectAnswer) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Incorrect",
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        } else if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.White)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(14.dp))

                                    Text(
                                        text = optionText,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                            color = when {
                                                isAnswerSubmitted && isCorrectAnswer -> MintText
                                                isAnswerSubmitted && isSelected && !isCorrectAnswer -> RoseText
                                                else -> MidnightBlue
                                            }
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Expandable Explanation Tray
                    AnimatedVisibility(
                        visible = isAnswerSubmitted,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        val isCorrect = selectedOption == currentQ.correctIndex
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .testTag("quiz_explanation_tray"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCorrect) SoftMintBg.copy(alpha = 0.5f) else SoftRoseBg.copy(alpha = 0.5f)
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isCorrect) Color(0xFFA7F3D0) else Color(0xFFFECACA)
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Info,
                                        contentDescription = null,
                                        tint = if (isCorrect) PythonEmerald else ErrorRed,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isCorrect) "Correct Answer!" else "Incorrect Choice",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isCorrect) MintText else RoseText
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = currentQ.explanation,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = if (isCorrect) MintText else RoseText,
                                        lineHeight = 20.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Primary Action Button (Submit Answer OR Next Question)
                    if (!isAnswerSubmitted) {
                        Button(
                            onClick = onSubmitAnswer,
                            enabled = selectedOption != null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("quiz_submit_button"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricCobalt,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Check Answer",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    } else {
                        Button(
                            onClick = onNextQuestion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("quiz_next_button"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MidnightBlue,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = if (currentQuizIndex + 1 < totalQuestions) "Next Question" else "View Results",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
