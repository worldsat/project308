package com.uilover.project308.ui.saved

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.data.model.JobMatch
import com.uilover.project308.ui.components.SorceBottomNavBar
import com.uilover.project308.ui.theme.AppShapes
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer
import com.uilover.project308.ui.theme.Project308Theme
import com.uilover.project308.ui.theme.Secondary
import com.uilover.project308.ui.theme.SecondaryContainer
import com.uilover.project308.ui.theme.Spacing
import com.uilover.project308.ui.theme.Surface
import com.uilover.project308.ui.theme.SurfaceContainerHigh

/**
 * SavedJobsScreen for Sorce Career AI faithfully matching the Flutter implementation.
 * Displays bookmarked jobs, AI match resonance summary banner, category filter pills,
 * rich cards with View Details & Quick Apply buttons, and an empty state.
 */
@Composable
fun SavedJobsScreen(
    state: SavedJobsUiState,
    onAction: (SavedJobsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.feedbackMessage) {
        state.feedbackMessage?.let { msg ->
            val result = if (state.lastUnbookmarkedJobId != null) {
                snackbarHostState.showSnackbar(
                    message = msg,
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Short
                )
            } else {
                snackbarHostState.showSnackbar(
                    message = msg,
                    duration = SnackbarDuration.Short
                )
            }
            if (result == SnackbarResult.ActionPerformed) {
                state.lastUnbookmarkedJobId?.let { id ->
                    onAction(SavedJobsAction.UndoBookmark(id))
                }
            }
            onAction(SavedJobsAction.DismissFeedback)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Surface,
        topBar = {
            SavedTopAppBar(
                savedCount = state.totalSavedCount,
                onBackClick = { onAction(SavedJobsAction.BackClicked) },
                onCompareClick = { onAction(SavedJobsAction.CompareWithAiClicked) }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                SorceBottomNavBar(
                    selectedTab = state.selectedNavTab,
                    onTabSelected = { onAction(SavedJobsAction.NavTabSelected(it)) }
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
            // 1. AI Resonance Radar Hero Banner
            if (state.totalSavedCount > 0) {
                item {
                    Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                        AiSavedRadarBanner(
                            averageMatchScore = state.averageMatchScore,
                            totalCount = state.totalSavedCount,
                            onCompareClick = { onAction(SavedJobsAction.CompareWithAiClicked) }
                        )
                    }
                }
            }

            // 2. Filter Pills Row
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = Spacing.md),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(SavedJobsFilter.values()) { filter ->
                        val isSelected = filter == state.selectedFilter
                        val labelText = when (filter) {
                            SavedJobsFilter.ALL -> "All (${state.totalSavedCount})"
                            else -> filter.label
                        }

                        SavedFilterPill(
                            label = labelText,
                            isSelected = isSelected,
                            onClick = { onAction(SavedJobsAction.FilterSelected(filter)) }
                        )
                    }
                }
            }

            // 3. Saved Roles Count Header
            item {
                Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Bookmarked Roles (${state.savedJobs.size})",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                fontSize = 15.sp
                            )
                        )

                        Text(
                            text = "Auto-synced with AI Radar",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariant,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            // 4. Saved Jobs List or Empty State
            if (state.savedJobs.isEmpty()) {
                item {
                    Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                        SavedJobsEmptyState(
                            onExploreClick = { onAction(SavedJobsAction.ExploreJobsClicked) }
                        )
                    }
                }
            } else {
                items(state.savedJobs, key = { it.id }) { job ->
                    Box(modifier = Modifier.padding(horizontal = Spacing.md, vertical = 2.dp)) {
                        SavedJobCard(
                            job = job,
                            onCardClick = { onAction(SavedJobsAction.JobClicked(job.id)) },
                            onApplyClick = { onAction(SavedJobsAction.ApplyClicked(job.id)) },
                            onBookmarkToggle = { onAction(SavedJobsAction.BookmarkToggled(job.id)) }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

/**
 * Custom Top App Bar matching Flutter Saved Jobs screen.
 */
@Composable
private fun SavedTopAppBar(
    savedCount: Int,
    onBackClick: () -> Unit,
    onCompareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate Back",
                        tint = OnSurface
                    )
                }

                Text(
                    text = "Saved Jobs",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 18.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(PrimaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "$savedCount Bookmarked",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            IconButton(
                onClick = onCompareClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = "Compare with AI",
                    tint = Primary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

/**
 * AI Saved Radar Banner matching Flutter's exact hero container, gradient, and full-width button.
 */
@Composable
private fun AiSavedRadarBanner(
    averageMatchScore: Int,
    totalCount: Int,
    onCompareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Primary.copy(alpha = 0.28f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0B63F6),
                        Color(0xFF004ECC)
                    )
                )
            )
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            // Top Row: Radar Icon + Title + Avg Fit Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Radar,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = "AI Match Resonance Radar",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Secondary)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "$averageMatchScore% Avg Fit",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            Text(
                text = "You have $totalCount bookmarked positions. 3 roles have fast-filling applicant velocity this week.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    fontSize = 12.5.sp,
                    lineHeight = 17.5.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Full width Compare Button
            Button(
                onClick = onCompareClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Primary
                ),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Compare Saved Roles with AI",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Primary,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

/**
 * Filter Chip Pill for Saved Jobs filtering matching Flutter style.
 */
@Composable
private fun SavedFilterPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Primary else Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) Primary else Outline,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = if (isSelected) Color.White else OnSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 12.5.sp
            )
        )
    }
}

/**
 * Rich Saved Job Card matching Flutter's _buildSavedJobCard layout:
 * Header (Logo, Title, Verified, Company • Location, Bookmark button)
 * Resonance & Salary Bar
 * Perks Chips
 * Actions Row (View Details & Quick Apply)
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SavedJobCard(
    job: JobMatch,
    onCardClick: () -> Unit,
    onApplyClick: () -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.02f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Logo, Title, Bookmark Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Logo 42x42
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .border(1.dp, Outline, RoundedCornerShape(10.dp))
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (job.companyLogoRes != 0) {
                        Image(
                            painter = painterResource(id = job.companyLogoRes),
                            contentDescription = job.companyName,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PrimaryContainer, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = job.companyName.take(1),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Primary,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Title + Subtitle Column
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = job.roleTitle,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )

                        if (job.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = "Verified Company",
                                tint = Primary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${job.companyName} • ${job.location}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Bookmark icon button
                IconButton(
                    onClick = onBookmarkToggle,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (job.isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (job.isBookmarked) Primary else OnSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Match Resonance & Compensation Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(SecondaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Bolt,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "${job.matchScore}% Resonance",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00875A)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = job.salaryRange,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            // Perks Chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                job.perks.forEach { perk ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = perk,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = OnSurfaceVariant
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Actions Row: View Details & Quick Apply
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onCardClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Outline),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = OnSurface
                    ),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text(
                        text = "View Details",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Button(
                    onClick = onApplyClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text(
                        text = "Quick Apply",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

/**
 * Empty State for Saved Jobs screen matching Flutter implementation.
 */
@Composable
private fun SavedJobsEmptyState(
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "No Saved Roles Found",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Bookmark top opportunities while exploring or clear your active filter to review your list.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = OnSurfaceVariant,
                    fontSize = 12.5.sp,
                    lineHeight = 17.5.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onExploreClick,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Primary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Primary
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Explore AI Recommendations",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Primary
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7)
@Composable
fun SavedJobsScreenPreview() {
    Project308Theme {
        SavedJobsScreen(
            state = SavedJobsUiState(
                savedJobs = SavedJobsViewModel().uiState.value.savedJobs,
                totalSavedCount = 6,
                averageMatchScore = 94
            ),
            onAction = {}
        )
    }
}
