package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.KeratonGold
import com.example.ui.theme.KeratonGoldLight
import com.example.ui.theme.SoganDark
import com.example.ui.theme.SoganPrimary

/**
 * Reusable Royal Javanese Header Container with authentic Sogan gradient,
 * high-definition Batik & Keris watermark texture, and golden Keraton ornamental trim.
 */
@Composable
fun JavaneseHeaderBanner(
    modifier: Modifier = Modifier,
    contentPaddingBottom: Dp = 14.dp,
    showBottomOrnament: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF26140D), // Deep Royal Sogan Ebony
                        SoganDark,         // 0xFF3E2723
                        SoganPrimary,      // 0xFF4E342E
                        Color(0xFF331D16)  // Warm Sogan Wood
                    )
                )
            )
    ) {
        // 1. High-Fidelity Javanese Batik & Keris Watermark Texture Layer
        Image(
            painter = painterResource(id = R.drawable.img_batik_keris_header),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.36f,
            modifier = Modifier.matchParentSize()
        )

        // 2. Gentle Sogan Scrim to preserve high contrast and guarantee typography legibility
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF26140D).copy(alpha = 0.40f),
                            SoganDark.copy(alpha = 0.25f),
                            Color(0xFF331D16).copy(alpha = 0.55f),
                            Color(0xFF1E0E08).copy(alpha = 0.75f)
                        )
                    )
                )
        )

        // 3. Artistic Keris Luk & Batik Decorative Watermark Accent (Canvas)
        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            val width = size.width
            val height = size.height

            // A. Stylized Keris Luk (Wavy Blade) Silhouette Watermark on the right side
            val kerisPath = Path().apply {
                val startX = width * 0.88f
                val startY = height * 0.05f
                moveTo(startX, startY)

                // Tip of the Keris blade
                lineTo(startX + 6f, startY + 12f)

                // Luk 1
                cubicTo(
                    startX + 28f, startY + 45f,
                    startX + 32f, startY + 65f,
                    startX + 10f, startY + 95f
                )
                // Luk 2
                cubicTo(
                    startX - 18f, startY + 125f,
                    startX - 22f, startY + 145f,
                    startX + 8f, startY + 175f
                )
                // Luk 3
                cubicTo(
                    startX + 26f, startY + 205f,
                    startX + 28f, startY + 225f,
                    startX + 6f, startY + 255f
                )
                // Luk 4
                cubicTo(
                    startX - 16f, startY + 285f,
                    startX - 18f, startY + 305f,
                    startX + 10f, startY + 335f
                )
                // Gonjo (Widening base of blade)
                lineTo(startX + 38f, startY + 348f)
                lineTo(startX - 22f, startY + 352f)
                lineTo(startX - 12f, startY + 365f)
                // Keris Hilt (Danganan / Ukiran)
                cubicTo(
                    startX - 25f, startY + 380f,
                    startX - 30f, startY + 400f,
                    startX - 18f, startY + 420f
                )
                cubicTo(
                    startX + 2f, startY + 410f,
                    startX + 8f, startY + 390f,
                    startX, startY + 365f
                )
                close()
            }

            drawPath(
                path = kerisPath,
                color = KeratonGold.copy(alpha = 0.16f)
            )

            // Keris Pamor Lines (Central Damascus ripples)
            val pamorPath = Path().apply {
                val startX = width * 0.88f
                val startY = height * 0.10f
                moveTo(startX + 3f, startY)
                cubicTo(
                    startX + 18f, startY + 50f,
                    startX - 8f, startY + 130f,
                    startX + 12f, startY + 210f
                )
                cubicTo(
                    startX - 6f, startY + 270f,
                    startX + 8f, startY + 320f,
                    startX + 2f, startY + 350f
                )
            }
            drawPath(
                path = pamorPath,
                color = KeratonGoldLight.copy(alpha = 0.22f),
                style = Stroke(width = 2.5f, cap = StrokeCap.Round)
            )

            // B. Classical Javanese Batik Kawung & Circular Medallion Watermark Accents
            val kawungRadius = 45f
            val kawungCenterX = width * 0.12f
            val kawungCenterY = height * 0.35f

            // 4 intersecting petals of Kawung
            val kawungPath = Path().apply {
                // Top petal
                moveTo(kawungCenterX, kawungCenterY)
                cubicTo(kawungCenterX - 18f, kawungCenterY - 35f, kawungCenterX + 18f, kawungCenterY - 35f, kawungCenterX, kawungCenterY)
                // Bottom petal
                cubicTo(kawungCenterX - 18f, kawungCenterY + 35f, kawungCenterX + 18f, kawungCenterY + 35f, kawungCenterX, kawungCenterY)
                // Left petal
                cubicTo(kawungCenterX - 35f, kawungCenterY - 18f, kawungCenterX - 35f, kawungCenterY + 18f, kawungCenterX, kawungCenterY)
                // Right petal
                cubicTo(kawungCenterX + 35f, kawungCenterY - 18f, kawungCenterX + 35f, kawungCenterY + 18f, kawungCenterX, kawungCenterY)
            }
            drawPath(
                path = kawungPath,
                color = KeratonGold.copy(alpha = 0.12f),
                style = Stroke(width = 1.8f)
            )
        }

        // 4. Header Foreground Content Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = contentPaddingBottom)
        ) {
            content()
        }

        // 5. Royal Keraton Golden Ornamental Trim at the bottom edge
        if (showBottomOrnament) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                // Gold Gradient Line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    KeratonGold.copy(alpha = 0.4f),
                                    KeratonGoldLight,
                                    Color(0xFFFFE082),
                                    KeratonGoldLight,
                                    KeratonGold.copy(alpha = 0.4f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Center Gold Diamond & Keris Emblem Ornament
                Canvas(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(8.dp)
                        .fillMaxWidth()
                ) {
                    val cx = size.width / 2f
                    val cy = size.height / 2f

                    // Center diamond
                    val diamond = Path().apply {
                        moveTo(cx, cy - 3.5f)
                        lineTo(cx + 6f, cy)
                        lineTo(cx, cy + 3.5f)
                        lineTo(cx - 6f, cy)
                        close()
                    }
                    drawPath(diamond, color = KeratonGoldLight)

                    // Side decorative pips
                    drawCircle(color = KeratonGold, radius = 1.5f, center = androidx.compose.ui.geometry.Offset(cx - 16f, cy))
                    drawCircle(color = KeratonGold, radius = 1.5f, center = androidx.compose.ui.geometry.Offset(cx + 16f, cy))
                    drawCircle(color = KeratonGold.copy(alpha = 0.6f), radius = 1.2f, center = androidx.compose.ui.geometry.Offset(cx - 28f, cy))
                    drawCircle(color = KeratonGold.copy(alpha = 0.6f), radius = 1.2f, center = androidx.compose.ui.geometry.Offset(cx + 28f, cy))
                }
            }
        }
    }
}
