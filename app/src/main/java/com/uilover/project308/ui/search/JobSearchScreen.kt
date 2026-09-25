package com.uilover.project308.ui.search

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.data.repository.DemoCareerRepository
import com.uilover.project308.ui.components.HighMatchJobCard
import com.uilover.project308.ui.components.SearchAiTriggerBar
import com.uilover.project308.ui.components.SorceBottomNavBar
import com.uilover.project308.ui.components.SorceTopAppBar
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.Project308Theme
import com.uilover.project308.ui.theme.Surface

/**
 * Search & Explore Jobs Screen matching Flutter's job_search_screen.dart 1:1.
 */
@Composable
fun JobSearchScreen(
    state: JobSearchUiState,
    onAction: (JobSearchAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onAction(JobSearchAction.SnackbarDismissed)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Surface,
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            // 1. Search Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp)
            ) {
                SearchAiTriggerBar(
                    query = state.query,
                    onQueryChange = { onAction(JobSearchAction.QueryChanged(it)) },
                    onAiFilterClick = { onAction(JobSearchAction.AiFilterClicked) },
                    onSearchClick = { onAction(JobSearchAction.AiFilterClicked) }
                )
            }

            // 2. Filter Category Chips
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(state.filterCategories) { category ->
                        val isSelected = category == state.selectedCategory
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) Primary else Color.White,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) Primary else Outline
                            ),
                            modifier = Modifier.clickable(
                                role = Role.Button,
                                onClick = { onAction(JobSearchAction.CategorySelected(category)) }
                            )
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = category,
                                    style = TextStyle(
                                        fontSize = 12.5.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else OnSurface
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Result Count Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Showing ${state.filteredJobs.size} matching roles",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceVariant
                    )
                )

                if (state.query.isNotEmpty() || state.selectedCategory != "All") {
                    Text(
                        text = "Clear filters",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Primary
                        ),
                        modifier = Modifier.clickable(
                            role = Role.Button,
                            onClick = { onAction(JobSearchAction.ClearFiltersClicked) }
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Search Results List
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (state.filteredJobs.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.SearchOff,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No roles found",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try clearing filters or searching for another keyword",
                            style = TextStyle(
                                fontSize = 12.5.sp,
                                color = OnSurfaceVariant
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.filteredJobs, key = { it.id }) { job ->
                            HighMatchJobCard(
                                job = job,
                                onJobClick = { onAction(JobSearchAction.JobClicked(job.id)) },
                                onApplyClick = { onAction(JobSearchAction.JobClicked(job.id)) },
                                onBookmarkToggle = { onAction(JobSearchAction.BookmarkToggled(job.id)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// PREVIEWS
// ============================================================================

@Preview(showBackground = true, device = Devices.PIXEL_7)
@Composable
private fun JobSearchScreenPreview() {
    val jobs = DemoCareerRepository.getAllJobs()
    Project308Theme {
        JobSearchScreen(
            state = JobSearchUiState(
                allJobs = jobs,
                filteredJobs = jobs
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7, fontScale = 1.25f)
@Composable
private fun JobSearchScreenFontScalePreview() {
    val jobs = DemoCareerRepository.getAllJobs()
    Project308Theme {
        JobSearchScreen(
            state = JobSearchUiState(
                allJobs = jobs,
                filteredJobs = jobs
            ),
            onAction = {}
        )
    }
}
