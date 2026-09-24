package com.uilover.project308.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uilover.project308.data.repository.DemoCareerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for JobDetailScreen per rules.md §15 and §18.
 */
class JobDetailViewModel(
    private val jobId: String = "amazon_senior_swe"
) : ViewModel() {

    private val initialDetail = DemoCareerRepository.getJobDetail(jobId)

    private val _uiState = MutableStateFlow(
        JobDetailUiState(
            jobDetail = initialDetail,
            isSaved = initialDetail.isBookmarked,
            applyState = ApplyState.Idle
        )
    )
    val uiState: StateFlow<JobDetailUiState> = _uiState.asStateFlow()

    fun loadJob(newJobId: String) {
        val detail = DemoCareerRepository.getJobDetail(newJobId)
        _uiState.update {
            it.copy(
                jobDetail = detail,
                isSaved = detail.isBookmarked,
                applyState = ApplyState.Idle
            )
        }
    }

    fun onAction(action: JobDetailAction) {
        when (action) {
            is JobDetailAction.BookmarkToggled -> {
                val currentJob = _uiState.value.jobDetail
                if (currentJob != null) {
                    val newSaved = DemoCareerRepository.toggleBookmark(currentJob.id)
                    _uiState.update { currentState ->
                        currentState.copy(
                            isSaved = newSaved,
                            jobDetail = currentState.jobDetail?.copy(isBookmarked = newSaved)
                        )
                    }
                }
            }
            is JobDetailAction.ApplyClicked -> {
                val currentApplyState = _uiState.value.applyState
                if (currentApplyState is ApplyState.Idle) {
                    _uiState.update { it.copy(applyState = ApplyState.Preparing) }
                    viewModelScope.launch {
                        delay(1200L)
                        _uiState.update { it.copy(applyState = ApplyState.Sent) }
                    }
                }
            }
            is JobDetailAction.BackClicked -> {}
            is JobDetailAction.SeeAllPerksClicked -> {}
            is JobDetailAction.SquadClicked -> {}
            is JobDetailAction.ProfileClicked -> {}
        }
    }
}
