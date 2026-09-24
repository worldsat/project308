package com.uilover.project308.ui.applications

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.uilover.project308.R
import com.uilover.project308.ui.theme.Primary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for ApplicationsScreen managing pipeline data and filters per rules.md §18 and §19.
 */
class ApplicationsViewModel : ViewModel() {

    private val allApplications = listOf(
        JobApplicationItem(
            id = "app_google",
            jobId = "google_staff_ux",
            companyName = "Google",
            companyLogoRes = R.drawable.google_official_logo,
            roleTitle = "Staff UX Architect",
            salaryRange = "$155,000 – $190,000 / yr",
            location = "Mountain View, CA • Remote-first",
            appliedDate = "Applied Sep 12",
            currentStage = ApplicationStage.INTERVIEWING,
            stageTitle = "System Architecture Round",
            stageSubtitle = "Virtual On-site with Staff Engineering Committee",
            nextActionDate = "Thursday, Sep 26 • 2:00 PM",
            aiMatchScore = 94,
            aiTip = "Practice modular design systems & Flutter multi-screen state management."
        ),
        JobApplicationItem(
            id = "app_amazon",
            jobId = "amazon_senior_swe",
            companyName = "Amazon",
            companyLogoRes = R.drawable.amazon_official_logo,
            roleTitle = "Senior Software Engineer",
            salaryRange = "$160,000 – $215,000 / yr",
            location = "Seattle, WA • Hybrid On-site",
            appliedDate = "Applied Sep 04",
            currentStage = ApplicationStage.OFFER_RECEIVED,
            stageTitle = "Formal Offer Extended",
            stageSubtitle = "$185,000 Base + $45,000 RSU / yr + Relocation",
            nextActionDate = "Decision deadline: Oct 02",
            aiMatchScore = 98,
            aiTip = "Top 8th percentile offer in Greater Seattle. AI negotiation buffer available."
        ),
        JobApplicationItem(
            id = "app_microsoft",
            jobId = "microsoft_senior_frontend",
            companyName = "Microsoft",
            companyLogoRes = R.drawable.microsoft_official_logo,
            roleTitle = "Senior Frontend Engineer",
            salaryRange = "$130,000 – $175,000 / yr",
            location = "Redmond, WA • Remote",
            appliedDate = "Applied Sep 15",
            currentStage = ApplicationStage.INTERVIEWING,
            stageTitle = "Technical Interview Round 2",
            stageSubtitle = "Copilot UX & Realtime Collaboration Architecture",
            nextActionDate = "Tomorrow, Sep 24 • 11:30 AM",
            aiMatchScore = 94,
            aiTip = "Review modern React 19 concurrent pipelines and Fluent UI styling."
        ),
        JobApplicationItem(
            id = "app_apple",
            jobId = "apple_ios_swe",
            companyName = "Apple",
            companyLogoRes = R.drawable.apple_official_logo,
            roleTitle = "iOS Software Engineer",
            salaryRange = "$145,000 – $190,000 / yr",
            location = "Cupertino, CA • Hybrid",
            appliedDate = "Applied Sep 18",
            currentStage = ApplicationStage.INTERVIEWING,
            stageTitle = "Live Coding & Algorithm Deep Dive",
            stageSubtitle = "Swift 6 Concurrency & ProMotion 120Hz Animation",
            nextActionDate = "Friday, Sep 27 • 10:00 AM",
            aiMatchScore = 91,
            aiTip = "Focus on Instruments memory profiling, CPU trace analysis, and thread safety."
        ),
        JobApplicationItem(
            id = "app_stripe",
            jobId = "stripe_product_designer",
            companyName = "Stripe",
            monogramText = "S",
            monogramColor = Primary,
            roleTitle = "Product Designer II",
            salaryRange = "$125,000 – $155,000 / yr",
            location = "San Francisco, CA • On-site",
            appliedDate = "Applied Sep 20",
            currentStage = ApplicationStage.SCREENING,
            stageTitle = "Portfolio Review & Screening",
            stageSubtitle = "Reviewed by Global Design Systems Lead",
            nextActionDate = "Feedback expected by Wednesday",
            aiMatchScore = 89,
            aiTip = "High resonance score on your checkout typography & micro-interactions."
        ),
        JobApplicationItem(
            id = "app_netflix",
            jobId = "netflix_senior_backend",
            companyName = "Netflix",
            monogramText = "N",
            monogramColor = Color(0xFFE50914),
            roleTitle = "Senior Platform Engineer",
            salaryRange = "$200,000 – $250,000 / yr",
            location = "Los Gatos, CA • Flexible",
            appliedDate = "Applied Aug 28",
            currentStage = ApplicationStage.ARCHIVED,
            stageTitle = "Position Filled",
            stageSubtitle = "Team opted for internal mobility candidate",
            nextActionDate = null,
            aiMatchScore = 93,
            aiTip = "Recruiter marked your profile as priority for upcoming Q4 openings."
        )
    )

    private val _uiState = MutableStateFlow(
        ApplicationsUiState(
            applications = allApplications
        )
    )
    val uiState: StateFlow<ApplicationsUiState> = _uiState.asStateFlow()

    fun onAction(action: ApplicationsAction) {
        when (action) {
            is ApplicationsAction.FilterSelected -> {
                val filtered = when (action.filter) {
                    ApplicationFilter.ALL -> allApplications
                    ApplicationFilter.INTERVIEWING -> allApplications.filter { it.currentStage == ApplicationStage.INTERVIEWING }
                    ApplicationFilter.UNDER_REVIEW -> allApplications.filter { it.currentStage == ApplicationStage.SCREENING || it.currentStage == ApplicationStage.APPLIED }
                    ApplicationFilter.OFFERS -> allApplications.filter { it.currentStage == ApplicationStage.OFFER_RECEIVED }
                    ApplicationFilter.ARCHIVED -> allApplications.filter { it.currentStage == ApplicationStage.ARCHIVED }
                }
                _uiState.update {
                    it.copy(
                        selectedFilter = action.filter,
                        applications = filtered
                    )
                }
            }
            is ApplicationsAction.DismissFeedback -> {
                _uiState.update { it.copy(feedbackMessage = null) }
            }
            is ApplicationsAction.WithdrawApplicationClicked -> {
                _uiState.update {
                    it.copy(feedbackMessage = "Withdrawal request submitted for this role")
                }
            }
            is ApplicationsAction.NavTabSelected -> {
                _uiState.update { it.copy(selectedNavTab = action.tab) }
            }
            else -> {
                // Handled in NavHost
            }
        }
    }
}
