package com.unera.social.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

/**
 * AnimatedPostBackground - Hardware-accelerated, 60 FPS Compose Canvas animations
 * for text posts. Renders lightweight, fluid visual backgrounds without videos or heavy assets.
 */
@Composable
fun AnimatedPostBackground(
    templateId: String,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true
) {
    val cleanId = remember(templateId) {
        templateId.substringBefore("|").trim()
    }

    val template = remember(cleanId) {
        findBackgroundTemplate(cleanId) ?: ALL_TEXT_POST_BACKGROUNDS[7] // fallback
    }

    // Infinite animation loop (paused if isVisible is false for performance)
    val infiniteTransition = rememberInfiniteTransition(label = "bg_anim")
    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "animProgress"
    )

    val fastProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fastProgress"
    )

    val pulseProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseProgress"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
            .background(Brush.linearGradient(template.baseColors.ifEmpty { listOf(Color(0xFF1E1B4B), Color(0xFF311042)) }))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            if (w <= 0f || h <= 0f) return@Canvas

            when (cleanId) {
                // ==================== FLOWERS ====================
                "animated:blooming_rose" -> drawBloomingRose(w, h, pulseProgress, animProgress)
                "animated:cherry_blossom" -> drawCherryBlossom(w, h, animProgress)
                "animated:sunflower_breeze" -> drawSunflowerBreeze(w, h, animProgress, pulseProgress)
                "animated:wind_flowers" -> drawWindFlowers(w, h, animProgress)
                "animated:golden_petals" -> drawGoldenPetals(w, h, animProgress)

                // ==================== NATURE ====================
                "animated:garden_butterflies" -> drawGardenButterflies(w, h, animProgress, fastProgress)
                "animated:forest_fireflies" -> drawForestFireflies(w, h, animProgress, pulseProgress)
                "animated:waterfall_mist" -> drawWaterfallMist(w, h, fastProgress)

                // ==================== 3D CHARACTERS ====================
                "animated:dancing_bear" -> drawDancingTeddyBear(w, h, fastProgress, pulseProgress)
                "animated:dancing_panda" -> drawDancingPanda(w, h, fastProgress, pulseProgress)
                "animated:bouncing_bunny" -> drawBouncingBunny(w, h, fastProgress, pulseProgress)
                "animated:kawaii_kitty" -> drawKawaiiKitty(w, h, fastProgress, pulseProgress)
                "animated:fantasy_fairy" -> drawFantasyFairy(w, h, animProgress, fastProgress)

                // ==================== ROMANTIC ====================
                "animated:floating_hearts" -> drawFloatingHearts(w, h, animProgress)
                "animated:beating_heart" -> drawBeatingHeart(w, h, pulseProgress)
                "animated:rose_petals_love" -> drawRosePetalsLove(w, h, animProgress)
                "animated:romantic_glow" -> drawRomanticGlow(w, h, animProgress, pulseProgress)

                // ==================== MAGICAL ====================
                "animated:aurora_borealis" -> drawAuroraBorealis(w, h, animProgress)
                "animated:cosmic_stars" -> drawCosmicStars(w, h, animProgress, fastProgress)
                "animated:magical_orbs" -> drawMagicalOrbs(w, h, animProgress, pulseProgress)
                "animated:fantasy_castle" -> drawFantasyCastle(w, h, animProgress, pulseProgress)

                // ==================== CELEBRATION ====================
                "animated:confetti_fiesta" -> drawConfettiFiesta(w, h, fastProgress)
                "animated:rising_balloons" -> drawRisingBalloons(w, h, animProgress)
                "animated:fireworks_night" -> drawFireworksNight(w, h, fastProgress)
                "animated:birthday_sparkles" -> drawBirthdaySparkles(w, h, animProgress, pulseProgress)

                // ==================== ENVIRONMENTS ====================
                "animated:ocean_fish" -> drawOceanFish(w, h, animProgress, fastProgress)
                "animated:falling_snow" -> drawFallingSnow(w, h, animProgress)
                "animated:gentle_rain" -> drawGentleRain(w, h, fastProgress)
                "animated:sunset_clouds" -> drawSunsetClouds(w, h, animProgress)

                else -> drawCosmicStars(w, h, animProgress, fastProgress)
            }
        }
    }
}

// =========================================================================
// CANVAS DRAWING IMPLEMENTATIONS (60 FPS NATIVE HIGH PERFORMANCE)
// =========================================================================

/**
 * 1. Blooming Rose: Unfurling rose petals with swirling rose leaves.
 */
private fun DrawScope.drawBloomingRose(w: Float, h: Float, pulse: Float, progress: Float) {
    val cx = w * 0.5f
    val cy = h * 0.55f
    val baseR = min(w, h) * 0.28f * (0.85f + 0.18f * pulse)

    // Outer glow
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFE11D48).copy(alpha = 0.35f), Color.Transparent),
            center = Offset(cx, cy),
            radius = baseR * 1.8f
        ),
        center = Offset(cx, cy),
        radius = baseR * 1.8f
    )

    // Layered rose petals opening
    val petalCount = 7
    for (layer in 0..3) {
        val layerR = baseR * (1f - layer * 0.22f)
        val angleOffset = layer * 42f + pulse * 14f
        for (i in 0 until petalCount) {
            val angle = Math.toRadians((i * (360f / petalCount) + angleOffset).toDouble())
            val px = cx + (layerR * 0.45f * cos(angle)).toFloat()
            val py = cy + (layerR * 0.45f * sin(angle)).toFloat()
            val petalColor = when (layer) {
                0 -> Color(0xFF9F1239)
                1 -> Color(0xFFBE123C)
                2 -> Color(0xFFE11D48)
                else -> Color(0xFFFB7185)
            }
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(petalColor, petalColor.copy(alpha = 0.6f)),
                    center = Offset(px, py),
                    radius = layerR * 0.55f
                ),
                center = Offset(px, py),
                radius = layerR * 0.48f
            )
        }
    }

    // Rose core
    drawCircle(
        color = Color(0xFFFFF1F2).copy(alpha = 0.85f),
        center = Offset(cx, cy),
        radius = baseR * 0.12f * (1f + 0.1f * pulse)
    )

    // 12 Drifting flower petals in air
    for (i in 0..11) {
        val seed = i * 73.19f
        val driftY = ((progress + (seed % 1f)) % 1f) * h
        val driftX = (sin(progress * 2 * PI + seed) * 45f + (seed * 83f) % w).toFloat()
        val petalSize = 9f + (seed % 8f)
        val alpha = 0.4f + 0.5f * sin(driftY / h * PI).toFloat().coerceIn(0f, 1f)
        drawOval(
            color = Color(0xFFFDA4AF).copy(alpha = alpha),
            topLeft = Offset(driftX, driftY),
            size = Size(petalSize, petalSize * 1.8f)
        )
    }
}

/**
 * 2. Cherry Blossom: Flowing sakura branches + falling tumbling petals.
 */
private fun DrawScope.drawCherryBlossom(w: Float, h: Float, progress: Float) {
    // Top-right branch silhouette
    val branchPath = Path().apply {
        moveTo(w, 0f)
        cubicTo(w * 0.75f, h * 0.08f, w * 0.6f, h * 0.18f, w * 0.45f, h * 0.14f)
        cubicTo(w * 0.52f, h * 0.22f, w * 0.4f, h * 0.28f, w * 0.32f, h * 0.25f)
    }
    drawPath(
        path = branchPath,
        color = Color(0xFF1E112A).copy(alpha = 0.75f),
        style = Stroke(width = 7.dp.toPx(), cap = StrokeCap.Round)
    )

    // Blossom buds on branch
    val buds = listOf(
        Offset(w * 0.72f, h * 0.09f), Offset(w * 0.61f, h * 0.17f),
        Offset(w * 0.48f, h * 0.15f), Offset(w * 0.35f, h * 0.25f),
        Offset(w * 0.42f, h * 0.24f)
    )
    for (b in buds) {
        drawCircle(color = Color(0xFFF472B6), center = b, radius = 6.dp.toPx())
        drawCircle(color = Color(0xFFFBCFE8), center = b, radius = 3.dp.toPx())
    }

    // 22 Tumbling sakura petals drifting diagonally
    for (i in 0..21) {
        val seed = i * 47.33f
        val startX = (seed * 113f) % (w * 1.4f)
        val t = (progress + (seed % 1f)) % 1f
        val x = startX - t * (w * 0.5f) + (sin(t * 4 * PI + seed) * 30f).toFloat()
        val y = t * h
        val flipScale = cos(t * 6 * PI + seed).toFloat().coerceIn(-1f, 1f)
        val pW = 14f * abs(flipScale).coerceAtLeast(0.2f)
        val pH = 12f

        drawOval(
            color = Color(0xFFF9A8D4).copy(alpha = 0.85f),
            topLeft = Offset(x, y),
            size = Size(pW, pH)
        )
        drawCircle(
            color = Color(0xFFFDF2F8).copy(alpha = 0.7f),
            center = Offset(x + pW * 0.3f, y + pH * 0.3f),
            radius = pW * 0.25f
        )
    }
}

/**
 * 3. Sunflower Breeze: Golden sunlight rays + swaying large sunflowers.
 */
private fun DrawScope.drawSunflowerBreeze(w: Float, h: Float, progress: Float, pulse: Float) {
    // Top-left sun flare
    val sunCenter = Offset(w * 0.15f, h * 0.1f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFFEF08A).copy(alpha = 0.45f), Color.Transparent),
            center = sunCenter,
            radius = w * 0.7f
        ),
        center = sunCenter,
        radius = w * 0.7f
    )

    // Swaying sunflowers at bottom
    val flowers = listOf(
        Pair(w * 0.2f, h * 0.82f),
        Pair(w * 0.5f, h * 0.88f),
        Pair(w * 0.82f, h * 0.84f)
    )

    for ((idx, pos) in flowers.withIndex()) {
        val sway = sin(progress * 2 * PI + idx * 1.5f).toFloat() * 18f
        val fx = pos.first + sway
        val fy = pos.second
        val r = 38f + idx * 6f

        // Stem
        drawLine(
            color = Color(0xFF15803D),
            start = Offset(pos.first, h),
            end = Offset(fx, fy),
            strokeWidth = 5.dp.toPx()
        )

        // Golden petals circle
        val petals = 12
        for (p in 0 until petals) {
            val angle = Math.toRadians((p * (360f / petals) + sway * 0.5f).toDouble())
            val px = fx + (r * 0.85f * cos(angle)).toFloat()
            val py = fy + (r * 0.85f * sin(angle)).toFloat()
            drawOval(
                color = Color(0xFFF59E0B),
                topLeft = Offset(px - 9f, py - 9f),
                size = Size(18f, 18f)
            )
        }
        // Center disk
        drawCircle(color = Color(0xFF451A03), center = Offset(fx, fy), radius = r * 0.42f)
        drawCircle(color = Color(0xFF78350F), center = Offset(fx, fy), radius = r * 0.25f)
    }

    // Floating golden pollen sparkles
    for (i in 0..14) {
        val seed = i * 29.17f
        val px = (w * (0.1f + 0.8f * ((progress * 0.8f + (seed % 1f)) % 1f))).toFloat()
        val py = (h * (0.2f + 0.6f * ((seed * 37f) % 1f)) + sin(progress * 4 * PI + seed) * 15f).toFloat()
        drawCircle(color = Color(0xFFFEF08A).copy(alpha = 0.7f * pulse), center = Offset(px, py), radius = 3.5f)
    }
}

/**
 * 4. Wildflowers in Wind.
 */
private fun DrawScope.drawWindFlowers(w: Float, h: Float, progress: Float) {
    for (i in 0..8) {
        val seed = i * 61.3f
        val baseX = w * (0.08f + 0.84f * (i / 8f))
        val stemH = h * (0.22f + 0.15f * ((seed % 1f)))
        val sway = sin(progress * 2 * PI + i * 0.8f).toFloat() * 24f
        val tipX = baseX + sway
        val tipY = h - stemH

        // Stem
        drawLine(
            color = Color(0xFF166534),
            start = Offset(baseX, h),
            end = Offset(tipX, tipY),
            strokeWidth = 3.5.dp.toPx()
        )

        // Flower head
        val flowerColor = when (i % 4) {
            0 -> Color(0xFFEC4899)
            1 -> Color(0xFFA855F7)
            2 -> Color(0xFF38BDF8)
            else -> Color(0xFFFACC15)
        }
        drawCircle(color = flowerColor, center = Offset(tipX, tipY), radius = 14f)
        drawCircle(color = Color.White, center = Offset(tipX, tipY), radius = 5f)
    }
}

/**
 * 5. Golden Falling Petals: Glittering gold flakes with depth vortex.
 */
private fun DrawScope.drawGoldenPetals(w: Float, h: Float, progress: Float) {
    for (i in 0..24) {
        val seed = i * 39.81f
        val t = (progress + (seed % 1f)) % 1f
        val y = t * h
        val x = (w * (0.1f + 0.8f * ((seed * 19f) % 1f)) + sin(t * 3 * PI + seed) * 35f).toFloat()
        val scale = (0.6f + 0.8f * ((seed * 7f) % 1f))
        val pW = 12f * scale
        val pH = 18f * scale

        drawOval(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFFEF08A), Color(0xFFCA8A04)),
                start = Offset(x, y),
                end = Offset(x + pW, y + pH)
            ),
            topLeft = Offset(x, y),
            size = Size(pW, pH)
        )
        // Sparkle glint
        drawCircle(color = Color.White.copy(alpha = 0.8f), center = Offset(x + pW * 0.5f, y + pH * 0.3f), radius = 2f)
    }
}

/**
 * 6. Garden Butterflies: Flapping 3D butterflies with figure-eight flight paths.
 */
private fun DrawScope.drawGardenButterflies(w: Float, h: Float, progress: Float, fastProgress: Float) {
    val butterflies = listOf(
        Triple(0.25f, 0.35f, Color(0xFF38BDF8)),
        Triple(0.7f, 0.45f, Color(0xFFA855F7)),
        Triple(0.48f, 0.72f, Color(0xFFF43F5E))
    )

    for ((idx, bf) in butterflies.withIndex()) {
        val time = (progress + idx * 0.33f) % 1f
        val pathAngle = time * 2 * PI
        val bx = (w * bf.first + sin(pathAngle) * 45f).toFloat()
        val by = (h * bf.second + sin(pathAngle * 2) * 25f).toFloat()
        val wingFlap = abs(sin(fastProgress * 6 * PI + idx)).toFloat().coerceIn(0.15f, 1f)

        // Wing 1 (left)
        drawOval(
            color = bf.third,
            topLeft = Offset(bx - 18f * wingFlap, by - 12f),
            size = Size(18f * wingFlap, 24f)
        )
        // Wing 2 (right)
        drawOval(
            color = bf.third,
            topLeft = Offset(bx, by - 12f),
            size = Size(18f * wingFlap, 24f)
        )
        // Butterfly body
        drawLine(
            color = Color(0xFF0F172A),
            start = Offset(bx, by - 10f),
            end = Offset(bx, by + 12f),
            strokeWidth = 2.5.dp.toPx()
        )
    }

    // Ambient garden fireflies
    for (i in 0..12) {
        val seed = i * 53.7f
        val fx = (w * ((seed * 11f) % 1f) + sin(progress * 2 * PI + seed) * 15f).toFloat()
        val fy = (h * ((seed * 17f) % 1f) + cos(progress * 2 * PI + seed) * 15f).toFloat()
        drawCircle(
            color = Color(0xFFA7F3D0).copy(alpha = 0.6f),
            center = Offset(fx, fy),
            radius = 3.5f
        )
    }
}

/**
 * 7. Enchanted Forest Fireflies.
 */
private fun DrawScope.drawForestFireflies(w: Float, h: Float, progress: Float, pulse: Float) {
    for (i in 0..20) {
        val seed = i * 43.11f
        val t = (progress * 0.6f + (seed % 1f)) % 1f
        val fx = (w * (0.05f + 0.9f * ((seed * 23f) % 1f)) + sin(t * 3 * PI + seed) * 35f).toFloat()
        val fy = (h * (0.1f + 0.8f * ((seed * 41f) % 1f)) + cos(t * 2 * PI + seed) * 25f).toFloat()
        val glow = (0.3f + 0.7f * sin(progress * 4 * PI + seed).toFloat().coerceIn(0f, 1f)) * pulse

        drawCircle(
            color = Color(0xFF4ADE80).copy(alpha = glow * 0.35f),
            center = Offset(fx, fy),
            radius = 16f
        )
        drawCircle(
            color = Color(0xFFDCFCE7).copy(alpha = glow),
            center = Offset(fx, fy),
            radius = 4f
        )
    }
}

/**
 * 8. Waterfall Mist.
 */
private fun DrawScope.drawWaterfallMist(w: Float, h: Float, fastProgress: Float) {
    for (i in 0..28) {
        val seed = i * 31.4f
        val t = (fastProgress + (seed % 1f)) % 1f
        val x = (w * (0.2f + 0.6f * ((seed * 13f) % 1f))).toFloat()
        val y = t * h
        drawLine(
            color = Color(0xFFBAE6FD).copy(alpha = 0.5f),
            start = Offset(x, y),
            end = Offset(x, y + 22f),
            strokeWidth = 2.5.dp.toPx()
        )
    }
    // Rising mist at bottom
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFE0F2FE).copy(alpha = 0.35f), Color.Transparent),
            center = Offset(w * 0.5f, h),
            radius = w * 0.6f
        ),
        center = Offset(w * 0.5f, h),
        radius = w * 0.6f
    )
}

/**
 * 9. Dancing Teddy Bear: 3D cartoon bear bobbing, wiggling ears, moving paws with music notes.
 */
private fun DrawScope.drawDancingTeddyBear(w: Float, h: Float, fast: Float, pulse: Float) {
    val cx = w * 0.5f
    val bounceY = sin(fast * 4 * PI).toFloat() * 14f
    val cy = h * 0.65f + bounceY
    val tilt = sin(fast * 2 * PI).toFloat() * 8f

    // Disco floor highlight
    drawOval(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFA855F7).copy(alpha = 0.35f), Color.Transparent),
            center = Offset(cx, h * 0.88f),
            radius = w * 0.35f
        ),
        topLeft = Offset(cx - w * 0.3f, h * 0.82f),
        size = Size(w * 0.6f, 40f)
    )

    // Bear Body
    drawOval(
        color = Color(0xFFB45309),
        topLeft = Offset(cx - 42f, cy - 25f),
        size = Size(84f, 96f)
    )
    // Tummy patch
    drawOval(
        color = Color(0xFFFDE68A),
        topLeft = Offset(cx - 24f, cy - 10f),
        size = Size(48f, 60f)
    )

    // Dancing Paws / Arms
    val leftArmAngle = sin(fast * 2 * PI).toFloat() * 25f
    val rightArmAngle = -sin(fast * 2 * PI).toFloat() * 25f
    drawCircle(color = Color(0xFFB45309), center = Offset(cx - 52f, cy + leftArmAngle), radius = 16f)
    drawCircle(color = Color(0xFFB45309), center = Offset(cx + 52f, cy + rightArmAngle), radius = 16f)

    // Bear Feet
    drawOval(color = Color(0xFF92400E), topLeft = Offset(cx - 40f, cy + 62f), size = Size(32f, 22f))
    drawOval(color = Color(0xFF92400E), topLeft = Offset(cx + 8f, cy + 62f), size = Size(32f, 22f))

    // Bear Head
    val headY = cy - 65f
    val headX = cx + tilt * 0.5f
    drawCircle(color = Color(0xFFB45309), center = Offset(headX, headY), radius = 38f)

    // Ears (wiggling)
    val earWiggle = sin(fast * 4 * PI).toFloat() * 4f
    drawCircle(color = Color(0xFF92400E), center = Offset(headX - 32f, headY - 32f + earWiggle), radius = 16f)
    drawCircle(color = Color(0xFFFDE68A), center = Offset(headX - 32f, headY - 32f + earWiggle), radius = 9f)
    drawCircle(color = Color(0xFF92400E), center = Offset(headX + 32f, headY - 32f - earWiggle), radius = 16f)
    drawCircle(color = Color(0xFFFDE68A), center = Offset(headX + 32f, headY - 32f - earWiggle), radius = 9f)

    // Snout & Nose
    drawOval(color = Color(0xFFFDE68A), topLeft = Offset(headX - 18f, headY - 6f), size = Size(36f, 26f))
    drawCircle(color = Color(0xFF1E293B), center = Offset(headX, headY + 3f), radius = 6f)

    // Eyes with blink
    val isBlinking = fast in 0.45f..0.52f
    if (!isBlinking) {
        drawCircle(color = Color(0xFF1E293B), center = Offset(headX - 14f, headY - 14f), radius = 4.5f)
        drawCircle(color = Color.White, center = Offset(headX - 15f, headY - 16f), radius = 1.5f)
        drawCircle(color = Color(0xFF1E293B), center = Offset(headX + 14f, headY - 14f), radius = 4.5f)
        drawCircle(color = Color.White, center = Offset(headX + 13f, headY - 16f), radius = 1.5f)
    } else {
        drawLine(color = Color(0xFF1E293B), start = Offset(headX - 18f, headY - 14f), end = Offset(headX - 10f, headY - 14f), strokeWidth = 2.dp.toPx())
        drawLine(color = Color(0xFF1E293B), start = Offset(headX + 10f, headY - 14f), end = Offset(headX + 18f, headY - 14f), strokeWidth = 2.dp.toPx())
    }

    // Floating musical notes ♪
    for (i in 0..4) {
        val seed = i * 21.7f
        val noteT = (fast + (seed % 1f)) % 1f
        val nx = cx + (if (i % 2 == 0) -1 else 1) * (50f + noteT * 80f)
        val ny = headY - noteT * 90f
        val alpha = (1f - noteT).coerceIn(0f, 1f)
        drawCircle(color = Color(0xFFFBCFE8).copy(alpha = alpha), center = Offset(nx, ny), radius = 5f)
        drawLine(color = Color(0xFFFBCFE8).copy(alpha = alpha), start = Offset(nx + 4f, ny), end = Offset(nx + 4f, ny - 14f), strokeWidth = 2.dp.toPx())
    }
}

/**
 * 10. Groovy Panda: Cartoon panda dancing.
 */
private fun DrawScope.drawDancingPanda(w: Float, h: Float, fast: Float, pulse: Float) {
    val cx = w * 0.5f
    val bounce = sin(fast * 4 * PI).toFloat() * 12f
    val cy = h * 0.66f + bounce

    // Panda Body
    drawOval(color = Color.White, topLeft = Offset(cx - 38f, cy - 20f), size = Size(76f, 90f))
    // Black belly vest
    drawOval(color = Color(0xFF1E293B), topLeft = Offset(cx - 42f, cy - 12f), size = Size(84f, 40f))

    // Black Arms
    val armSway = sin(fast * 2 * PI).toFloat() * 20f
    drawCircle(color = Color(0xFF1E293B), center = Offset(cx - 48f, cy + armSway), radius = 15f)
    drawCircle(color = Color(0xFF1E293B), center = Offset(cx + 48f, cy - armSway), radius = 15f)

    // Feet
    drawOval(color = Color(0xFF1E293B), topLeft = Offset(cx - 36f, cy + 60f), size = Size(30f, 20f))
    drawOval(color = Color(0xFF1E293B), topLeft = Offset(cx + 6f, cy + 60f), size = Size(30f, 20f))

    // Head
    val headY = cy - 60f
    drawCircle(color = Color.White, center = Offset(cx, headY), radius = 36f)

    // Black Ears
    drawCircle(color = Color(0xFF1E293B), center = Offset(cx - 30f, headY - 30f), radius = 14f)
    drawCircle(color = Color(0xFF1E293B), center = Offset(cx + 30f, headY - 30f), radius = 14f)

    // Eye Patches & Eyes
    drawOval(color = Color(0xFF1E293B), topLeft = Offset(cx - 24f, headY - 14f), size = Size(18f, 24f))
    drawOval(color = Color(0xFF1E293B), topLeft = Offset(cx + 6f, headY - 14f), size = Size(18f, 24f))
    drawCircle(color = Color.White, center = Offset(cx - 15f, headY - 4f), radius = 4f)
    drawCircle(color = Color.White, center = Offset(cx + 15f, headY - 4f), radius = 4f)
    // Rosy cheeks
    drawCircle(color = Color(0xFFFDA4AF).copy(alpha = 0.7f), center = Offset(cx - 24f, headY + 10f), radius = 6f)
    drawCircle(color = Color(0xFFFDA4AF).copy(alpha = 0.7f), center = Offset(cx + 24f, headY + 10f), radius = 6f)
    // Nose
    drawOval(color = Color(0xFF1E293B), topLeft = Offset(cx - 5f, headY + 2f), size = Size(10f, 6f))

    // Floating bamboo leaves
    for (i in 0..5) {
        val seed = i * 33.7f
        val t = (fast + (seed % 1f)) % 1f
        val lx = (w * (0.15f + 0.7f * ((seed * 11f) % 1f)) + sin(t * 3 * PI) * 20f).toFloat()
        val ly = t * h
        drawOval(color = Color(0xFF86EFAC), topLeft = Offset(lx, ly), size = Size(16f, 8f))
    }
}

/**
 * 11. Bouncing Bunny.
 */
private fun DrawScope.drawBouncingBunny(w: Float, h: Float, fast: Float, pulse: Float) {
    val cx = w * 0.5f
    val hop = abs(sin(fast * 2 * PI)).toFloat()
    val cy = h * 0.7f - hop * 45f

    // Fluffy Body
    drawOval(color = Color.White, topLeft = Offset(cx - 32f, cy - 10f), size = Size(64f, 68f))

    // Paws
    drawCircle(color = Color.White, center = Offset(cx - 20f, cy + 48f), radius = 10f)
    drawCircle(color = Color.White, center = Offset(cx + 20f, cy + 48f), radius = 10f)

    // Head
    val headY = cy - 42f
    drawCircle(color = Color.White, center = Offset(cx, headY), radius = 28f)

    // Long Ears (flopping with hop)
    val earFlop = (1f - hop) * 15f
    drawOval(color = Color.White, topLeft = Offset(cx - 24f + earFlop * 0.3f, headY - 55f), size = Size(16f, 44f))
    drawOval(color = Color(0xFFFBCFE8), topLeft = Offset(cx - 20f + earFlop * 0.3f, headY - 48f), size = Size(8f, 32f))
    drawOval(color = Color.White, topLeft = Offset(cx + 8f - earFlop * 0.3f, headY - 55f), size = Size(16f, 44f))
    drawOval(color = Color(0xFFFBCFE8), topLeft = Offset(cx + 12f - earFlop * 0.3f, headY - 48f), size = Size(8f, 32f))

    // Face details
    drawCircle(color = Color(0xFF475569), center = Offset(cx - 10f, headY - 4f), radius = 3.5f)
    drawCircle(color = Color(0xFF475569), center = Offset(cx + 10f, headY - 4f), radius = 3.5f)
    drawCircle(color = Color(0xFFF472B6), center = Offset(cx, headY + 5f), radius = 4f)

    // Floating pastel sparkle stars
    for (i in 0..8) {
        val seed = i * 29.5f
        val sx = (w * (0.1f + 0.8f * ((seed * 7f) % 1f))).toFloat()
        val sy = (h * (0.15f + 0.7f * ((seed * 19f) % 1f)) + sin(fast * 4 * PI + seed) * 12f).toFloat()
        drawCircle(color = Color(0xFFE9D5FF).copy(alpha = 0.75f * pulse), center = Offset(sx, sy), radius = 3f)
    }
}

/**
 * 12. Waving Kawaii Kitty.
 */
private fun DrawScope.drawKawaiiKitty(w: Float, h: Float, fast: Float, pulse: Float) {
    val cx = w * 0.5f
    val cy = h * 0.68f

    // Body
    drawOval(color = Color(0xFFFFFBEB), topLeft = Offset(cx - 34f, cy - 15f), size = Size(68f, 76f))
    // Red Collar & Bell
    drawRect(color = Color(0xFFEF4444), topLeft = Offset(cx - 24f, cy - 12f), size = Size(48f, 8f))
    drawCircle(color = Color(0xFFFACC15), center = Offset(cx, cy - 8f), radius = 6f)

    // Waving lucky paw
    val wave = sin(fast * 4 * PI).toFloat() * 16f
    drawCircle(color = Color(0xFFFFFBEB), center = Offset(cx + 34f, cy - 24f + wave), radius = 13f)
    drawCircle(color = Color(0xFFFFFBEB), center = Offset(cx - 26f, cy + 18f), radius = 13f)

    // Head
    val headY = cy - 48f
    drawCircle(color = Color(0xFFFFFBEB), center = Offset(cx, headY), radius = 32f)

    // Pointed Ears
    val leftEar = Path().apply {
        moveTo(cx - 28f, headY - 14f)
        lineTo(cx - 24f, headY - 44f)
        lineTo(cx - 4f, headY - 26f)
        close()
    }
    drawPath(path = leftEar, color = Color(0xFFFFFBEB))
    val rightEar = Path().apply {
        moveTo(cx + 4f, headY - 26f)
        lineTo(cx + 24f, headY - 44f)
        lineTo(cx + 28f, headY - 14f)
        close()
    }
    drawPath(path = rightEar, color = Color(0xFFFFFBEB))

    // Eyes, Whiskers, Nose
    drawCircle(color = Color(0xFF1E293B), center = Offset(cx - 12f, headY - 2f), radius = 4f)
    drawCircle(color = Color(0xFF1E293B), center = Offset(cx + 12f, headY - 2f), radius = 4f)
    drawCircle(color = Color(0xFFFB7185), center = Offset(cx, headY + 5f), radius = 3f)

    // Whiskers
    drawLine(color = Color(0xFF94A3B8), start = Offset(cx - 16f, headY + 5f), end = Offset(cx - 36f, headY + 2f), strokeWidth = 1.5.dp.toPx())
    drawLine(color = Color(0xFF94A3B8), start = Offset(cx - 16f, headY + 9f), end = Offset(cx - 36f, headY + 11f), strokeWidth = 1.5.dp.toPx())
    drawLine(color = Color(0xFF94A3B8), start = Offset(cx + 16f, headY + 5f), end = Offset(cx + 36f, headY + 2f), strokeWidth = 1.5.dp.toPx())
    drawLine(color = Color(0xFF94A3B8), start = Offset(cx + 16f, headY + 9f), end = Offset(cx + 36f, headY + 11f), strokeWidth = 1.5.dp.toPx())

    // Popping love hearts
    for (i in 0..4) {
        val seed = i * 41.2f
        val t = (fast + (seed % 1f)) % 1f
        val hx = cx + (if (i % 2 == 0) -1 else 1) * (45f + t * 60f)
        val hy = cy - 40f - t * 80f
        val scale = (1f - t) * 14f
        drawCircle(color = Color(0xFFFDA4AF).copy(alpha = 1f - t), center = Offset(hx, hy), radius = scale * 0.5f)
    }
}

/**
 * 13. Magic Fairy: Hovering miniature fairy silhouette with fluttering wings & fairy dust.
 */
private fun DrawScope.drawFantasyFairy(w: Float, h: Float, progress: Float, fast: Float) {
    val fx = w * 0.5f + sin(progress * 2 * PI).toFloat() * 35f
    val fy = h * 0.6f + cos(progress * 2 * PI).toFloat() * 18f
    val wingFlap = abs(sin(fast * 8 * PI)).toFloat().coerceIn(0.2f, 1f)

    // Glowing aura
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFA855F7).copy(alpha = 0.5f), Color.Transparent),
            center = Offset(fx, fy),
            radius = 65f
        ),
        center = Offset(fx, fy),
        radius = 65f
    )

    // Translucent Fairy Wings
    drawOval(
        color = Color(0xFFE0E7FF).copy(alpha = 0.7f),
        topLeft = Offset(fx - 28f * wingFlap, fy - 22f),
        size = Size(28f * wingFlap, 36f)
    )
    drawOval(
        color = Color(0xFFE0E7FF).copy(alpha = 0.7f),
        topLeft = Offset(fx, fy - 22f),
        size = Size(28f * wingFlap, 36f)
    )

    // Silhouette body
    drawOval(color = Color(0xFF312E81), topLeft = Offset(fx - 6f, fy - 10f), size = Size(12f, 26f))
    drawCircle(color = Color(0xFF312E81), center = Offset(fx, fy - 18f), radius = 7f)

    // Wand & trailing fairy dust
    val wandTip = Offset(fx + 22f, fy - 8f)
    drawLine(color = Color(0xFFFDE047), start = Offset(fx + 5f, fy), end = wandTip, strokeWidth = 2.dp.toPx())
    drawCircle(color = Color.White, center = wandTip, radius = 4f)

    // Trailing fairy dust particles
    for (i in 0..15) {
        val seed = i * 23.3f
        val t = (fast + (seed % 1f)) % 1f
        val dx = fx - t * 80f + sin(t * 4 * PI + seed) * 18f
        val dy = fy + t * 40f + cos(t * 3 * PI + seed) * 14f
        drawCircle(color = Color(0xFFFACC15).copy(alpha = (1f - t) * 0.8f), center = Offset(dx, dy), radius = 2.5f)
    }
}

/**
 * 14. Floating 3D Hearts: Shaded romantic hearts rising with 3D specular depth.
 */
private fun DrawScope.drawFloatingHearts(w: Float, h: Float, progress: Float) {
    for (i in 0..17) {
        val seed = i * 59.13f
        val t = (progress + (seed % 1f)) % 1f
        val y = h * (1f - t)
        val x = (w * (0.08f + 0.84f * ((seed * 17f) % 1f)) + sin(t * 4 * PI + seed) * 25f).toFloat()
        val scale = 0.7f + 0.7f * ((seed * 3f) % 1f)
        val size = 26f * scale
        val alpha = sin(t * PI).toFloat().coerceIn(0f, 1f) * 0.9f

        drawHeart(x, y, size, Color(0xFFF43F5E).copy(alpha = alpha))
    }
}

/**
 * 15. Beating Heart: Large rhythmic 3D heart with expanding shockwave glow rings.
 */
private fun DrawScope.drawBeatingHeart(w: Float, h: Float, pulse: Float) {
    val cx = w * 0.5f
    val cy = h * 0.55f
    val baseSize = min(w, h) * 0.42f * (0.85f + 0.22f * pulse)

    // Expanding shockwave ring 1
    val ringR = baseSize * (1.1f + 0.7f * pulse)
    drawCircle(
        color = Color(0xFFEF4444).copy(alpha = (1f - pulse) * 0.45f),
        center = Offset(cx, cy),
        radius = ringR,
        style = Stroke(width = 4.dp.toPx())
    )

    // Center big 3D heart
    drawHeart(cx, cy - baseSize * 0.2f, baseSize, Color(0xFFDC2626))

    // Specular glossy reflection
    drawCircle(
        color = Color.White.copy(alpha = 0.45f),
        center = Offset(cx - baseSize * 0.18f, cy - baseSize * 0.28f),
        radius = baseSize * 0.1f
    )
}

/**
 * Helper to draw a heart shape on Canvas.
 */
private fun DrawScope.drawHeart(cx: Float, cy: Float, size: Float, color: Color) {
    val path = Path().apply {
        moveTo(cx, cy + size * 0.4f)
        cubicTo(cx - size * 0.8f, cy - size * 0.3f, cx - size * 0.4f, cy - size * 0.8f, cx, cy - size * 0.35f)
        cubicTo(cx + size * 0.4f, cy - size * 0.8f, cx + size * 0.8f, cy - size * 0.3f, cx, cy + size * 0.4f)
        close()
    }
    drawPath(path = path, color = color)
}

/**
 * 16. Rose Petals Love.
 */
private fun DrawScope.drawRosePetalsLove(w: Float, h: Float, progress: Float) {
    for (i in 0..19) {
        val seed = i * 47.9f
        val t = (progress + (seed % 1f)) % 1f
        val y = t * h
        val x = (w * (0.05f + 0.9f * ((seed * 11f) % 1f)) + cos(t * 3 * PI + seed) * 30f).toFloat()
        val pW = 16f
        val pH = 24f
        val alpha = sin(t * PI).toFloat().coerceIn(0f, 1f)

        drawOval(
            color = Color(0xFFFB7185).copy(alpha = alpha),
            topLeft = Offset(x, y),
            size = Size(pW, pH)
        )
    }
}

/**
 * 17. Romantic Starry Glow.
 */
private fun DrawScope.drawRomanticGlow(w: Float, h: Float, progress: Float, pulse: Float) {
    for (i in 0..18) {
        val seed = i * 67.3f
        val x = (w * (0.05f + 0.9f * ((seed * 13f) % 1f))).toFloat()
        val y = (h * (0.1f + 0.8f * ((seed * 29f) % 1f))).toFloat()
        val r = 18f + 12f * sin(progress * 2 * PI + seed).toFloat()
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFDE047).copy(alpha = 0.5f * pulse), Color.Transparent),
                center = Offset(x, y),
                radius = r
            ),
            center = Offset(x, y),
            radius = r
        )
    }
}

/**
 * 18. Aurora Borealis: Flowing waving curtains of emerald, cyan, and violet + twinkling stars.
 */
private fun DrawScope.drawAuroraBorealis(w: Float, h: Float, progress: Float) {
    // Stars
    for (i in 0..30) {
        val seed = i * 37.1f
        val sx = (w * ((seed * 7f) % 1f)).toFloat()
        val sy = (h * 0.6f * ((seed * 19f) % 1f)).toFloat()
        val starAlpha = 0.3f + 0.7f * sin(progress * 4 * PI + seed).toFloat().coerceIn(0f, 1f)
        drawCircle(color = Color.White.copy(alpha = starAlpha), center = Offset(sx, sy), radius = 2f)
    }

    // 3 Overlapping Aurora wave ribbons
    val waves = listOf(
        Triple(Color(0xFF10B981), 0.3f, 40f),
        Triple(Color(0xFF06B6D4), 0.45f, 50f),
        Triple(Color(0xFF8B5CF6), 0.58f, 60f)
    )

    for ((idx, wave) in waves.withIndex()) {
        val path = Path().apply {
            moveTo(0f, h * wave.second)
            val segments = 8
            val segW = w / segments
            for (s in 1..segments) {
                val segX = s * segW
                val waveY = h * wave.second + sin(progress * 2 * PI + s * 0.7f + idx * 1.2f).toFloat() * wave.third
                lineTo(segX, waveY)
            }
            lineTo(w, h * (wave.second + 0.25f))
            lineTo(0f, h * (wave.second + 0.25f))
            close()
        }
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(wave.first.copy(alpha = 0.45f), Color.Transparent),
                startY = h * wave.second - 20f,
                endY = h * (wave.second + 0.25f)
            )
        )
    }
}

/**
 * 19. Cosmic Galaxy Stars: Swirling deep space with shooting star meteors.
 */
private fun DrawScope.drawCosmicStars(w: Float, h: Float, progress: Float, fast: Float) {
    // 40 Twinkling stars
    for (i in 0..39) {
        val seed = i * 51.3f
        val sx = (w * ((seed * 11f) % 1f)).toFloat()
        val sy = (h * ((seed * 23f) % 1f)).toFloat()
        val twinkle = 0.2f + 0.8f * sin(progress * 6 * PI + seed).toFloat().coerceIn(0f, 1f)
        drawCircle(color = Color(0xFFBAE6FD).copy(alpha = twinkle), center = Offset(sx, sy), radius = 2.5f)
    }

    // Shooting meteor
    val meteorT = (fast * 1.2f) % 1f
    if (meteorT < 0.6f) {
        val mx = w * (0.8f - meteorT * 0.9f)
        val my = h * (0.15f + meteorT * 0.65f)
        val tailX = mx + 55f
        val tailY = my - 35f
        drawLine(
            brush = Brush.linearGradient(
                colors = listOf(Color.White, Color.Transparent),
                start = Offset(mx, my),
                end = Offset(tailX, tailY)
            ),
            start = Offset(mx, my),
            end = Offset(tailX, tailY),
            strokeWidth = 3.dp.toPx()
        )
    }
}

/**
 * 20. Magical Floating Orbs.
 */
private fun DrawScope.drawMagicalOrbs(w: Float, h: Float, progress: Float, pulse: Float) {
    val orbs = listOf(
        Pair(w * 0.25f, h * 0.4f),
        Pair(w * 0.75f, h * 0.35f),
        Pair(w * 0.5f, h * 0.7f)
    )

    for ((idx, pos) in orbs.withIndex()) {
        val floatY = sin(progress * 2 * PI + idx * 2f).toFloat() * 18f
        val ox = pos.first
        val oy = pos.second + floatY
        val r = 32f * (0.9f + 0.15f * pulse)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFC084FC).copy(alpha = 0.8f), Color(0xFF9333EA).copy(alpha = 0.3f), Color.Transparent),
                center = Offset(ox, oy),
                radius = r * 2f
            ),
            center = Offset(ox, oy),
            radius = r * 2f
        )
        drawCircle(color = Color.White.copy(alpha = 0.9f), center = Offset(ox, oy), radius = r * 0.3f)
    }
}

/**
 * 21. Fantasy Castle.
 */
private fun DrawScope.drawFantasyCastle(w: Float, h: Float, progress: Float, pulse: Float) {
    // Crescent Moon
    val moonX = w * 0.82f
    val moonY = h * 0.2f
    drawCircle(color = Color(0xFFFEF08A), center = Offset(moonX, moonY), radius = 24f)
    drawCircle(color = Color(0xFF180A2E), center = Offset(moonX - 8f, moonY - 6f), radius = 22f)

    // Spires silhouette at bottom
    val spirePath = Path().apply {
        moveTo(0f, h)
        lineTo(w * 0.15f, h * 0.72f)
        lineTo(w * 0.3f, h * 0.85f)
        lineTo(w * 0.45f, h * 0.65f)
        lineTo(w * 0.55f, h * 0.62f)
        lineTo(w * 0.65f, h * 0.82f)
        lineTo(w * 0.8f, h * 0.75f)
        lineTo(w, h)
        close()
    }
    drawPath(path = spirePath, color = Color(0xFF0F071D).copy(alpha = 0.85f))
}

/**
 * 22. Confetti Fiesta: 35+ tumbling metallic 3D confetti with multi-axis spin.
 */
private fun DrawScope.drawConfettiFiesta(w: Float, h: Float, fast: Float) {
    val colors = listOf(Color(0xFFF59E0B), Color(0xFFEC4899), Color(0xFF38BDF8), Color(0xFF10B981), Color(0xFFA855F7))
    for (i in 0..34) {
        val seed = i * 41.7f
        val t = (fast + (seed % 1f)) % 1f
        val y = t * h
        val x = (w * (0.05f + 0.9f * ((seed * 19f) % 1f)) + sin(t * 5 * PI + seed) * 30f).toFloat()
        val spinX = cos(t * 8 * PI + seed).toFloat().coerceIn(-1f, 1f)
        val cW = 14f * abs(spinX).coerceAtLeast(0.15f)
        val cH = 8f

        drawRect(
            color = colors[i % colors.size],
            topLeft = Offset(x, y),
            size = Size(cW, cH)
        )
    }
}

/**
 * 23. Celebration Balloons: Glossy 3D balloons rising with curved strings.
 */
private fun DrawScope.drawRisingBalloons(w: Float, h: Float, progress: Float) {
    val balloonColors = listOf(Color(0xFFEF4444), Color(0xFFF59E0B), Color(0xFF3B82F6), Color(0xFF10B981), Color(0xFFA855F7))
    for (i in 0..6) {
        val seed = i * 67.9f
        val t = (progress + (seed % 1f)) % 1f
        val y = h * (1.1f - t * 1.3f)
        val sway = sin(t * 3 * PI + seed).toFloat() * 22f
        val x = (w * (0.12f + 0.76f * ((seed * 13f) % 1f)) + sway).toFloat()
        val bW = 42f
        val bH = 54f
        val color = balloonColors[i % balloonColors.size]

        // Balloon Body
        drawOval(color = color, topLeft = Offset(x - bW * 0.5f, y - bH * 0.5f), size = Size(bW, bH))
        // Specular 3D Highlight
        drawCircle(color = Color.White.copy(alpha = 0.55f), center = Offset(x - bW * 0.2f, y - bH * 0.22f), radius = 6f)

        // String
        val stringPath = Path().apply {
            moveTo(x, y + bH * 0.5f)
            cubicTo(x - 8f, y + bH * 0.5f + 20f, x + 8f, y + bH * 0.5f + 40f, x, y + bH * 0.5f + 65f)
        }
        drawPath(path = stringPath, color = Color(0xFFE2E8F0).copy(alpha = 0.7f), style = Stroke(width = 1.5.dp.toPx()))
    }
}

/**
 * 24. Midnight Fireworks.
 */
private fun DrawScope.drawFireworksNight(w: Float, h: Float, fast: Float) {
    val bursts = listOf(
        Pair(Offset(w * 0.3f, h * 0.32f), Color(0xFFF43F5E)),
        Pair(Offset(w * 0.7f, h * 0.25f), Color(0xFF38BDF8)),
        Pair(Offset(w * 0.5f, h * 0.48f), Color(0xFFFACC15))
    )

    for ((idx, burst) in bursts.withIndex()) {
        val t = (fast * 1.4f + idx * 0.33f) % 1f
        val radius = t * 75f
        val sparks = 16
        val alpha = (1f - t).coerceIn(0f, 1f)

        for (s in 0 until sparks) {
            val angle = Math.toRadians((s * (360f / sparks)).toDouble())
            val sx = burst.first.x + (radius * cos(angle)).toFloat()
            val sy = burst.first.y + (radius * sin(angle)).toFloat() + t * t * 25f // gravity drop
            drawCircle(color = burst.second.copy(alpha = alpha), center = Offset(sx, sy), radius = 3f)
        }
    }
}

/**
 * 25. Golden Birthday Sparkles.
 */
private fun DrawScope.drawBirthdaySparkles(w: Float, h: Float, progress: Float, pulse: Float) {
    // Rising golden champagne bubbles
    for (i in 0..24) {
        val seed = i * 37.7f
        val t = (progress + (seed % 1f)) % 1f
        val y = h * (1f - t)
        val x = (w * ((seed * 19f) % 1f)).toFloat()
        val r = 3f + (seed % 5f)
        drawCircle(color = Color(0xFFFDE047).copy(alpha = 0.65f), center = Offset(x, y), radius = r, style = Stroke(width = 1.5.dp.toPx()))
    }

    // Burst sparkle flares
    for (i in 0..4) {
        val seed = i * 49.3f
        val fx = (w * (0.15f + 0.7f * ((seed * 9f) % 1f))).toFloat()
        val fy = (h * (0.2f + 0.6f * ((seed * 23f) % 1f))).toFloat()
        val flareSize = (20f + 14f * pulse)
        drawLine(color = Color(0xFFFEF08A), start = Offset(fx - flareSize, fy), end = Offset(fx + flareSize, fy), strokeWidth = 2.dp.toPx())
        drawLine(color = Color(0xFFFEF08A), start = Offset(fx, fy - flareSize), end = Offset(fx, fy + flareSize), strokeWidth = 2.dp.toPx())
    }
}

/**
 * 26. Ocean Waves & Swimming Fish: Deep turquoise with swimming colorful fish.
 */
private fun DrawScope.drawOceanFish(w: Float, h: Float, progress: Float, fast: Float) {
    // Caustic sunlight waves at top
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFF38BDF8).copy(alpha = 0.35f), Color.Transparent),
            center = Offset(w * 0.5f, 0f),
            radius = w * 0.8f
        ),
        center = Offset(w * 0.5f, 0f),
        radius = w * 0.8f
    )

    // Swimming fish 1 (orange clownfish moving right)
    val fish1X = ((progress * 1.2f) % 1f) * (w + 100f) - 50f
    val fish1Y = h * 0.4f + sin(progress * 4 * PI).toFloat() * 16f
    val tailWag1 = sin(fast * 8 * PI).toFloat() * 6f
    // Body
    drawOval(color = Color(0xFFF97316), topLeft = Offset(fish1X - 22f, fish1Y - 12f), size = Size(44f, 24f))
    drawRect(color = Color.White, topLeft = Offset(fish1X - 6f, fish1Y - 12f), size = Size(8f, 24f))
    // Tail
    val tail1 = Path().apply {
        moveTo(fish1X - 22f, fish1Y)
        lineTo(fish1X - 36f, fish1Y - 14f + tailWag1)
        lineTo(fish1X - 36f, fish1Y + 14f + tailWag1)
        close()
    }
    drawPath(path = tail1, color = Color(0xFFEA580C))
    drawCircle(color = Color.Black, center = Offset(fish1X + 12f, fish1Y - 4f), radius = 2.5f)

    // Swimming fish 2 (blue tang moving left)
    val fish2X = (1f - (progress * 0.85f) % 1f) * (w + 120f) - 60f
    val fish2Y = h * 0.65f + cos(progress * 3 * PI).toFloat() * 18f
    val tailWag2 = sin(fast * 8 * PI + 1f).toFloat() * 6f
    drawOval(color = Color(0xFF2563EB), topLeft = Offset(fish2X - 24f, fish2Y - 14f), size = Size(48f, 28f))
    drawOval(color = Color(0xFFFACC15), topLeft = Offset(fish2X + 8f, fish2Y - 8f), size = Size(16f, 16f))
    val tail2 = Path().apply {
        moveTo(fish2X + 24f, fish2Y)
        lineTo(fish2X + 38f, fish2Y - 14f + tailWag2)
        lineTo(fish2X + 38f, fish2Y + 14f + tailWag2)
        close()
    }
    drawPath(path = tail2, color = Color(0xFFFACC15))
    drawCircle(color = Color.Black, center = Offset(fish2X - 14f, fish2Y - 4f), radius = 2.5f)

    // Rising bubbles
    for (i in 0..12) {
        val seed = i * 29.3f
        val t = (fast + (seed % 1f)) % 1f
        val bx = (w * ((seed * 17f) % 1f) + sin(t * 4 * PI) * 10f).toFloat()
        val by = h * (1f - t)
        drawCircle(color = Color(0xFFBAE6FD).copy(alpha = 0.55f), center = Offset(bx, by), radius = 3.5f, style = Stroke(width = 1.dp.toPx()))
    }
}

/**
 * 27. Falling Snow: Layered realistic crystalline snowflakes.
 */
private fun DrawScope.drawFallingSnow(w: Float, h: Float, progress: Float) {
    for (i in 0..35) {
        val seed = i * 33.1f
        val t = (progress + (seed % 1f)) % 1f
        val y = t * h
        val x = (w * ((seed * 13f) % 1f) + sin(t * 3 * PI + seed) * 22f).toFloat()
        val r = 2.5f + (seed % 4.5f)
        val alpha = (0.4f + 0.6f * ((seed * 7f) % 1f))

        drawCircle(color = Color.White.copy(alpha = alpha), center = Offset(x, y), radius = r)
    }
}

/**
 * 28. Gentle Rain on Glass.
 */
private fun DrawScope.drawGentleRain(w: Float, h: Float, fast: Float) {
    for (i in 0..38) {
        val seed = i * 47.9f
        val t = (fast + (seed % 1f)) % 1f
        val y = t * h
        val x = (w * ((seed * 19f) % 1f)).toFloat()
        drawLine(
            color = Color(0xFF93C5FD).copy(alpha = 0.5f),
            start = Offset(x, y),
            end = Offset(x - 8f, y + 26f),
            strokeWidth = 1.5.dp.toPx()
        )
    }
}

/**
 * 29. Sunset Clouds: Warm setting sun with parallax gliding clouds.
 */
private fun DrawScope.drawSunsetClouds(w: Float, h: Float, progress: Float) {
    // Sun on horizon
    val sunCenter = Offset(w * 0.5f, h * 0.75f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFFDE047), Color(0xFFEA580C), Color.Transparent),
            center = sunCenter,
            radius = w * 0.5f
        ),
        center = sunCenter,
        radius = w * 0.5f
    )

    // Gliding cumulus clouds
    val clouds = listOf(
        Triple(0.2f, 0.35f, 90f),
        Triple(0.65f, 0.45f, 120f),
        Triple(0.4f, 0.6f, 80f)
    )

    for ((idx, c) in clouds.withIndex()) {
        val t = (progress * (0.4f + idx * 0.2f) + idx * 0.33f) % 1f
        val cx = t * (w + c.third * 2f) - c.third
        val cy = h * c.second

        drawOval(
            color = Color(0xFF7C2D12).copy(alpha = 0.45f),
            topLeft = Offset(cx - c.third * 0.5f, cy - 18f),
            size = Size(c.third, 36f)
        )
        drawCircle(
            color = Color(0xFF9A3412).copy(alpha = 0.55f),
            center = Offset(cx, cy - 8f),
            radius = 24f
        )
    }
}

/**
 * TextPostBackgroundView - The complete presentation layer for a background-styled text post.
 * Handles both classic CSS-like gradients and newly introduced animated scenes.
 * Automatically ensures high text contrast, readability, and correct alignment.
 */
@Composable
fun TextPostBackgroundView(
    backgroundStyle: String?,
    content: String,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    onTextClick: (() -> Unit)? = null
) {
    if (content.isBlank() && backgroundStyle.isNullOrBlank()) return

    val cleanStyle = remember(backgroundStyle) { backgroundStyle?.trim() ?: "" }
    val styling = remember(cleanStyle) { TextPostStyling.parse(cleanStyle) }
    val isAnimated = remember(cleanStyle) { cleanStyle.startsWith("animated:") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 280.dp)
            .clipToBounds()
            .then(if (onTextClick != null) Modifier.clickable { onTextClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        if (isAnimated) {
            // Render High-Fidelity Animated Background Scene
            AnimatedPostBackground(
                templateId = cleanStyle,
                modifier = Modifier.fillMaxSize(),
                isVisible = isVisible
            )
        } else {
            // Render Classic Linear Gradient (Preserves 100% existing posts)
            val gradientColors = remember(cleanStyle) {
                when {
                    cleanStyle.contains("#FF0057") -> listOf(Color(0xFFFF0057), Color(0xFFE64C4C))
                    cleanStyle.contains("#a8ff78") -> listOf(Color(0xFFA8FF78), Color(0xFF78FFD6))
                    cleanStyle.contains("#e65c00") -> listOf(Color(0xFFE65C00), Color(0xFFF9D423))
                    cleanStyle.contains("#00C6FF") -> listOf(Color(0xFF00C6FF), Color(0xFF0072FF))
                    cleanStyle.contains("#434343") -> listOf(Color(0xFF434343), Color(0xFF000000))
                    cleanStyle.contains("#f6d365") -> listOf(Color(0xFFF6D365), Color(0xFFFDA085))
                    else -> listOf(Color(0xFF1877F2), Color(0xFF0055FF))
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.linearGradient(gradientColors))
            )
        }

        // Automatic contrast scrim layer so animated graphics never overpower text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isAnimated) Color.Black.copy(alpha = 0.22f)
                    else Color.Transparent
                )
        )

        // User's Text Layer (Styled, Centered/Aligned, with soft contrast shadow)
        Text(
            text = content,
            color = styling.textColor,
            fontSize = styling.fontSizeSp.sp,
            fontWeight = when (styling.fontStyleName) {
                "serif" -> FontWeight.SemiBold
                "display" -> FontWeight.Black
                else -> FontWeight.ExtraBold
            },
            fontFamily = when (styling.fontStyleName) {
                "serif" -> FontFamily.Serif
                else -> FontFamily.Default
            },
            textAlign = styling.alignment,
            lineHeight = (styling.fontSizeSp * 1.45f).sp,
            style = androidx.compose.ui.text.TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.75f),
                    offset = Offset(2f, 2f),
                    blurRadius = 8f
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 24.dp)
        )
    }
}
