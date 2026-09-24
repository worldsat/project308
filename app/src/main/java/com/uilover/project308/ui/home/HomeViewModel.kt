package com.uilover.project308.ui.home

import androidx.lifecycle.ViewModel
import com.uilover.project308.data.repository.DemoCareerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for HomeScreen per rules.md §18 and MVVM/UDF architecture.
 */
class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            topHiringCompanies = DemoCareerRepository.getTopHiringCompanies(),
            popularJobCategories = DemoCareerRepository.getPopularJobCategories(),
            recentHighMatches = DemoCareerRepository.getRecentHighMatches()
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.SearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = action.query) }
            }
            is HomeAction.BookmarkToggled -> {
                val newStatus = DemoCareerRepository.toggleBookmark(action.jobId)
                _uiState.update { currentState ->
                    val updatedMatches = currentState.recentHighMatches.map { match ->
                        if (match.id == action.jobId) {
                            match.copy(isBookmarked = newStatus)
                        } else {
                            match
                        }
                    }
                    currentState.copy(recentHighMatches = updatedMatches)
                }
            }
            is HomeAction.NavTabSelected -> {
                _uiState.update { it.copy(selectedNavTab = action.tab) }
            }
            is HomeAction.NotificationBellClicked -> {
                _uiState.update { it.copy(hasUnreadNotifications = false) }
            }
            is HomeAction.AiFilterClicked -> {
                // Grounded micro-interaction: placeholder for filter action
            }
            is HomeAction.SearchSubmitted -> {
                // Grounded navigation/search action
            }
            is HomeAction.FindJobsWithAiClicked -> {
                // Grounded AI search CTA
            }
            is HomeAction.SeeAllCompaniesClicked -> {}
            is HomeAction.CompanyClicked -> {}
            is HomeAction.SeeAllCategoriesClicked -> {}
            is HomeAction.CategoryClicked -> {}
            is HomeAction.ViewRadarClicked -> {}
            is HomeAction.ApplyClicked -> {}
            is HomeAction.JobClicked -> {}
            is HomeAction.AskAiAssistantClicked -> {}
            is HomeAction.ProfileClicked -> {}
        }
    }
}
