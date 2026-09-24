package com.uilover.project308.ui.saved

import androidx.lifecycle.ViewModel
import com.uilover.project308.R
import com.uilover.project308.data.model.JobMatch
import com.uilover.project308.data.repository.DemoCareerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for SavedJobsScreen managing bookmarks, filtering, and resonance calculations.
 */
class SavedJobsViewModel : ViewModel() {

    private val allJobs = listOf(
        JobMatch(
            id = "amazon_senior_swe",
            companyName = "Amazon",
            companyLogoRes = R.drawable.amazon_official_logo,
            isVerified = true,
            roleTitle = "Senior Software Engineer",
            salaryRange = "$160K – $215K / yr",
            location = "Seattle, WA • Hybrid",
            employmentType = "Full-time",
            perks = listOf("Health Insurance", "401(k) Match", "AWS Cloud"),
            matchScore = 98,
            matchCaption = "Strong skill resonance",
            isBookmarked = true
        ),
        JobMatch(
            id = "google_staff_ux",
            companyName = "Google",
            companyLogoRes = R.drawable.google_official_logo,
            isVerified = true,
            roleTitle = "Staff UX Architect",
            salaryRange = "$155K – $190K / yr",
            location = "Mountain View, CA • Remote",
            employmentType = "Remote-first",
            perks = listOf("Design Systems", "Flutter UI", "Equity Included"),
            matchScore = 94,
            matchCaption = "Portfolio aligned",
            isBookmarked = true
        ),
        JobMatch(
            id = "microsoft_senior_frontend",
            companyName = "Microsoft",
            companyLogoRes = R.drawable.microsoft_official_logo,
            isVerified = true,
            roleTitle = "Senior Frontend Engineer",
            salaryRange = "$130K – $175K / yr",
            location = "Redmond, WA • Remote",
            employmentType = "Remote",
            perks = listOf("React 19", "TypeScript", "Copilot AI"),
            matchScore = 94,
            matchCaption = "AI Copilot Integration",
            isBookmarked = true
        ),
        JobMatch(
            id = "apple_ios_swe",
            companyName = "Apple",
            companyLogoRes = R.drawable.apple_official_logo,
            isVerified = true,
            roleTitle = "iOS Software Engineer",
            salaryRange = "$145K – $190K / yr",
            location = "Cupertino, CA • Hybrid",
            employmentType = "Full-time",
            perks = listOf("Swift 6", "ProMotion 120Hz", "Apple Silicon"),
            matchScore = 91,
            matchCaption = "Native mobile performance",
            isBookmarked = true
        ),
        JobMatch(
            id = "google_staff_cloud",
            companyName = "Google",
            companyLogoRes = R.drawable.google_official_logo,
            isVerified = true,
            roleTitle = "Staff Cloud Architect",
            salaryRange = "$160K – $210K / yr",
            location = "Mountain View, CA • Hybrid",
            employmentType = "Full-time",
            perks = listOf("GCP Cloud", "Kubernetes / GKE", "Zero-Trust"),
            matchScore = 96,
            matchCaption = "Enterprise cloud scale",
            isBookmarked = true
        ),
        JobMatch(
            id = "stripe_product_designer",
            companyName = "Stripe",
            companyLogoRes = R.drawable.sorce_ai_official_logo,
            isVerified = true,
            roleTitle = "Product Designer II",
            salaryRange = "$125K – $155K / yr",
            location = "San Francisco, CA • On-site",
            employmentType = "Full-time",
            perks = listOf("FinTech UX", "Design Systems", "Prototyping"),
            matchScore = 89,
            matchCaption = "Checkout experience lead",
            isBookmarked = true
        )
    )

    private val _uiState = MutableStateFlow(
        SavedJobsUiState(
            savedJobs = allJobs,
            totalSavedCount = allJobs.size,
            averageMatchScore = 94,
            urgentCount = 3
        )
    )
    val uiState: StateFlow<SavedJobsUiState> = _uiState.asStateFlow()

    init {
        allJobs.forEach { DemoCareerRepository.setBookmarked(it.id, true) }
    }

    fun onAction(action: SavedJobsAction) {
        when (action) {
            is SavedJobsAction.FilterSelected -> {
                val filtered = applyFilter(allJobs.filter { it.isBookmarked }, action.filter)
                _uiState.update {
                    it.copy(
                        selectedFilter = action.filter,
                        savedJobs = filtered
                    )
                }
            }
            is SavedJobsAction.BookmarkToggled -> {
                val isNowBookmarked = DemoCareerRepository.toggleBookmark(action.jobId)
                val updatedAll = allJobs.map {
                    if (it.id == action.jobId) it.copy(isBookmarked = isNowBookmarked) else it
                }
                val activeSaved = updatedAll.filter { it.isBookmarked }
                val filtered = applyFilter(activeSaved, _uiState.value.selectedFilter)
                val avgScore = if (activeSaved.isNotEmpty()) {
                    activeSaved.map { it.matchScore }.average().toInt()
                } else 0

                _uiState.update {
                    it.copy(
                        savedJobs = filtered,
                        totalSavedCount = activeSaved.size,
                        averageMatchScore = avgScore,
                        feedbackMessage = if (isNowBookmarked) "Role bookmarked" else "Role removed from Saved"
                    )
                }
            }
            is SavedJobsAction.DismissFeedback -> {
                _uiState.update { it.copy(feedbackMessage = null) }
            }
            is SavedJobsAction.NavTabSelected -> {
                _uiState.update { it.copy(selectedNavTab = action.tab) }
            }
            else -> {
                // Handled in NavHost
            }
        }
    }

    private fun applyFilter(jobs: List<JobMatch>, filter: SavedJobsFilter): List<JobMatch> {
        return when (filter) {
            SavedJobsFilter.ALL -> jobs
            SavedJobsFilter.TOP_MATCH -> jobs.filter { it.matchScore >= 94 }
            SavedJobsFilter.REMOTE -> jobs.filter {
                it.location.contains("Remote", ignoreCase = true) || it.location.contains("Hybrid", ignoreCase = true)
            }
            SavedJobsFilter.ENGINEERING -> jobs.filter {
                it.roleTitle.contains("Engineer", ignoreCase = true) || it.roleTitle.contains("Architect", ignoreCase = true)
            }
            SavedJobsFilter.DESIGN -> jobs.filter {
                it.roleTitle.contains("UX", ignoreCase = true) || it.roleTitle.contains("Designer", ignoreCase = true)
            }
        }
    }
}
