package com.uilover.project308.data.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Single benefit / perk item displayed in the 4-column perks grid.
 */
data class JobPerk(
    val id: String,
    val title: String,
    val icon: ImageVector
)

/**
 * Key technology tag item with primary or secondary tint.
 */
data class JobTechnology(
    val name: String,
    val isPrimary: Boolean = true
)

/**
 * Full job profile data model for JobDetailScreen per rules.md §15.
 */
data class JobDetail(
    val id: String,
    val companyName: String,
    @field:DrawableRes val companyLogoRes: Int? = null,
    val isVerified: Boolean = true,
    val roleTitle: String,
    val hiringStatus: String = "Actively Hiring",
    val location: String,
    val workStyle: String,
    val compensation: String,
    val compensationSuffix: String = "/ year + equity",
    val perksTotalCount: String = "12 total",
    val perks: List<JobPerk>,
    val aiMatchScore: Int = 92,
    val aiMatchDescription: String,
    val roleOverview: String,
    val responsibilities: List<String>,
    val keyTechnologies: List<JobTechnology>,
    val squadTitle: String = "Meet the Seattle Squad",
    val squadDescription: String = "6 alumni from your network work here",
    val alumniCount: Int = 6,
    val isBookmarked: Boolean = false,
    val monogramText: String? = null,
    val monogramColor: Color? = null
)
