package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CobaltDark
import com.example.ui.theme.CobaltLight
import com.example.ui.theme.ElectricCobalt
import com.example.ui.theme.MidnightBlue

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    cornerRadius: Dp = 14.dp,
    elevation: Dp = 4.dp,
    showGlowBorder: Boolean = true
) {
    val shape = RoundedCornerShape(cornerRadius)
    val borderModifier = if (showGlowBorder) {
        Modifier.border(
            width = 1.5.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    ElectricCobalt.copy(alpha = 0.8f),
                    CobaltLight.copy(alpha = 0.4f),
                    Color(0xFF10B981).copy(alpha = 0.5f)
                )
            ),
            shape = shape
        )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .size(size)
            .shadow(elevation, shape = shape, clip = false)
            .clip(shape)
            .background(MidnightBlue)
            .then(borderModifier)
            .testTag("app_logo_image"),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_codebridge_logo),
            contentDescription = "CodeBridge Logo",
            modifier = Modifier.size(size),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun AppBrandBadge(
    modifier: Modifier = Modifier,
    logoSize: Dp = 32.dp
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(24.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppLogo(
            size = logoSize,
            cornerRadius = 8.dp,
            elevation = 0.dp,
            showGlowBorder = false
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "CodeBridge",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        )
    }
}
