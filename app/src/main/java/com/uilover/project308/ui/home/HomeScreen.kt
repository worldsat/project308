package com.uilover.project308.ui.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.data.repository.DemoCareerRepository
import com.uilover.project308.ui.components.AiDiscoveryHeroCard
import com.uilover.project308.ui.components.CareerCoachBanner
import com.uilover.project308.ui.components.HighMatchJobCard
import com.uilover.project308.ui.components.HiringCompanyCard
import com.uilover.project308.ui.components.JobCategoryCard
import com.uilover.project308.ui.components.SearchAiTriggerBar
import com.uilover.project308.ui.components.SorceBottomNavBar
import com.uilover.project308.ui.components.SorceTopAppBar
import com.uilover.project308.ui.theme.OnPrimary
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.Project308Theme
import com.uilover.project308.ui.theme.Spacing
import com.uilover.project308.ui.theme.Surface
import com.uilover.project308.ui.theme.SurfaceContainerHigh

/**
 * HomeScreen (Discovery Dashboard) for Sorce Career AI per design.md §2, §3 and rules.md §13.
 */
@Composable
fun HomeScreen(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Surface,
        topBar = {
            SorceTopAppBar(
                hasUnreadNotifications = state.hasUnreadNotifications,
                onNotificationsClick = { onAction(HomeAction.NotificationBellClicked) },
                onProfileClick = { onAction(HomeAction.ProfileClicked) }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                SorceBottomNavBar(
                    selectedTab = state.selectedNavTab,
                    onTabSelected = { onAction(HomeAction.NavTabSelected(it)) }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            // 1. Search Bar with AI Filter Trigger
            item {
                Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                    SearchAiTriggerBar(
                        query = state.searchQuery,
                        onQueryChange = { onAction(HomeAction.SearchQueryChanged(it)) },
                        onAiFilterClick = { onAction(HomeAction.AiFilterClicked) },
                        onSearchClick = { onAction(HomeAction.SearchSubmitted) }
                    )
                }
            }

            // 2. AI Discovery Hero Card
            item {
                Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                    AiDiscoveryHeroCard(
                        onFindJobsClick = { onAction(HomeAction.FindJobsWithAiClicked) }
                    )
                }
            }

            // 3. Top Hiring Companies Carousel
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Header Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.md),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                        ) {
                            Text(
                                text = "Top Hiring Companies",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface,
                                    fontSize = 17.5.sp
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(SurfaceContainerHigh)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Active",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = OnSurfaceVariant,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Text(
                            text = "See all",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            ),
                            modifier = Modifier.clickable(
                                role = Role.Button,
                                onClick = { onAction(HomeAction.SeeAllCompaniesClicked) }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Horizontal Companies Carousel
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = Spacing.md),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.topHiringCompanies, key = { it.id }) { company ->
                            HiringCompanyCard(
                                company = company,
                                onClick = { onAction(HomeAction.CompanyClicked(company.id)) }
                            )
                        }
                    }
                }
            }

            // 4. Popular Job Categories Grid
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.md)
                ) {
                    // Header Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Popular Job Categories",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                fontSize = 17.5.sp
                            )
                        )

                        Text(
                            text = "See all",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            ),
                            modifier = Modifier.clickable(
                                role = Role.Button,
                                onClick = { onAction(HomeAction.SeeAllCategoriesClicked) }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 2x2 Categories Grid
                    val categories = state.popularJobCategories
                    if (categories.size >= 4) {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            // Row 1: Engineering & Data & AI
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    JobCategoryCard(
                                        category = categories[0],
                                        onClick = { onAction(HomeAction.CategoryClicked(categories[0].id)) }
                                    )
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    JobCategoryCard(
                                        category = categories[1],
                                        onClick = { onAction(HomeAction.CategoryClicked(categories[1].id)) }
                                    )
                                }
                            }

                            // Row 2: Design & Marketing
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    JobCategoryCard(
                                        category = categories[2],
                                        onClick = { onAction(HomeAction.CategoryClicked(categories[2].id)) }
                                    )
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    JobCategoryCard(
                                        category = categories[3],
                                        onClick = { onAction(HomeAction.CategoryClicked(categories[3].id)) }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 5. Recent High Matches Stream
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.md)
                ) {
                    // Header Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                        ) {
                            Text(
                                text = "Recent High Matches",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface,
                                    fontSize = 17.5.sp
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${state.recentHighMatches.size.coerceAtLeast(3)}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = OnPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Text(
                            text = "View radar",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            ),
                            modifier = Modifier.clickable(
                                role = Role.Button,
                                onClick = { onAction(HomeAction.ViewRadarClicked) }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Job Cards List
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        state.recentHighMatches.forEach { job ->
                            HighMatchJobCard(
                                job = job,
                                onJobClick = { onAction(HomeAction.JobClicked(job.id)) },
                                onBookmarkToggle = { onAction(HomeAction.BookmarkToggled(job.id)) },
                                onApplyClick = { onAction(HomeAction.ApplyClicked(job.id)) }
                            )
                        }
                    }
                }
            }

            // 6. Ask AI Career Assistant Mini Banner
            item {
                Box(modifier = Modifier.padding(horizontal = Spacing.md)) {
                    CareerCoachBanner(
                        onClick = { onAction(HomeAction.AskAiAssistantClicked) }
                    )
                }
            }

            // Bottom spacer for scroll comfort
            item {
                Spacer(modifier = Modifier.height(Spacing.sm))
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7)
@Composable
fun HomeScreenPreview() {
    Project308Theme {
        HomeScreen(
            state = HomeUiState(
                topHiringCompanies = DemoCareerRepository.getTopHiringCompanies(),
                popularJobCategories = DemoCareerRepository.getPopularJobCategories(),
                recentHighMatches = DemoCareerRepository.getRecentHighMatches()
            ),
            onAction = {}
        )
    }
}
