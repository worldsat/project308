package com.uilover.project308.ui.intro

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.R
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer
import com.uilover.project308.ui.theme.Project308Theme

/**
 * Intro & Onboarding Screen for Sorce Career AI per design.md §2 and rules.md §12.
 *
 * Implements:
 * - Edge-to-edge wallpaper visual with gentle vertical blend gradients
 * - Top bar with official Sorce branding + AI chip and frosted glass Skip pill
 * - Three floating glassmorphism value proposition pills with subtle float dynamics
 * - Bottom rounded content sheet with dual-tone bold headline, subtitle, step indicator,
 *   primary CTA button, and secondary Sign In action.
 */
@Composable
fun IntroOnboardingScreen(
    state: IntroOnboardingUiState,
    onAction: (IntroOnboardingAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // Subtle breathing/floating dynamics for the three value pills
    val infiniteTransition = rememberInfiniteTransition(label = "floatingStatPills")
    val floatOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pillFloat1"
    )
    val floatOffset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3800, delayMillis = 400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pillFloat2"
    )
    val floatOffset3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, delayMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pillFloat3"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F9FE))
    ) {
        // 1. Edge-to-Edge Fluid Waves Wallpaper
        Image(
            painter = painterResource(id = R.drawable.intro_wallpaper),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Top Scrim for high header legibility
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.70f),
                            Color.White.copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    )
                )
        )

        // 3. Bottom Scrim blending seamlessly into the bottom card
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(240.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.40f),
                            Color.White.copy(alpha = 0.90f)
                        )
                    )
                )
        )

        // 4. Main Screen Content Hierarchy
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Brand Mark + Wordmark + AI Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Logo Container
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(
                                elevation = 3.dp,
                                shape = RoundedCornerShape(10.dp),
                                spotColor = Color(0x260F172A)
                            )
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sorce_ai_official_logo),
                            contentDescription = "Sorce AI Logo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }

                    // Wordmark
                    Text(
                        text = "Sorce",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = OnSurface,
                            letterSpacing = (-0.3).sp
                        )
                    )

                    // AI Chip Badge
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFEFF6FF),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color(0xFFBFDBFE),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "AI",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Primary,
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }

                // Frosted Glass "Skip" Button
                Surface(
                    modifier = Modifier.clickable(
                        role = Role.Button,
                        onClick = { onAction(IntroOnboardingAction.SkipClicked) }
                    ),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.85f),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.95f)),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Skip",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF334155),
                                fontSize = 12.sp
                            )
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Skip Onboarding",
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Mid Hero Section with Floating Value Proposition Pills
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Pill 1: Match Precision (Top Left)
                FloatingStatPill(
                    iconText = "✨",
                    iconBgColor = Color(0xFFEFF6FF),
                    iconBorderColor = Color(0xFFDBEAFE),
                    label = "98% Match Precision",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 24.dp, top = 44.dp)
                        .offset(y = floatOffset1.dp)
                )

                // Pill 2: Role Count (Mid Right)
                FloatingStatPill(
                    iconText = "💼",
                    iconBgColor = Color(0xFFF0F9FF),
                    iconBorderColor = Color(0xFFE0F2FE),
                    label = "50,000+ Top Tech Roles",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp)
                        .offset(y = floatOffset2.dp)
                )

                // Pill 3: Speed / Efficiency (Bottom Left)
                FloatingStatPill(
                    iconText = "🚀",
                    iconBgColor = Color(0xFFEEF2FF),
                    iconBorderColor = Color(0xFFE0E7FF),
                    label = "10x Faster Applications",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 36.dp, bottom = 24.dp)
                        .offset(y = floatOffset3.dp)
                )
            }

            // Bottom Content Sheet
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White,
                shadowElevation = 20.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                        .padding(top = 28.dp, bottom = 16.dp)
                        .navigationBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Headline
                    Text(
                        text = "Find Your Dream Role",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 25.sp,
                            lineHeight = 31.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = OnSurface,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "With Pure AI Precision",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 25.sp,
                            lineHeight = 31.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Primary,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Subtitle Body Copy
                    Text(
                        text = "Sorce analyzes your tech stack, predicts culture fit, and applies to tier-1 roles automatically.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            fontWeight = FontWeight.Normal,
                            color = OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Step / Carousel Indicators (Step 1 Active)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(28.dp)
                                .height(7.dp)
                                .background(Primary, CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .background(Color(0xFFE2E8F0), CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .background(Color(0xFFE2E8F0), CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Primary CTA: Get Started
                    Button(
                        onClick = { onAction(IntroOnboardingAction.GetStartedClicked) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(16.dp),
                                spotColor = Color(0x660B63F6),
                                ambientColor = Color(0x330B63F6)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Get Started",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Secondary Text Link: Already have an account? Sign In
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account? ",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 13.sp
                            )
                        )
                        Text(
                            text = "Sign In",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            ),
                            modifier = Modifier.clickable(
                                role = Role.Button,
                                onClick = { onAction(IntroOnboardingAction.SignInClicked) }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

/**
 * Floating frosted-glass pill badge displaying value propositions.
 */
@Composable
private fun FloatingStatPill(
    iconText: String,
    iconBgColor: Color,
    iconBorderColor: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color.White.copy(alpha = 0.92f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.95f)),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .background(iconBgColor, RoundedCornerShape(8.dp))
                    .border(1.dp, iconBorderColor, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconText,
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface
                )
            )
        }
    }
}

// ============================================================================
// PREVIEWS (design.md §5 & rules.md §28)
// ============================================================================

@Preview(showBackground = true, device = Devices.PIXEL_7)
@Composable
private fun IntroOnboardingScreenPreview() {
    Project308Theme {
        IntroOnboardingScreen(
            state = IntroOnboardingUiState(),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, fontScale = 1.25f)
@Composable
private fun IntroOnboardingScreenFontScalePreview() {
    Project308Theme {
        IntroOnboardingScreen(
            state = IntroOnboardingUiState(),
            onAction = {}
        )
    }
}
