package com.uilover.project308.ui.saved

import com.uilover.project308.data.model.NavTab

/**
 * User actions and events for SavedJobsScreen per rules.md §18 and §25.
 */
sealed interface SavedJobsAction {
    data object BackClicked : SavedJobsAction
    data class FilterSelected(val filter: SavedJobsFilter) : SavedJobsAction
    data class JobClicked(val jobId: String) : SavedJobsAction
    data class ApplyClicked(val jobId: String) : SavedJobsAction
    data class BookmarkToggled(val jobId: String) : SavedJobsAction
    data class UndoBookmark(val jobId: String) : SavedJobsAction
    data object CompareWithAiClicked : SavedJobsAction
    data object ExploreJobsClicked : SavedJobsAction
    data class NavTabSelected(val tab: NavTab) : SavedJobsAction
    data object DismissFeedback : SavedJobsAction
}
