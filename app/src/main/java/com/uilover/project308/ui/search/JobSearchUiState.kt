package com.uilover.project308.ui.search

import com.uilover.project308.data.model.NavTab

/**
 * Filter chip definition for JobSearchScreen per rules.md §14.2.
 */
data class SearchFilterChip(
    val id: String,
    val label: String,
    val countBadge: String? = null,
    val iconType: FilterIconType = FilterIconType.NONE
)

enum class FilterIconType {
    NONE,
    CODE,
    PAYMENTS
}

/**
 * Immutable UI State for JobSearchScreen per rules.md §18 and §19.
 */
data class JobSearchUiState(
    val query: String = "Senior Software",
    val selectedFilterId: String = "all",
    val filterChips: List<SearchFilterChip> = listOf(
        SearchFilterChip(id = "all", label = "All", countBadge = "148"),
        SearchFilterChip(id = "remote", label = "Remote"),
        SearchFilterChip(id = "full_time", label = "Full-time"),
        SearchFilterChip(id = "tech_eng", label = "Tech & Eng", iconType = FilterIconType.CODE),
        SearchFilterChip(id = "design", label = "Design"),
        SearchFilterChip(id = "high_salary", label = "High Salary ($100k+)", iconType = FilterIconType.PAYMENTS)
    ),
    val selectedSort: String = "Relevant",
    val sortOptions: List<String> = listOf("Relevant", "Latest", "Salary (High)", "AI Match"),
    val isAutopilotEnabled: Boolean = false,
    val totalJobsCount: Int = 148,
    val jobs: List<SearchJobItem> = emptyList(),
    val hasUnreadNotifications: Boolean = true,
    val selectedNavTab: NavTab = NavTab.SEARCH
)
