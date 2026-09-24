package com.uilover.project308.ui.home

import com.uilover.project308.data.model.Company
import com.uilover.project308.data.model.JobCategory
import com.uilover.project308.data.model.JobMatch
import com.uilover.project308.data.model.NavTab

/**
 * Immutable UI State for HomeScreen per rules.md §18 and §19.
 */
data class HomeUiState(
    val searchQuery: String = "",
    val hasUnreadNotifications: Boolean = true,
    val topHiringCompanies: List<Company> = emptyList(),
    val popularJobCategories: List<JobCategory> = emptyList(),
    val recentHighMatches: List<JobMatch> = emptyList(),
    val selectedNavTab: NavTab = NavTab.HOME
)
