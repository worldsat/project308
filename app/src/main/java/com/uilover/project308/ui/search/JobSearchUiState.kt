package com.uilover.project308.ui.search

import com.uilover.project308.data.model.JobMatch
import com.uilover.project308.data.model.NavTab

/**
 * UI State for JobSearchScreen matching Flutter job_search_screen.dart.
 */
data class JobSearchUiState(
    val query: String = "",
    val selectedCategory: String = "All",
    val filterCategories: List<String> = listOf(
        "All",
        "Engineering",
        "Design",
        "Data & AI",
        "Marketing",
        "Remote"
    ),
    val allJobs: List<JobMatch> = emptyList(),
    val filteredJobs: List<JobMatch> = emptyList(),
    val snackbarMessage: String? = null,
    val hasUnreadNotifications: Boolean = false,
    val selectedNavTab: NavTab = NavTab.SEARCH
)
