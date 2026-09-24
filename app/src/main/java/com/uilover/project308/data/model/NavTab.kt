package com.uilover.project308.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The 5 grounded bottom navigation items visible across Sorce Career AI screens.
 */
enum class NavTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Outlined.Home),
    SEARCH("Search", Icons.Outlined.Search),
    SAVED("Saved", Icons.Outlined.BookmarkBorder),
    APPLICATIONS("Applications", Icons.Outlined.WorkOutline),
    PROFILE("Profile", Icons.Outlined.PersonOutline)
}
