package com.uilover.project308.ui.search

import androidx.lifecycle.ViewModel
import com.uilover.project308.R
import com.uilover.project308.data.repository.DemoCareerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for JobSearchScreen managing state and search interactions per rules.md §14, §18, and §19.
 */
class JobSearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        JobSearchUiState(
            jobs = getInitialGroundedJobs()
        )
    )
    val uiState: StateFlow<JobSearchUiState> = _uiState.asStateFlow()

    fun onAction(action: JobSearchAction) {
        when (action) {
            is JobSearchAction.QueryChanged -> {
                _uiState.update { it.copy(query = action.query) }
            }
            is JobSearchAction.ClearQueryClicked -> {
                _uiState.update { it.copy(query = "") }
            }
            is JobSearchAction.FilterChipToggled -> {
                _uiState.update { it.copy(selectedFilterId = action.chipId) }
            }
            is JobSearchAction.SortSelected -> {
                _uiState.update { it.copy(selectedSort = action.sortOption) }
            }
            is JobSearchAction.CycleSortClicked -> {
                _uiState.update { current ->
                    val options = current.sortOptions
                    val currentIndex = options.indexOf(current.selectedSort)
                    val nextIndex = if (currentIndex >= 0) (currentIndex + 1) % options.size else 0
                    current.copy(selectedSort = options[nextIndex])
                }
            }
            is JobSearchAction.BookmarkToggled -> {
                val newStatus = DemoCareerRepository.toggleBookmark(action.jobId)
                _uiState.update { current ->
                    val updatedJobs = current.jobs.map { job ->
                        if (job.id == action.jobId) {
                            job.copy(isBookmarked = newStatus)
                        } else {
                            job
                        }
                    }
                    current.copy(jobs = updatedJobs)
                }
            }
            is JobSearchAction.AutopilotToggled -> {
                _uiState.update { it.copy(isAutopilotEnabled = !it.isAutopilotEnabled) }
            }
            is JobSearchAction.NavTabSelected -> {
                _uiState.update { it.copy(selectedNavTab = action.tab) }
            }
            is JobSearchAction.FilterButtonClicked -> {
                // Grounded trigger - no ungrounded filter drawer per rules.md §14.2
            }
            is JobSearchAction.JobClicked -> {
                // Handled in navigation layer
            }
            is JobSearchAction.NotificationsClicked -> {
                // Notification bell feedback
            }
            is JobSearchAction.ProfileClicked -> {
                // Profile avatar feedback
            }
        }
    }

    companion object {
        fun getInitialGroundedJobs(): List<SearchJobItem> = listOf(
            SearchJobItem(
                id = "amazon_senior_swe",
                companyName = "Amazon",
                companyLogoRes = R.drawable.amazon_official_logo,
                isVerified = true,
                roleTitle = "Senior Software Engineer",
                salaryRange = "$160K - $215K",
                salaryUnit = "/ yr",
                postedTime = "3 hours ago",
                location = "Seattle, WA",
                workStyle = "Hybrid",
                tags = listOf("Full-time", "Distributed Systems"),
                highlightTag = "High Match",
                matchScore = 98,
                isBookmarked = DemoCareerRepository.isBookmarked("amazon_senior_swe")
            ),
            SearchJobItem(
                id = "google_staff_ux",
                companyName = "Google",
                companyLogoRes = R.drawable.google_official_logo,
                isVerified = true,
                roleTitle = "Staff UX Architect",
                salaryRange = "$155K - $190K",
                salaryUnit = "/ yr",
                postedTime = "1 day ago",
                location = "Mountain View, CA",
                workStyle = "Remote",
                isRemote = true,
                tags = listOf("Full-time", "Design Systems"),
                highlightTag = "Portfolio Aligned",
                matchScore = 94,
                isBookmarked = DemoCareerRepository.isBookmarked("google_staff_ux")
            ),
            SearchJobItem(
                id = "google_staff_cloud",
                companyName = "Google",
                companyLogoRes = R.drawable.google_official_logo,
                isVerified = true,
                roleTitle = "Staff Cloud Architect",
                salaryRange = "$160K - $210K",
                salaryUnit = "/ yr",
                postedTime = "2 days ago",
                location = "Mountain View, CA",
                workStyle = "Hybrid",
                tags = listOf("Full-time", "Staff Level"),
                highlightTag = "GCP",
                matchScore = 96,
                isBookmarked = DemoCareerRepository.isBookmarked("google_staff_cloud")
            ),
            SearchJobItem(
                id = "microsoft_senior_frontend",
                companyName = "Microsoft",
                companyLogoRes = R.drawable.microsoft_official_logo,
                isVerified = true,
                roleTitle = "Senior Frontend Engineer",
                salaryRange = "$130K - $175K",
                salaryUnit = "/ yr",
                postedTime = "5 hours ago",
                location = "Redmond, WA",
                workStyle = "Remote",
                isRemote = true,
                tags = listOf("Full-time", "React / TS"),
                highlightTag = "AI Recommended",
                matchScore = 94,
                isBookmarked = DemoCareerRepository.isBookmarked("microsoft_senior_frontend")
            ),
            SearchJobItem(
                id = "stripe_product_designer",
                companyName = "Stripe",
                isStripeMonogram = true,
                isVerified = true,
                roleTitle = "Product Designer II",
                salaryRange = "$125K - $155K",
                salaryUnit = "/ yr",
                postedTime = "Just now",
                isJustNow = true,
                location = "San Francisco, CA",
                workStyle = "On-site",
                tags = listOf("Full-time", "Design Systems"),
                highlightTag = "Figma",
                matchScore = 89,
                isBookmarked = DemoCareerRepository.isBookmarked("stripe_product_designer")
            ),
            SearchJobItem(
                id = "apple_ios_swe",
                companyName = "Apple",
                companyLogoRes = R.drawable.apple_official_logo,
                isVerified = true,
                roleTitle = "iOS Software Engineer",
                salaryRange = "$145K - $190K",
                salaryUnit = "/ yr",
                postedTime = "1 day ago",
                location = "Cupertino, CA",
                workStyle = "Hybrid",
                tags = listOf("Full-time", "SwiftUI"),
                highlightTag = "Native",
                matchScore = 91,
                isBookmarked = DemoCareerRepository.isBookmarked("apple_ios_swe")
            )
        )
    }
}
