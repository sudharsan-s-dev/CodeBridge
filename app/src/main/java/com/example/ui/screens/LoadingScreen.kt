package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppLogo
import com.example.ui.theme.ElectricCobalt
import com.example.ui.theme.JavaAmber
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.PythonEmerald
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    onLoadingFinished: () -> Unit,
    modifier: Modifier = Modifier,
    autoProceedDelayMs: Long = 1800L
) {
    // Dynamic progress state
    var progress by remember { mutableFloatStateOf(0f) }
    var currentStatusIndex by remember { mutableStateOf(0) }
    val statusMessages = listOf(
        "Initializing curriculum tracks...",
        "Bridging language paradigms...",
        "Indexing C, C++, Java & Python syntax...",
        "Calibrating side-by-side matrices...",
        "CodeBridge is ready!"
    )

    // Animated glow pulse
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    // Entrance animation
    val enterAlpha = remember { Animatable(0f) }
    val enterScale = remember { Animatable(0.9f) }

    LaunchedEffect(Unit) {
        enterAlpha.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
    }
    LaunchedEffect(Unit) {
        enterScale.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
    }

    // Step progression
    LaunchedEffect(Unit) {
        val stepTime = autoProceedDelayMs / 4
        for (i in 1..4) {
            delay(stepTime)
            progress = i * 0.25f
            currentStatusIndex = i.coerceAtMost(statusMessages.lastIndex)
        }
        delay(250)
        onLoadingFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MidnightBlue,
                        Color(0xFF0D1527),
                        Slate800
                    )
                )
            )
            .testTag("loading_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .alpha(enterAlpha.value)
                .scale(enterScale.value),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ambient pulsing aura around logo
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(160.dp)
            ) {
                // Outer glow disc
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .scale(pulseScale)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    ElectricCobalt.copy(alpha = glowAlpha),
                                    PythonEmerald.copy(alpha = glowAlpha * 0.4f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // The Logo
                AppLogo(
                    size = 110.dp,
                    cornerRadius = 28.dp,
                    elevation = 12.dp,
                    showGlowBorder = true,
                    modifier = Modifier.testTag("loading_screen_logo")
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // App Name
            Text(
                text = "CodeBridge",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 1.2.sp,
                    fontSize = 30.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("loading_screen_title")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // App Tagline / Description
            Text(
                text = "A Programming Language Comparison and Learning Platform",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Slate400,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .testTag("loading_screen_subtitle")
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Language Badges Pill Row
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.06f))
                    .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                LanguageMiniDot("C", Color(0xFF6366F1))
                Spacer(modifier = Modifier.width(12.dp))
                LanguageMiniDot("C++", Color(0xFF818CF8))
                Spacer(modifier = Modifier.width(12.dp))
                LanguageMiniDot("Java", JavaAmber)
                Spacer(modifier = Modifier.width(12.dp))
                LanguageMiniDot("Python", PythonEmerald)
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Progress Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .testTag("loading_screen_progress_bar"),
                    color = ElectricCobalt,
                    trackColor = Slate700,
                    strokeCap = StrokeCap.Round
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Real-time status text
                Text(
                    text = statusMessages[currentStatusIndex],
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Slate400,
                        fontSize = 13.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("loading_screen_status_text")
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Skip / Enter Button for fast access
            Button(
                onClick = onLoadingFinished,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.12f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(48.dp)
                    .testTag("loading_screen_skip_button")
            ) {
                Text(
                    text = "Enter Platform",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun LanguageMiniDot(name: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        )
    }
}
