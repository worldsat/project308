package com.uilover.project308.ui.applications

import com.uilover.project308.data.model.NavTab

/**
 * Immutable UI State for ApplicationsScreen per rules.md §18 and §19.
 */
data class ApplicationsUiState(
    val selectedFilter: ApplicationFilter = ApplicationFilter.ALL,
    val applications: List<JobApplicationItem> = emptyList(),
    val totalAppliedCount: Int = 12,
    val reviewingCount: Int = 5,
    val interviewingCount: Int = 4,
    val offersCount: Int = 1,
    val selectedNavTab: NavTab = NavTab.APPLICATIONS,
    val feedbackMessage: String? = null
)
