package com.uilover.project308.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AllInclusive
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.data.model.Company
import com.uilover.project308.ui.theme.AppShapes
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.OutlineVariant
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.SurfaceContainerLow
import com.uilover.project308.ui.theme.SurfaceVariant

/**
 * Single item card in Top Hiring Companies carousel per design.md §3.3 and rules.md §13.3.
 */
@Composable
fun HiringCompanyCard(
    company: Company,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(104.dp)
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.CardRegular,
                spotColor = Primary.copy(alpha = 0.05f)
            )
            .clickable(role = Role.Button, onClick = onClick),
        shape = AppShapes.CardRegular,
        colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
        border = BorderStroke(1.dp, OutlineVariant)
    ) {
        Column(
            modifier = Modifier
                .width(104.dp)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Container 48x48dp
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLow),
                contentAlignment = Alignment.Center
            ) {
                when {
                    company.logoRes != null -> {
                        Image(
                            painter = painterResource(id = company.logoRes),
                            contentDescription = company.name,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(2.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    company.monogramText == "Meta" -> {
                        Icon(
                            imageVector = Icons.Outlined.AllInclusive,
                            contentDescription = "Meta",
                            tint = Primary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    company.monogramText != null -> {
                        Text(
                            text = company.monogramText,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = company.monogramColor ?: OnSurface,
                                fontWeight = FontWeight.Black,
                                fontSize = 22.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = company.name,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface,
                    fontSize = 13.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "${company.openJobsCount} open jobs",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
