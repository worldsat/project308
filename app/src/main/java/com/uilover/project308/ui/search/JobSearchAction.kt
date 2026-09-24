package com.uilover.project308.ui.search

import com.uilover.project308.data.model.NavTab

/**
 * User actions and events for JobSearchScreen per rules.md §14, §18, and §25.
 */
sealed interface JobSearchAction {
    data class QueryChanged(val query: String) : JobSearchAction
    data object ClearQueryClicked : JobSearchAction
    data class FilterChipToggled(val chipId: String) : JobSearchAction
    data object FilterButtonClicked : JobSearchAction
    data class SortSelected(val sortOption: String) : JobSearchAction
    data object CycleSortClicked : JobSearchAction
    data class BookmarkToggled(val jobId: String) : JobSearchAction
    data class JobClicked(val jobId: String) : JobSearchAction
    data object AutopilotToggled : JobSearchAction
    data class NavTabSelected(val tab: NavTab) : JobSearchAction
    data object NotificationsClicked : JobSearchAction
    data object ProfileClicked : JobSearchAction
}
