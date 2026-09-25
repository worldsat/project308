package com.uilover.project308.ui.search

import com.uilover.project308.data.model.NavTab

/**
 * User actions and events for JobSearchScreen matching Flutter job_search_screen.dart.
 */
sealed interface JobSearchAction {
    data class QueryChanged(val query: String) : JobSearchAction
    data object ClearQueryClicked : JobSearchAction
    data class CategorySelected(val category: String) : JobSearchAction
    data class FilterChipToggled(val chipId: String) : JobSearchAction
    data object ClearFiltersClicked : JobSearchAction
    data object AiFilterClicked : JobSearchAction
    data object FilterButtonClicked : JobSearchAction
    data object SnackbarDismissed : JobSearchAction
    data class BookmarkToggled(val jobId: String) : JobSearchAction
    data class JobClicked(val jobId: String) : JobSearchAction
    data class ApplyClicked(val jobId: String) : JobSearchAction
    data class NavTabSelected(val tab: NavTab) : JobSearchAction
    data object NotificationsClicked : JobSearchAction
    data object ProfileClicked : JobSearchAction
}
