package com.uilover.project308.ui.search

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.ElectricBolt
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.data.model.NavTab
import com.uilover.project308.ui.components.SorceBottomNavBar
import com.uilover.project308.ui.components.SorceTopAppBar
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer
import com.uilover.project308.ui.theme.Project308Theme
import com.uilover.project308.ui.theme.Spacing
import com.uilover.project308.ui.theme.Surface
import com.uilover.project308.ui.theme.SurfaceContainerHigh

/**
 * Search & Explore Jobs Screen for Sorce Career AI per design.md §2 and rules.md §14.
 */
@Composable
fun JobSearchScreen(
    state: JobSearchUiState,
    onAction: (JobSearchAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Surface,
        topBar = {
            SorceTopAppBar(
                hasUnreadNotifications = state.hasUnreadNotifications,
                onNotificationsClick = { onAction(JobSearchAction.NotificationsClicked) },
                onProfileClick = { onAction(JobSearchAction.ProfileClicked) },
                screenContext = "Search",
                modifier = Modifier.statusBarsPadding()
            )
        },
        bottomBar = {
            SorceBottomNavBar(
                selectedTab = state.selectedNavTab,
                onTabSelected = { onAction(JobSearchAction.NavTabSelected(it)) }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentPadding = PaddingValues(bottom = Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            // 1. Search Bar & Filter Button
            item {
                SearchInputRow(
                    query = state.query,
                    onQueryChange = { onAction(JobSearchAction.QueryChanged(it)) },
                    onClearClick = { onAction(JobSearchAction.ClearQueryClicked) },
                    onFilterClick = { onAction(JobSearchAction.FilterButtonClicked) },
                    modifier = Modifier.padding(horizontal = Spacing.md, vertical = 4.dp)
                )
            }

            // 2. Horizontal Filter Chips
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = Spacing.md),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    items(state.filterChips) { chip ->
                        val isSelected = chip.id == state.selectedFilterId
                        SearchFilterChipItem(
                            chip = chip,
                            isSelected = isSelected,
                            onClick = { onAction(JobSearchAction.FilterChipToggled(chip.id)) }
                        )
                    }
                }
            }

            // 3. Results Summary & Sort Control
            item {
                ResultsSummaryRow(
                    totalJobsCount = state.totalJobsCount,
                    selectedSort = state.selectedSort,
                    onSortClick = { onAction(JobSearchAction.CycleSortClicked) },
                    modifier = Modifier.padding(horizontal = Spacing.md, vertical = 4.dp)
                )
            }

            // 4. Search Feed Job Cards
            items(state.jobs, key = { it.id }) { job ->
                SearchJobCard(
                    job = job,
                    onJobClick = { onAction(JobSearchAction.JobClicked(job.id)) },
                    onBookmarkClick = { onAction(JobSearchAction.BookmarkToggled(job.id)) },
                    modifier = Modifier.padding(horizontal = Spacing.md)
                )
            }

            // 5. Autopilot Application Banner
            item {
                AutopilotBanner(
                    isEnabled = state.isAutopilotEnabled,
                    onToggle = { onAction(JobSearchAction.AutopilotToggled) },
                    modifier = Modifier.padding(horizontal = Spacing.md, vertical = 6.dp)
                )
            }
        }
    }
}

/**
 * Search input field with leading search icon, clear button, and adjacent filter button.
 */
@Composable
private fun SearchInputRow(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Search Input Box
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 2.dp,
            border = BorderStroke(1.dp, Outline.copy(alpha = 0.35f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search job titles, companies, keywords...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = OnSurfaceVariant.copy(alpha = 0.65f),
                                fontSize = 13.5.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(
                            color = OnSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        cursorBrush = SolidColor(Primary)
                    )
                }

                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = onClearClick,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "Clear search",
                            tint = OnSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Filter Action Button with Active Indicator Dot
        Box(
            modifier = Modifier
                .size(48.dp)
                .shadow(elevation = 2.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, Outline.copy(alpha = 0.35f), CircleShape)
                .clickable(role = Role.Button, onClick = onFilterClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = "Filter Options",
                tint = Primary,
                modifier = Modifier.size(22.dp)
            )

            // Active filter dot
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 10.dp)
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(Primary)
                    .border(1.5.dp, Color.White, CircleShape)
            )
        }
    }
}

/**
 * Filter chip with icon, label, count badge, and selection styling.
 */
@Composable
private fun SearchFilterChipItem(
    chip: SearchFilterChip,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(role = Role.Button, onClick = onClick),
        shape = CircleShape,
        color = if (isSelected) Primary else Color.White,
        border = BorderStroke(
            1.dp,
            if (isSelected) Primary else Outline.copy(alpha = 0.45f)
        ),
        shadowElevation = if (isSelected) 3.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Optional Icon
            when (chip.iconType) {
                FilterIconType.CODE -> {
                    Icon(
                        imageVector = Icons.Outlined.Code,
                        contentDescription = null,
                        tint = if (isSelected) Color.White else Primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                FilterIconType.PAYMENTS -> {
                    Icon(
                        imageVector = Icons.Outlined.Payments,
                        contentDescription = null,
                        tint = if (isSelected) Color.White else Primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                FilterIconType.NONE -> Unit
            }

            // Chip Label
            Text(
                text = chip.label,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = if (isSelected) Color.White else OnSurface,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                    fontSize = 12.5.sp
                )
            )

            // Optional Count Badge
            if (chip.countBadge != null) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color.White.copy(alpha = 0.25f)
                            else PrimaryContainer
                        )
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = chip.countBadge,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isSelected) Color.White else Primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.5.sp
                        )
                    )
                }
            }
        }
    }
}

/**
 * Results summary showing job count, AI Curated badge, and sort dropdown trigger.
 */
@Composable
private fun ResultsSummaryRow(
    totalJobsCount: Int,
    selectedSort: String,
    onSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "$totalJobsCount Jobs",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 18.sp
                    )
                )

                // AI Curated Pill
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(PrimaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "AI Curated",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            Text(
                text = "Tailored to your profile & experience",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = OnSurfaceVariant,
                    fontSize = 11.5.sp
                )
            )
        }

        // Sort Control Pill
        Surface(
            modifier = Modifier.clickable(role = Role.Button, onClick = onSortClick),
            shape = CircleShape,
            color = Color.White,
            border = BorderStroke(1.dp, Outline.copy(alpha = 0.45f)),
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Sort: ",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 11.5.sp
                    )
                )
                Text(
                    text = selectedSort,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.5.sp
                    )
                )
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Change sort",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * Search Feed Job Card matching rules.md §14.5 and the supplied reference design.
 */
@Composable
private fun SearchJobCard(
    job: SearchJobItem,
    onJobClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onJobClick),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, Outline.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Company Logo + Title + Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Logo Box
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (job.isStripeMonogram) Primary else Color(0xFFF1F4F9))
                            .border(1.dp, Outline.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (job.isStripeMonogram) {
                            // Native Stripe Monogram per rules.md §10.1
                            Text(
                                text = "S",
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic,
                                    color = Color.White
                                )
                            )
                        } else if (job.companyLogoRes != null) {
                            Image(
                                painter = painterResource(id = job.companyLogoRes),
                                contentDescription = job.companyName,
                                modifier = Modifier.size(28.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = job.companyName,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = OnSurfaceVariant,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.5.sp
                                )
                            )
                            if (job.isVerified) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified company",
                                    tint = Primary,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }

                        Text(
                            text = job.roleTitle,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                fontSize = 16.5.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Bookmark Toggle Button
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F4F9))
                        .clickable(role = Role.Button, onClick = onBookmarkClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (job.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (job.isBookmarked) Primary else OnSurfaceVariant,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }

            // Compensation & Posted Time Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = job.salaryRange,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = job.salaryUnit,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    )
                }

                // Time Badge
                if (job.isJustNow) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFFDBE1FF))
                            .padding(horizontal = 9.dp, vertical = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ElectricBolt,
                                contentDescription = null,
                                tint = Color(0xFF001849),
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "Just now",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF001849),
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 9.dp, vertical = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Schedule,
                                contentDescription = null,
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = job.postedTime,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }

            // Location & Work Style Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = job.location,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 12.sp
                    )
                )

                // Dot separator
                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .clip(CircleShape)
                        .background(OnSurfaceVariant)
                )

                if (job.isRemote) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Wifi,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "Remote",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        )
                    }
                } else {
                    Text(
                        text = job.workStyle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurface,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            // Tag Strip & Match Score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    job.tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(SurfaceContainerHigh)
                                .padding(horizontal = 9.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSurfaceVariant,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    if (job.highlightTag != null) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(PrimaryContainer)
                                .padding(horizontal = 9.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = job.highlightTag,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }

                // Match Score Pill
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFE0ECFF))
                        .padding(horizontal = 9.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "${job.matchScore}% Match",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurface,
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

/**
 * Autopilot Application promo banner per rules.md §14.6.
 */
@Composable
private fun AutopilotBanner(
    isEnabled: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Primary,
        shadowElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SmartToy,
                        contentDescription = "Autopilot AI",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Autopilot Application",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = "Let Sorce AI auto-tailor your resume",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Surface(
                modifier = Modifier.clickable(role = Role.Button, onClick = onToggle),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Text(
                    text = if (isEnabled) "Enabled" else "Enable",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp
                    ),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                )
            }
        }
    }
}

// ============================================================================
// PREVIEWS (design.md §5 & rules.md §28)
// ============================================================================

@Preview(showBackground = true, device = Devices.PIXEL_7)
@Composable
private fun JobSearchScreenPreview() {
    Project308Theme {
        JobSearchScreen(
            state = JobSearchUiState(
                jobs = JobSearchViewModel.getInitialGroundedJobs()
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, fontScale = 1.25f)
@Composable
private fun JobSearchScreenFontScalePreview() {
    Project308Theme {
        JobSearchScreen(
            state = JobSearchUiState(
                jobs = JobSearchViewModel.getInitialGroundedJobs()
            ),
            onAction = {}
        )
    }
}
