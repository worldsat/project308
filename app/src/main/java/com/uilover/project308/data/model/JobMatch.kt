package com.uilover.project308.data.model

import androidx.annotation.DrawableRes

/**
 * Job match item data model for recent high matches stream.
 */
data class JobMatch(
    val id: String,
    val companyName: String,
    @field:DrawableRes val companyLogoRes: Int,
    val isVerified: Boolean = true,
    val roleTitle: String,
    val salaryRange: String,
    val location: String,
    val employmentType: String,
    val perks: List<String>,
    val matchScore: Int,
    val matchCaption: String,
    val isBookmarked: Boolean = false
)
