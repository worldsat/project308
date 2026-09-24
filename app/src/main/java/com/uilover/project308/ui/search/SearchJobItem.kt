package com.uilover.project308.ui.search

import androidx.annotation.DrawableRes

/**
 * Data model for search job cards per rules.md §14.5.
 */
data class SearchJobItem(
    val id: String,
    val companyName: String,
    @field:DrawableRes val companyLogoRes: Int? = null,
    val isStripeMonogram: Boolean = false,
    val isVerified: Boolean = true,
    val roleTitle: String,
    val salaryRange: String,
    val salaryUnit: String = "/ yr",
    val postedTime: String,
    val isJustNow: Boolean = false,
    val location: String,
    val workStyle: String,
    val isRemote: Boolean = false,
    val tags: List<String> = emptyList(),
    val highlightTag: String? = null,
    val matchScore: Int,
    val isBookmarked: Boolean = false
)
