package com.uilover.project308.ui.applications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.ui.components.SorceBottomNavBar
import com.uilover.project308.ui.theme.AppShapes
import com.uilover.project308.ui.theme.OnPrimaryContainer
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.OutlineVariant
import com.uilover.project308.ui.theme.PillAiBg
import com.uilover.project308.ui.theme.PillAiText
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer
import com.uilover.project308.ui.theme.Project308Theme
import com.uilover.project308.ui.theme.Secondary
import com.uilover.project308.ui.theme.SecondaryContainer
import com.uilover.project308.ui.theme.Spacing
import com.uilover.project308.ui.theme.Surface
import com.uilover.project308.ui.theme.SurfaceContainerHigh

/**
 * ApplicationsScreen for Sorce Career AI per design.md §2 and rules.md §18.
 * Displays application tracker, interview stages, countdown alerts, and AI prep tools.
 */
@Composable
fun ApplicationsScreen(
    state: ApplicationsUiState,
    onAction: (ApplicationsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.feedbackMessage) {
        state.feedbackMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            onAction(ApplicationsAction.DismissFeedback)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Surface,
        topBar = {
            ApplicationsTopAppBar(
                activeInterviewsCount = state.interviewingCount,
                onBackClick = { onAction(ApplicationsAction.BackClicked) },
                onAiCoachClick = { onAction(ApplicationsAction.StartMockInterviewClicked) }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                SorceBottomNavBar(
                    selectedTab = state.selectedNavTab,
                    onTabSelected = { onAction(ApplicationsAction.NavTabSelected(it)) }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Pipeline Hero Summary Banner
            item {
                Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                    PipelineHeroBanner(
                        totalApplied = state.totalAppliedCount,
                        reviewing = state.reviewingCount,
                        interviewing = state.interviewingCount,
                        offers = state.offersCount,
                        onPrepareWithAiClick = { onAction(ApplicationsAction.StartMockInterviewClicked) }
                    )
                }
            }

            // 2. Filter Pills Row
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = Spacing.md),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ApplicationFilter.values()) { filter ->
                        val isSelected = filter == state.selectedFilter
                        val countText = when (filter) {
                            ApplicationFilter.ALL -> state.totalAppliedCount
                            ApplicationFilter.INTERVIEWING -> state.interviewingCount
                            ApplicationFilter.UNDER_REVIEW -> state.reviewingCount
                            ApplicationFilter.OFFERS -> state.offersCount
                            ApplicationFilter.ARCHIVED -> 1
                        }

                        FilterChipPill(
                            label = "${filter.label} ($countText)",
                            isSelected = isSelected,
                            onClick = { onAction(ApplicationsAction.FilterSelected(filter)) }
                        )
                    }
                }
            }

            // 3. AI Interview Coach Quick Action Strip
            item {
                Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                    InterviewPrepCoachStrip(
                        onStartPrep = { onAction(ApplicationsAction.StartMockInterviewClicked) }
                    )
                }
            }

            // 4. Header: Active Applications Count
            item {
                Box(
                    modifier = Modifier.padding(horizontal = Spacing.md),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tracked Roles (${state.applications.size})",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                fontSize = 16.sp
                            )
                        )

                        Text(
                            text = "Auto-updated via AI",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariant,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            // 5. Application Items List
            items(state.applications, key = { it.id }) { item ->
                Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                    ApplicationCard(
                        item = item,
                        onClick = { onAction(ApplicationsAction.ApplicationClicked(item.id, item.jobId)) },
                        onAiPrepClick = { onAction(ApplicationsAction.AiInterviewPrepClicked(item.id, item.roleTitle)) },
                        onViewDetailsClick = { onAction(ApplicationsAction.ViewJobDetailsClicked(item.jobId)) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Top App Bar for Applications Screen.
 */
@Composable
private fun ApplicationsTopAppBar(
    activeInterviewsCount: Int,
    onBackClick: () -> Unit,
    onAiCoachClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Surface
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate Back",
                        tint = OnSurface
                    )
                }

                Text(
                    text = "Applications",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 18.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SecondaryContainer)
                        .padding(horizontal = 8.dp, vertical = 2.5.dp)
                ) {
                    Text(
                        text = "$activeInterviewsCount Live",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF006644),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.5.sp
                        )
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onAiCoachClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = "AI Interview Coach",
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Hero Funnel Banner showing the application pipeline metrics and AI alert.
 */
@Composable
private fun PipelineHeroBanner(
    totalApplied: Int,
    reviewing: Int,
    interviewing: Int,
    offers: Int,
    onPrepareWithAiClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = AppShapes.CardHero,
                spotColor = Primary.copy(alpha = 0.35f)
            ),
        shape = AppShapes.CardHero,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.22f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0B63F6),
                            Color(0xFF0952D1),
                            Color(0xFF043399)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Top status pill
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.16f))
                            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(50))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF34D399))
                        )
                        Text(
                            text = "Live Pipeline Active",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        )
                    }

                    Text(
                        text = "92% AI Response Rate",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFDBEAFE),
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    )
                }

                // 4-Stage Funnel Metrics Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FunnelMetricCell(count = totalApplied.toString(), label = "Applied")
                    FunnelSeparator()
                    FunnelMetricCell(count = reviewing.toString(), label = "Reviewing")
                    FunnelSeparator()
                    FunnelMetricCell(
                        count = interviewing.toString(),
                        label = "Interview",
                        isHighlight = true
                    )
                    FunnelSeparator()
                    FunnelMetricCell(
                        count = offers.toString(),
                        label = "Offers",
                        isOffer = true
                    )
                }

                // Divider line
                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.18f),
                    thickness = 1.dp
                )

                // Upcoming Next Event Highlight
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "⚡ Next Round in 2 Days",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color(0xFFA5F3FC),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.5.sp
                            )
                        )
                        Text(
                            text = "Google • Staff UX Architecture",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        )
                    }

                    ElevatedButton(
                        onClick = onPrepareWithAiClick,
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color.White,
                            contentColor = Primary
                        ),
                        shape = AppShapes.ButtonPill,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 7.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Prep with AI",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FunnelMetricCell(
    count: String,
    label: String,
    isHighlight: Boolean = false,
    isOffer: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = when {
                    isOffer -> Color(0xFF34D399)
                    isHighlight -> Color(0xFFFDE047)
                    else -> Color.White
                },
                fontSize = 20.sp
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White.copy(alpha = 0.85f),
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
private fun FunnelSeparator() {
    Icon(
        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
        contentDescription = null,
        tint = Color.White.copy(alpha = 0.4f),
        modifier = Modifier
            .size(14.dp)
            .padding(top = 6.dp)
    )
}

/**
 * Filter Chip Pill item for filtering applications list.
 */
@Composable
private fun FilterChipPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Primary else Color.White)
            .border(
                1.dp,
                if (isSelected) Primary else Outline,
                RoundedCornerShape(20.dp)
            )
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = if (isSelected) Color.White else OnSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 12.sp
            )
        )
    }
}

/**
 * AI Mock Interview Coach Strip.
 */
@Composable
private fun InterviewPrepCoachStrip(
    onStartPrep: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppShapes.CardRegular)
            .background(Color(0xFFEFF6FF))
            .border(1.dp, Primary.copy(alpha = 0.25f), AppShapes.CardRegular)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SmartToy,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        text = "AI Mock Interview Practice",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            fontSize = 13.5.sp
                        )
                    )
                    Text(
                        text = "Real-time voice & coding questions simulator",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            OutlinedButton(
                onClick = onStartPrep,
                shape = AppShapes.ButtonPill,
                border = BorderStroke(1.dp, Primary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Primary
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.height(34.dp)
            ) {
                Text(
                    text = "Launch",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.5.sp
                    )
                )
            }
        }
    }
}

/**
 * Rich Application Card displaying details, pipeline progress stepper, next event, and AI tip.
 */
@Composable
private fun ApplicationCard(
    item: JobApplicationItem,
    onClick: () -> Unit,
    onAiPrepClick: () -> Unit,
    onViewDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.CardRegular,
                spotColor = Color.Black.copy(alpha = 0.04f)
            ),
        shape = AppShapes.CardRegular,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(role = Role.Button, onClick = onClick)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top Row: Company Logo + Names + Stage Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Logo Box
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(1.dp, OutlineVariant, RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (item.companyLogoRes != null) {
                            Image(
                                painter = painterResource(id = item.companyLogoRes),
                                contentDescription = item.companyName,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text(
                                text = item.monogramText ?: item.companyName.take(1),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = item.monogramColor ?: Primary
                                )
                            )
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = item.companyName,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = OnSurfaceVariant,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp
                                )
                            )
                            if (item.isVerified) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = "Verified Company",
                                    tint = Primary,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }

                        Text(
                            text = item.roleTitle,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                fontSize = 15.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Stage Badge
                StageBadge(stage = item.currentStage, stageTitle = item.stageTitle)
            }

            // Salary & Location Info Badges
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Payments,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = item.salaryRange,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = item.location,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // 4-Step Progress Stepper Visual
            ApplicationProgressStepper(stage = item.currentStage)

            // Next Event Scheduling Box (if available)
            if (item.nextActionDate != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF8FAFC))
                        .border(1.dp, OutlineVariant, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(15.dp)
                        )

                        Column {
                            Text(
                                text = item.stageSubtitle,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface,
                                    fontSize = 12.sp
                                )
                            )
                            Text(
                                text = item.nextActionDate,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Primary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }

            // AI Insight Tip Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF0FDF4))
                    .border(1.dp, Color(0xFFDCFCE7), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = Secondary,
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = item.aiTip,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF166534),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onViewDetailsClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    shape = AppShapes.Button,
                    border = BorderStroke(1.dp, Outline),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = OnSurfaceVariant
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = "View Role Details",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.5.sp
                        )
                    )
                }

                ElevatedButton(
                    onClick = onAiPrepClick,
                    modifier = Modifier
                        .weight(1.2f)
                        .height(36.dp),
                    shape = AppShapes.Button,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Primary,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "AI Interview Prep",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.5.sp
                        )
                    )
                }
            }
        }
    }
}

/**
 * 4-Step Visual Stepper (Applied -> Screening -> Interview -> Offer)
 */
@Composable
private fun ApplicationProgressStepper(
    stage: ApplicationStage
) {
    val activeIndex = when (stage) {
        ApplicationStage.APPLIED -> 0
        ApplicationStage.SCREENING -> 1
        ApplicationStage.INTERVIEWING -> 2
        ApplicationStage.OFFER_RECEIVED -> 3
        ApplicationStage.ARCHIVED -> 0
    }

    val stepLabels = listOf("Applied", "Screening", "Interview", "Offer")

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            stepLabels.forEachIndexed { index, _ ->
                val isCompleted = index < activeIndex
                val isCurrent = index == activeIndex
                val isPending = index > activeIndex

                // Step Dot
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isCompleted -> Color(0xFF10B981)
                                isCurrent -> Primary
                                else -> SurfaceContainerHigh
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(11.dp)
                        )
                    } else if (isCurrent) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                    }
                }

                // Connecting Line (except after last step)
                if (index < stepLabels.lastIndex) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.5.dp)
                            .background(
                                if (index < activeIndex) Color(0xFF10B981) else OutlineVariant
                            )
                    )
                }
            }
        }

        // Labels Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stepLabels.forEachIndexed { index, label ->
                val isCurrent = index == activeIndex
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        color = if (isCurrent) Primary else OnSurfaceVariant
                    )
                )
            }
        }
    }
}

/**
 * Stage Badge with color coding depending on stage status.
 */
@Composable
private fun StageBadge(
    stage: ApplicationStage,
    stageTitle: String
) {
    val (bgColor, textColor, borderColor) = when (stage) {
        ApplicationStage.OFFER_RECEIVED -> Triple(
            Color(0xFFE6FBF2),
            Color(0xFF006644),
            Color(0xFF00D284).copy(alpha = 0.5f)
        )
        ApplicationStage.INTERVIEWING -> Triple(
            Color(0xFFFEF3C7),
            Color(0xFF92400E),
            Color(0xFFF59E0B).copy(alpha = 0.5f)
        )
        ApplicationStage.SCREENING,
        ApplicationStage.APPLIED -> Triple(
            PrimaryContainer.copy(alpha = 0.6f),
            OnPrimaryContainer,
            Primary.copy(alpha = 0.3f)
        )
        ApplicationStage.ARCHIVED -> Triple(
            SurfaceContainerHigh,
            OnSurfaceVariant,
            OutlineVariant
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .padding(horizontal = 9.dp, vertical = 3.5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (stage == ApplicationStage.OFFER_RECEIVED) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFF006644),
                    modifier = Modifier.size(11.dp)
                )
            }
            Text(
                text = stageTitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.5.sp
                )
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7)
@Composable
fun ApplicationsScreenPreview() {
    Project308Theme {
        ApplicationsScreen(
            state = ApplicationsUiState(
                applications = ApplicationsViewModel().uiState.value.applications
            ),
            onAction = {}
        )
    }
}
