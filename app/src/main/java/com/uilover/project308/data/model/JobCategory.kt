package com.uilover.project308.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Job Category data model for popular job categories grid.
 */
data class JobCategory(
    val id: String,
    val title: String,
    val openRolesCount: String,
    val icon: ImageVector,
    val containerColor: Color,
    val iconTint: Color
)
