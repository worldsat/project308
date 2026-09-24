package com.uilover.project308.ui.home

import com.uilover.project308.data.model.NavTab

/**
 * User actions and events for HomeScreen per rules.md §18 and §25.
 */
sealed interface HomeAction {
    data class SearchQueryChanged(val query: String) : HomeAction
    data object AiFilterClicked : HomeAction
    data object SearchSubmitted : HomeAction
    data object FindJobsWithAiClicked : HomeAction
    data object SeeAllCompaniesClicked : HomeAction
    data class CompanyClicked(val companyId: String) : HomeAction
    data object SeeAllCategoriesClicked : HomeAction
    data class CategoryClicked(val categoryId: String) : HomeAction
    data object ViewRadarClicked : HomeAction
    data class BookmarkToggled(val jobId: String) : HomeAction
    data class ApplyClicked(val jobId: String) : HomeAction
    data class JobClicked(val jobId: String) : HomeAction
    data object AskAiAssistantClicked : HomeAction
    data class NavTabSelected(val tab: NavTab) : HomeAction
    data object NotificationBellClicked : HomeAction
    data object ProfileClicked : HomeAction
}
