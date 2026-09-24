package com.uilover.project308.ui.saved

import com.uilover.project308.data.model.JobMatch
import com.uilover.project308.data.model.NavTab

/**
 * Filter categories for Saved Jobs screen.
 */
enum class SavedJobsFilter(val label: String) {
    ALL("All"),
    TOP_MATCH("Top Match (>93%)"),
    REMOTE("Remote / Hybrid"),
    ENGINEERING("Engineering"),
    DESIGN("Design")
}

/**
 * Immutable UI State for SavedJobsScreen per rules.md §18 and §19.
 */
data class SavedJobsUiState(
    val selectedFilter: SavedJobsFilter = SavedJobsFilter.ALL,
    val savedJobs: List<JobMatch> = emptyList(),
    val totalSavedCount: Int = 0,
    val averageMatchScore: Int = 94,
    val urgentCount: Int = 3,
    val selectedNavTab: NavTab = NavTab.SAVED,
    val feedbackMessage: String? = null
)
