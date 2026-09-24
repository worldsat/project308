package com.uilover.project308.ui.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.R
import com.uilover.project308.data.model.JobDetail
import com.uilover.project308.data.model.JobPerk
import com.uilover.project308.data.model.JobTechnology
import com.uilover.project308.data.repository.DemoCareerRepository
import com.uilover.project308.ui.theme.OnPrimary
import com.uilover.project308.ui.theme.OnPrimaryContainer
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.OutlineVariant
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer
import com.uilover.project308.ui.theme.Project308Theme
import com.uilover.project308.ui.theme.SecondaryContainer
import com.uilover.project308.ui.theme.Spacing
import com.uilover.project308.ui.theme.Surface
import com.uilover.project308.ui.theme.SurfaceContainerHigh
import com.uilover.project308.ui.theme.SurfaceContainerLow

/**
 * JobDetailScreen for Sorce Career AI per design.md §2 and rules.md §15.
 * Faithfully matches the visual reference and specifications.
 */
@Composable
fun JobDetailScreen(
    state: JobDetailUiState,
    onAction: (JobDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val jobDetail = state.jobDetail ?: return

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Surface,
        topBar = {
            JobDetailTopAppBar(
                jobDetail = jobDetail,
                onBackClick = { onAction(JobDetailAction.BackClicked) },
                onProfileClick = { onAction(JobDetailAction.ProfileClicked) }
            )
        },
        bottomBar = {
            JobDetailStickyBottomBar(
                isSaved = state.isSaved,
                applyState = state.applyState,
                onBookmarkToggle = { onAction(JobDetailAction.BookmarkToggled) },
                onApplyClick = { onAction(JobDetailAction.ApplyClicked) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = Spacing.md,
                end = Spacing.md,
                top = Spacing.sm,
                bottom = Spacing.xxl
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. Primary Blue Overview Card
            item {
                JobOverviewHeroCard(jobDetail = jobDetail)
            }

            // 2. Perks & Benefits Section
            item {
                PerksAndBenefitsSection(
                    totalCount = jobDetail.perksTotalCount,
                    perks = jobDetail.perks,
                    onSeeAllClick = { onAction(JobDetailAction.SeeAllPerksClicked) }
                )
            }

            // 3. AI Profile Match Banner
            item {
                AiProfileMatchCard(
                    score = jobDetail.aiMatchScore,
                    description = jobDetail.aiMatchDescription
                )
            }

            // 4. Role Overview Card
            item {
                RoleOverviewCard(
                    overview = jobDetail.roleOverview,
                    responsibilities = jobDetail.responsibilities
                )
            }

            // 5. Key Technologies
            item {
                KeyTechnologiesSection(technologies = jobDetail.keyTechnologies)
            }

            // 6. Meet the Seattle Squad Preview Card
            item {
                SquadPreviewCard(
                    title = jobDetail.squadTitle,
                    description = jobDetail.squadDescription,
                    onSquadClick = { onAction(JobDetailAction.SquadClicked) }
                )
            }
        }
    }
}

/**
 * Top App Bar for Job Detail per rules.md §11.1 and §15.
 */
@Composable
private fun JobDetailTopAppBar(
    jobDetail: JobDetail,
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Surface,
        shadowElevation = 0.5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f, fill = false),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = OnSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.sorce_ai_official_logo),
                    contentDescription = "Sorce Brand Mark",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                )

                Column(modifier = Modifier.weight(1f, fill = false)) {
                    Text(
                        text = "Job Detail",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = "${jobDetail.companyName} • ${jobDetail.roleTitle}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 11.5.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .border(1.5.dp, Primary.copy(alpha = 0.25f), CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .clickable(role = Role.Button, onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Current User Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

/**
 * Primary Blue Job Overview Card with gradient, company logo, verified badge, and metadata.
 */
@Composable
private fun JobOverviewHeroCard(
    jobDetail: JobDetail,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 3.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF0B63F6), Color(0xFF0047B8))
                    )
                )
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Top Row: Logo + Company/Role + Actively Hiring Pill
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Company Logo in White Container (or Branded Monogram)
                        Box(
                            modifier = Modifier
                                .size(58.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (jobDetail.companyLogoRes != null && jobDetail.companyLogoRes != 0) {
                                        Color.White
                                    } else {
                                        jobDetail.monogramColor ?: Primary
                                    }
                                )
                                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                                .padding(
                                    if (jobDetail.companyLogoRes != null && jobDetail.companyLogoRes != 0) 6.dp else 2.dp
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (jobDetail.companyLogoRes != null && jobDetail.companyLogoRes != 0) {
                                Image(
                                    painter = painterResource(id = jobDetail.companyLogoRes),
                                    contentDescription = jobDetail.companyName,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Text(
                                    text = jobDetail.monogramText ?: jobDetail.companyName.take(1),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        fontSize = if ((jobDetail.monogramText ?: "").length > 2) 16.sp else 22.sp
                                    )
                                )
                            }
                        }

                        // Company & Role Header
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = jobDetail.companyName,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 13.sp
                                    )
                                )

                                if (jobDetail.isVerified) {
                                    Icon(
                                        imageVector = Icons.Filled.Verified,
                                        contentDescription = "Verified Company",
                                        tint = Color(0xFF72AEFF),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Text(
                                text = jobDetail.roleTitle,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 19.sp,
                                    lineHeight = 24.sp
                                )
                            )
                        }
                    }

                    // Actively Hiring Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.16f))
                            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(50))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF52FFAA))
                            )
                            Text(
                                text = jobDetail.hiringStatus,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }

                // Divider Line
                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.18f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 2.dp)
                )

                // 3 Metadata Rows
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Location
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Location",
                            tint = Color(0xFFB3C5FF),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = jobDetail.location,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.95f),
                                fontSize = 13.5.sp
                            )
                        )
                    }

                    // Work Style
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = "Work Style",
                            tint = Color(0xFFB3C5FF),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = jobDetail.workStyle,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.95f),
                                fontSize = 13.5.sp
                            )
                        )
                    }

                    // Compensation
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Payments,
                            contentDescription = "Compensation",
                            tint = Color(0xFFB3C5FF),
                            modifier = Modifier.size(18.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = jobDetail.compensation,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.5.sp
                                )
                            )
                            Text(
                                text = " " + jobDetail.compensationSuffix,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.75f),
                                    fontSize = 12.5.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Perks & Benefits 4-column grid section.
 */
@Composable
private fun PerksAndBenefitsSection(
    totalCount: String,
    perks: List<JobPerk>,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Perks & Benefits",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    fontSize = 17.5.sp
                )
            )

            Text(
                text = totalCount,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Primary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                ),
                modifier = Modifier.clickable(role = Role.Button, onClick = onSeeAllClick)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            perks.forEach { perk ->
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    color = SurfaceContainerLow,
                    border = BorderStroke(1.dp, OutlineVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(PrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = perk.icon,
                                contentDescription = perk.title,
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Text(
                            text = perk.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurface,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}

/**
 * AI Profile Match Card with circular progress gauge and resonance summary.
 */
@Composable
private fun AiProfileMatchCard(
    score: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = PrimaryContainer.copy(alpha = 0.65f),
        border = BorderStroke(1.dp, Primary.copy(alpha = 0.15f)),
        shadowElevation = 0.5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = "AI Match",
                        tint = Primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "AI Profile Match",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnPrimaryContainer,
                            fontSize = 15.sp
                        )
                    )
                }

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF3B4856),
                        fontSize = 12.5.sp,
                        lineHeight = 17.sp
                    )
                )
            }

            // Circular Progress Dial
            Box(
                modifier = Modifier.size(54.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 4.5.dp.toPx()
                    // Background track
                    drawCircle(
                        color = Color(0xFFD3DCED),
                        style = Stroke(width = strokeWidth)
                    )
                    // Foreground sweep progress
                    val sweepAngle = 360f * (score / 100f)
                    drawArc(
                        color = Color(0xFF0B63F6),
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                Text(
                    text = "$score%",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnPrimaryContainer,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

/**
 * Role Overview Card with overview text and Core Responsibilities bullet list.
 */
@Composable
private fun RoleOverviewCard(
    overview: String,
    responsibilities: List<String>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Outline),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Role Overview",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    fontSize = 17.5.sp
                )
            )

            Text(
                text = overview,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = OnSurfaceVariant,
                    fontSize = 13.5.sp,
                    lineHeight = 21.sp
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Core Responsibilities",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    fontSize = 14.5.sp
                )
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                responsibilities.forEach { responsibility ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 1.dp)
                        )

                        Text(
                            text = responsibility,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 12.5.sp,
                                lineHeight = 17.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Key Technologies Tag Cloud.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun KeyTechnologiesSection(
    technologies: List<JobTechnology>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Key Technologies",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = OnSurface,
                fontSize = 17.5.sp
            )
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            technologies.forEach { tech ->
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.White,
                    border = BorderStroke(1.dp, Outline),
                    shadowElevation = 0.5.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(if (tech.isPrimary) Primary else OnSurfaceVariant)
                        )

                        Text(
                            text = tech.name,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = OnSurface,
                                fontSize = 12.5.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Meet the Seattle Squad Preview Card per rules.md §10.3 and §15.7.
 */
@Composable
private fun SquadPreviewCard(
    title: String,
    description: String,
    onSquadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onSquadClick),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Outline),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Overlapping avatars + "+4" badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDBEAFE))
                            .border(1.5.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "JS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E40AF),
                                fontSize = 11.sp
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .offset(x = (-8).dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEDE9FE))
                            .border(1.5.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "EK",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5B21B6),
                                fontSize = 11.sp
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .offset(x = (-16).dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(SecondaryContainer)
                            .border(1.5.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+4",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Column(
                    modifier = Modifier.offset(x = (-8).dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            fontSize = 14.sp
                        )
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(SurfaceContainerLow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = "View Squad Details",
                    tint = OnSurface,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

/**
 * Sticky Bottom Action Tray with Bookmark button and Apply CTA pill per rules.md §15.8 and §15.9.
 */
@Composable
private fun JobDetailStickyBottomBar(
    isSaved: Boolean,
    applyState: ApplyState,
    onBookmarkToggle: () -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bookmarkBg by animateColorAsState(
        targetValue = if (isSaved) PrimaryContainer else SurfaceContainerLow,
        animationSpec = tween(200),
        label = "bookmarkBg"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 12.dp),
        color = Color.White.copy(alpha = 0.98f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = Spacing.md, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Secondary Bookmark Toggle Button
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(bookmarkBg)
                        .border(
                            1.dp,
                            if (isSaved) Primary.copy(alpha = 0.3f) else Outline,
                            CircleShape
                        )
                        .clickable(role = Role.Button, onClick = onBookmarkToggle),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = if (isSaved) "Remove Bookmark" else "Bookmark Job",
                        tint = if (isSaved) Primary else OnSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Primary Pill Apply CTA Button
                Button(
                    onClick = onApplyClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (applyState is ApplyState.Sent) Color(0xFF00B06F) else Primary,
                        contentColor = OnPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    when (applyState) {
                        is ApplyState.Idle -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Apply with AI Sorce",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                        }

                        is ApplyState.Preparing -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = OnPrimary,
                                    strokeWidth = 2.dp
                                )
                                Text(
                                    text = "Preparing Match Profile...",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.5.sp
                                    )
                                )
                            }
                        }

                        is ApplyState.Sent -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Application Sent!",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// PREVIEWS (per rules.md §28 and design.md §5)
// ============================================================================

@Preview(name = "JobDetailScreen - Light", showBackground = true, device = Devices.PIXEL_7)
@Composable
private fun JobDetailScreenPreview() {
    Project308Theme {
        JobDetailScreen(
            state = JobDetailUiState(
                jobDetail = DemoCareerRepository.getJobDetail(),
                isSaved = false,
                applyState = ApplyState.Idle
            ),
            onAction = {}
        )
    }
}

@Preview(name = "JobDetailScreen - FontScale 1.25", fontScale = 1.25f, showBackground = true, device = Devices.PIXEL_7)
@Composable
private fun JobDetailScreenFontScalePreview() {
    Project308Theme {
        JobDetailScreen(
            state = JobDetailUiState(
                jobDetail = DemoCareerRepository.getJobDetail(),
                isSaved = true,
                applyState = ApplyState.Sent
            ),
            onAction = {}
        )
    }
}
