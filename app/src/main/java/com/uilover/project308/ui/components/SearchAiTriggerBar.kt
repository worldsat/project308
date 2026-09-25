package com.uilover.project308.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer

/**
 * Search AI Trigger Bar matching Flutter's search_ai_trigger_bar.dart 1:1.
 */
@Composable
fun SearchAiTriggerBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onAiFilterClick: () -> Unit,
    onSearchClick: () -> Unit = {},
    readOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = CircleShape,
                spotColor = Color.Black.copy(alpha = 0.04f)
            ),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(1.dp, Outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = OnSurfaceVariant,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(role = Role.Button, onClick = onSearchClick)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = "Search by role, company, or tech stack...",
                        style = TextStyle(
                            color = OnSurfaceVariant.copy(alpha = 0.7f),
                            fontSize = 13.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (readOnly) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(role = Role.Button, onClick = onSearchClick)
                    ) {
                        Text(
                            text = query.ifEmpty { "Search by role, company, or tech stack..." },
                            style = TextStyle(
                                color = if (query.isEmpty()) OnSurfaceVariant.copy(alpha = 0.7f) else OnSurface,
                                fontSize = 13.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                } else {
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            color = OnSurface,
                            fontSize = 13.sp
                        ),
                        singleLine = true,
                        cursorBrush = SolidColor(Primary),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { onSearchClick() })
                    )
                }
            }

            // Trailing circular Tune button matching Flutter
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer.copy(alpha = 0.6f))
                    .clickable(role = Role.Button, onClick = onAiFilterClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "AI Filter",
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
