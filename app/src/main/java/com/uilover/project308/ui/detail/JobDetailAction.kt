package com.uilover.project308.ui.detail

/**
 * User actions and events for JobDetailScreen per rules.md §18 and §25.
 */
sealed interface JobDetailAction {
    data object BackClicked : JobDetailAction
    data object BookmarkToggled : JobDetailAction
    data object ApplyClicked : JobDetailAction
    data object SeeAllPerksClicked : JobDetailAction
    data object SquadClicked : JobDetailAction
    data object ProfileClicked : JobDetailAction
}
