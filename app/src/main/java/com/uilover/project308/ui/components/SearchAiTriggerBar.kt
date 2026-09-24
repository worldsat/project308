package com.uilover.project308.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.ui.theme.AppShapes
import com.uilover.project308.ui.theme.OnPrimaryContainer
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.PillAiBg
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer
import com.uilover.project308.ui.theme.Spacing
import com.uilover.project308.ui.theme.SurfaceVariant

/**
 * Search input bar with AI Filter trigger pill per design.md §3 and rules.md §13.1.
 */
@Composable
fun SearchAiTriggerBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onAiFilterClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.ButtonPill,
                spotColor = Primary.copy(alpha = 0.08f)
            ),
        shape = AppShapes.ButtonPill,
        color = SurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, Outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.sm, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = OnSurfaceVariant,
                modifier = Modifier
                    .size(22.dp)
                    .clickable(role = Role.Button, onClick = onSearchClick)
            )

            Spacer(modifier = Modifier.width(Spacing.xs))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = Spacing.xs),
                contentAlignment = Alignment.CenterStart
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = "Search job, company, role or stack...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = OnSurfaceVariant,
                            fontSize = 13.sp
                        ),
                        maxLines = 1
                    )
                }

                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = OnSurface,
                        fontSize = 13.sp
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(Primary),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearchClick() })
                )
            }

            // AI Filter Trigger Pill
            Row(
                modifier = Modifier
                    .clip(AppShapes.ButtonPill)
                    .background(PrimaryContainer)
                    .clickable(role = Role.Button, onClick = onAiFilterClick)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = "AI Filter",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnPrimaryContainer,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
