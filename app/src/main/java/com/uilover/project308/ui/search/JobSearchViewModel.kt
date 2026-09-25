package com.uilover.project308.ui.search

import androidx.lifecycle.ViewModel
import com.uilover.project308.data.model.JobMatch
import com.uilover.project308.data.repository.DemoCareerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for JobSearchScreen managing state and search interactions matching Flutter job_search_screen.dart.
 */
class JobSearchViewModel : ViewModel() {

    private val initialJobs = DemoCareerRepository.getAllJobs()

    private val _uiState = MutableStateFlow(
        JobSearchUiState(
            allJobs = initialJobs,
            filteredJobs = filterJobs(initialJobs, "", "All")
        )
    )
    val uiState: StateFlow<JobSearchUiState> = _uiState.asStateFlow()

    fun onAction(action: JobSearchAction) {
        when (action) {
            is JobSearchAction.QueryChanged -> {
                _uiState.update { current ->
                    current.copy(
                        query = action.query,
                        filteredJobs = filterJobs(current.allJobs, action.query, current.selectedCategory)
                    )
                }
            }
            is JobSearchAction.ClearQueryClicked -> {
                _uiState.update { current ->
                    current.copy(
                        query = "",
                        filteredJobs = filterJobs(current.allJobs, "", current.selectedCategory)
                    )
                }
            }
            is JobSearchAction.CategorySelected -> {
                _uiState.update { current ->
                    current.copy(
                        selectedCategory = action.category,
                        filteredJobs = filterJobs(current.allJobs, current.query, action.category)
                    )
                }
            }
            is JobSearchAction.FilterChipToggled -> {
                val category = when (action.chipId.lowercase()) {
                    "all" -> "All"
                    "remote" -> "Remote"
                    "engineering", "tech_eng" -> "Engineering"
                    "design" -> "Design"
                    "data_ai", "data & ai" -> "Data & AI"
                    "marketing" -> "Marketing"
                    else -> action.chipId
                }
                onAction(JobSearchAction.CategorySelected(category))
            }
            is JobSearchAction.ClearFiltersClicked -> {
                _uiState.update { current ->
                    current.copy(
                        query = "",
                        selectedCategory = "All",
                        filteredJobs = filterJobs(current.allJobs, "", "All")
                    )
                }
            }
            is JobSearchAction.AiFilterClicked -> {
                _uiState.update { it.copy(snackbarMessage = "AI matching filters applied.") }
            }
            is JobSearchAction.FilterButtonClicked -> {
                _uiState.update { it.copy(snackbarMessage = "AI matching filters applied.") }
            }
            is JobSearchAction.SnackbarDismissed -> {
                _uiState.update { it.copy(snackbarMessage = null) }
            }
            is JobSearchAction.BookmarkToggled -> {
                val newStatus = DemoCareerRepository.toggleBookmark(action.jobId)
                _uiState.update { current ->
                    val updatedAll = current.allJobs.map { job ->
                        if (job.id == action.jobId) job.copy(isBookmarked = newStatus) else job
                    }
                    val updatedFiltered = current.filteredJobs.map { job ->
                        if (job.id == action.jobId) job.copy(isBookmarked = newStatus) else job
                    }
                    current.copy(
                        allJobs = updatedAll,
                        filteredJobs = updatedFiltered
                    )
                }
            }
            is JobSearchAction.NavTabSelected -> {
                _uiState.update { it.copy(selectedNavTab = action.tab) }
            }
            is JobSearchAction.JobClicked,
            is JobSearchAction.ApplyClicked,
            is JobSearchAction.NotificationsClicked,
            is JobSearchAction.ProfileClicked -> {
                // Handled in navigation layer
            }
        }
    }

    companion object {
        fun filterJobs(allJobs: List<JobMatch>, query: String, category: String): List<JobMatch> {
            val q = query.trim().lowercase()
            val cat = category.trim()
            return allJobs.filter { job ->
                val matchesQuery = q.isEmpty() ||
                    job.roleTitle.lowercase().contains(q) ||
                    job.companyName.lowercase().contains(q) ||
                    job.location.lowercase().contains(q) ||
                    job.perks.any { it.lowercase().contains(q) }

                val matchesCategory = cat == "All" ||
                    (cat == "Remote" && (job.employmentType.lowercase().contains("remote") || job.location.lowercase().contains("remote"))) ||
                    (cat == "Engineering" && (job.roleTitle.lowercase().contains("engineer") || job.roleTitle.lowercase().contains("swe"))) ||
                    (cat == "Design" && (job.roleTitle.lowercase().contains("ux") || job.roleTitle.lowercase().contains("design"))) ||
                    (cat == "Data & AI" && (job.roleTitle.lowercase().contains("ai") || job.roleTitle.lowercase().contains("cloud") || job.roleTitle.lowercase().contains("data"))) ||
                    (cat == "Marketing" && job.roleTitle.lowercase().contains("marketing"))

                matchesQuery && matchesCategory
            }
        }
    }
}
