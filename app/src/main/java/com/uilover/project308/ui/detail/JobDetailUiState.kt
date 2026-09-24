package com.uilover.project308.ui.detail

import com.uilover.project308.data.model.JobDetail

/**
 * Represents the discrete phases of the Apply micro-interaction per rules.md §15.9.
 */
sealed interface ApplyState {
    data object Idle : ApplyState
    data object Preparing : ApplyState
    data object Sent : ApplyState
}

/**
 * Immutable UI State for JobDetailScreen per rules.md §18 and §19.
 */
data class JobDetailUiState(
    val jobDetail: JobDetail? = null,
    val isSaved: Boolean = false,
    val applyState: ApplyState = ApplyState.Idle,
    val isLoading: Boolean = false,
    val error: String? = null
)
