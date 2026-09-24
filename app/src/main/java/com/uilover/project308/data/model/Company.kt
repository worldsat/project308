package com.uilover.project308.data.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

/**
 * Hiring company data model for top hiring companies carousel.
 */
data class Company(
    val id: String,
    val name: String,
    val openJobsCount: Int,
    @field:DrawableRes val logoRes: Int? = null,
    val monogramText: String? = null,
    val monogramColor: Color? = null
)
