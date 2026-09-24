package com.uilover.project308.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Official Sorce Career AI Shape tokens as specified in design.md §1.3.
 */
object AppShapes {
    val TagChip = RoundedCornerShape(8.dp)          // Filter tags, feature pills
    val CardRegular = RoundedCornerShape(16.dp)     // Job cards, category tiles, company cards
    val CardHero = RoundedCornerShape(24.dp)        // Top banner cards with internal artwork
    val Button = RoundedCornerShape(12.dp)          // Primary & secondary CTAs
    val ButtonPill = RoundedCornerShape(50)         // Circular badges, search bar, icon containers
    val BottomSheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
}
