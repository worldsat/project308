package com.uilover.project308.ui.applications

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

enum class ApplicationStage {
    APPLIED,        // Just submitted
    SCREENING,      // HR / Recruiter Screening
    INTERVIEWING,   // Technical / Onsite Round
    OFFER_RECEIVED, // Offer in hand
    ARCHIVED        // Rejected or closed
}

enum class ApplicationFilter(val label: String) {
    ALL("All"),
    INTERVIEWING("Interviewing"),
    UNDER_REVIEW("Reviewing"),
    OFFERS("Offers"),
    ARCHIVED("Archived")
}

data class JobApplicationItem(
    val id: String,
    val jobId: String,
    val companyName: String,
    @field:DrawableRes val companyLogoRes: Int? = null,
    val monogramText: String? = null,
    val monogramColor: Color? = null,
    val roleTitle: String,
    val salaryRange: String,
    val location: String,
    val appliedDate: String,
    val currentStage: ApplicationStage,
    val stageTitle: String,
    val stageSubtitle: String,
    val nextActionDate: String? = null,
    val aiMatchScore: Int = 94,
    val aiTip: String,
    val isVerified: Boolean = true
)
