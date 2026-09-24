package com.uilover.project308.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.R
import com.uilover.project308.ui.components.SorceBottomNavBar
import com.uilover.project308.ui.theme.AppShapes
import com.uilover.project308.ui.theme.OnPrimaryContainer
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.OutlineVariant
import com.uilover.project308.ui.theme.PillAiBg
import com.uilover.project308.ui.theme.PillAiText
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer
import com.uilover.project308.ui.theme.Project308Theme
import com.uilover.project308.ui.theme.Secondary
import com.uilover.project308.ui.theme.SecondaryContainer
import com.uilover.project308.ui.theme.Spacing
import com.uilover.project308.ui.theme.Surface
import com.uilover.project308.ui.theme.SurfaceContainerHigh

/**
 * ProfileScreen for Sorce Career AI per design.md §2 and rules.md §18.
 * Displays user identity, AI Career Readiness, skills breakdown, career preferences,
 * and account options.
 */
@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.feedbackMessage) {
        state.feedbackMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            onAction(ProfileAction.DismissFeedback)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Surface,
        topBar = {
            ProfileTopAppBar(
                onBackClick = { onAction(ProfileAction.BackClicked) },
                onShareClick = { onAction(ProfileAction.ShareProfileClicked) },
                onSettingsClick = { onAction(ProfileAction.SettingsClicked) }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                SorceBottomNavBar(
                    selectedTab = state.selectedNavTab,
                    onTabSelected = { onAction(ProfileAction.NavTabSelected(it)) }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                horizontal = Spacing.md,
                vertical = Spacing.md
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Profile Header Identity Card
            item {
                ProfileHeaderCard(
                    userName = state.userName,
                    userHeadline = state.userHeadline,
                    location = state.location,
                    email = state.email,
                    onEditClick = { onAction(ProfileAction.EditProfileClicked) },
                    onShareClick = { onAction(ProfileAction.ShareProfileClicked) }
                )
            }

            // 2. AI Career Resonance & Metrics Card
            item {
                AiCareerReadinessCard(
                    profileMatchScore = state.profileMatchScore,
                    applicationsCount = state.applicationsCount,
                    interviewsScheduled = state.interviewsScheduled,
                    aiResumeScore = state.aiResumeScore,
                    onOptimizationClick = { onAction(ProfileAction.AiResumeOptimizationClicked) }
                )
            }

            // 3. Verified Skills & Tech Stack Card
            item {
                VerifiedSkillsCard(
                    skills = state.skills,
                    onAddSkillClick = { onAction(ProfileAction.AddSkillClicked) },
                    onSkillClick = { onAction(ProfileAction.SkillClicked(it)) }
                )
            }

            // 4. Target Career Preferences Card
            item {
                CareerPreferencesCard(
                    targetRole = state.targetRole,
                    targetSalaryRange = state.targetSalaryRange,
                    workStylePreference = state.workStylePreference
                )
            }

            // 5. Account Pipeline & Management Menu
            item {
                AccountMenuSection(
                    savedJobsCount = state.savedJobsCount,
                    applicationsCount = state.applicationsCount,
                    interviewsCount = state.interviewsScheduled,
                    onSavedJobsClick = { onAction(ProfileAction.SavedJobsClicked) },
                    onApplicationsClick = { onAction(ProfileAction.ApplicationsClicked) },
                    onInterviewsClick = { onAction(ProfileAction.InterviewsClicked) },
                    onAiCoachSettingsClick = { onAction(ProfileAction.SettingsClicked) },
                    onNotificationsClick = { onAction(ProfileAction.NotificationsClicked) },
                    onPrivacySecurityClick = { onAction(ProfileAction.PrivacySecurityClicked) },
                    onSignOutClick = { onAction(ProfileAction.SignOutClicked) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

/**
 * Custom Top App Bar for Profile Screen.
 */
@Composable
private fun ProfileTopAppBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate Back",
                        tint = OnSurface
                    )
                }

                Text(
                    text = "My Profile",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 18.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(PillAiBg)
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "AI Verified",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PillAiText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onShareClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Share Profile",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Account Settings",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Main User Identity Card displaying Avatar, Name, Title, and Action Buttons.
 */
@Composable
private fun ProfileHeaderCard(
    userName: String,
    userHeadline: String,
    location: String,
    email: String,
    onEditClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.CardRegular,
                spotColor = Color.Black.copy(alpha = 0.05f)
            ),
        shape = AppShapes.CardRegular,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Avatar with verified badge
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .border(3.dp, Primary.copy(alpha = 0.25f), CircleShape),
                    contentScale = ContentScale.Crop
                )

                // Verified Badge Icon
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Verified Profile",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // User Name
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = OnSurface,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Professional Headline
            Text(
                text = userHeadline,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = OnSurfaceVariant,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Location & Email Badges Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = location,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = email,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // CTA Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    shape = AppShapes.Button,
                    border = BorderStroke(1.dp, Primary),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Edit Profile",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    )
                }

                OutlinedButton(
                    onClick = onShareClick,
                    modifier = Modifier.height(42.dp),
                    shape = AppShapes.Button,
                    border = BorderStroke(1.dp, Outline),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = OnSurfaceVariant
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Share Profile",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * AI Career Readiness Card with key metrics and ATS resume status.
 */
@Composable
private fun AiCareerReadinessCard(
    profileMatchScore: Int,
    applicationsCount: Int,
    interviewsScheduled: Int,
    aiResumeScore: Int,
    onOptimizationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.CardRegular,
                spotColor = Primary.copy(alpha = 0.15f)
            ),
        shape = AppShapes.CardRegular,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F8FF)),
        border = BorderStroke(1.dp, Primary.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "AI Career Readiness",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            fontSize = 14.5.sp
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SecondaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "$profileMatchScore% Complete",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF006644),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3-Metric Horizontal Tiles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricTile(
                    value = "$profileMatchScore%",
                    label = "AI Resonance",
                    valueColor = Secondary,
                    modifier = Modifier.weight(1f)
                )
                MetricTile(
                    value = applicationsCount.toString(),
                    label = "Applied",
                    valueColor = Primary,
                    modifier = Modifier.weight(1f)
                )
                MetricTile(
                    value = interviewsScheduled.toString(),
                    label = "Interviews",
                    valueColor = Color(0xFF8B5CF6),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // AI Resume Banner Strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(1.dp, OutlineVariant, RoundedCornerShape(12.dp))
                    .clickable(role = Role.Button, onClick = onOptimizationClick)
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "ATS Resume Match: $aiResumeScore%",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface,
                                    fontSize = 12.5.sp
                                )
                            )
                            Text(
                                text = "Optimized for Senior Tech Roles",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                        contentDescription = "View Resume Details",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}

/**
 * Metric tile container for AI metrics.
 */
@Composable
private fun MetricTile(
    value: String,
    label: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, OutlineVariant, RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = valueColor,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.5.sp
                )
            )
        }
    }
}

/**
 * Verified Skills & Tech Stack Section with interactive chips.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VerifiedSkillsCard(
    skills: List<String>,
    onAddSkillClick: () -> Unit,
    onSkillClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.CardRegular,
                spotColor = Color.Black.copy(alpha = 0.05f)
            ),
        shape = AppShapes.CardRegular,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Verified Skills & Stack",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 15.sp
                    )
                )

                TextButton(
                    onClick = onAddSkillClick,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "Add",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                skills.forEachIndexed { index, skill ->
                    val isFeatured = index < 3
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isFeatured) PrimaryContainer.copy(alpha = 0.6f) else SurfaceContainerHigh
                            )
                            .border(
                                1.dp,
                                if (isFeatured) Primary.copy(alpha = 0.3f) else OutlineVariant,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable(role = Role.Button) { onSkillClick(skill) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = skill,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = if (isFeatured) OnPrimaryContainer else OnSurface,
                                fontWeight = if (isFeatured) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Career Preferences Card displaying target salary, role, and work style.
 */
@Composable
private fun CareerPreferencesCard(
    targetRole: String,
    targetSalaryRange: String,
    workStylePreference: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.CardRegular,
                spotColor = Color.Black.copy(alpha = 0.05f)
            ),
        shape = AppShapes.CardRegular,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "Target Preferences",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    fontSize = 15.sp
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            PreferenceItemRow(
                icon = Icons.Outlined.WorkOutline,
                title = "Desired Role",
                value = targetRole
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = OutlineVariant,
                thickness = 1.dp
            )

            PreferenceItemRow(
                icon = Icons.Outlined.Payments,
                title = "Expected Compensation",
                value = targetSalaryRange
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = OutlineVariant,
                thickness = 1.dp
            )

            PreferenceItemRow(
                icon = Icons.Outlined.LocationOn,
                title = "Work Environment",
                value = workStylePreference
            )
        }
    }
}

@Composable
private fun PreferenceItemRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OnSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = OnSurfaceVariant,
                    fontSize = 13.sp
                )
            )
        }

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = OnSurface,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        )
    }
}

/**
 * Account Menu Section for pipeline, saved jobs, alerts, and settings.
 */
@Composable
private fun AccountMenuSection(
    savedJobsCount: Int,
    applicationsCount: Int,
    interviewsCount: Int,
    onSavedJobsClick: () -> Unit,
    onApplicationsClick: () -> Unit,
    onInterviewsClick: () -> Unit,
    onAiCoachSettingsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onPrivacySecurityClick: () -> Unit,
    onSignOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = AppShapes.CardRegular,
                spotColor = Color.Black.copy(alpha = 0.05f)
            ),
        shape = AppShapes.CardRegular,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            MenuItemRow(
                icon = Icons.Outlined.BookmarkBorder,
                title = "Saved Jobs",
                badgeText = "$savedJobsCount saved",
                onClick = onSavedJobsClick
            )

            HorizontalDivider(color = OutlineVariant, thickness = 1.dp)

            MenuItemRow(
                icon = Icons.Outlined.WorkOutline,
                title = "Application Pipeline",
                badgeText = "$applicationsCount active",
                onClick = onApplicationsClick
            )

            HorizontalDivider(color = OutlineVariant, thickness = 1.dp)

            MenuItemRow(
                icon = Icons.Outlined.CalendarToday,
                title = "Interview Schedule",
                badgeText = "$interviewsCount upcoming",
                onClick = onInterviewsClick
            )

            HorizontalDivider(color = OutlineVariant, thickness = 1.dp)

            MenuItemRow(
                icon = Icons.Outlined.SmartToy,
                title = "AI Career Coach Settings",
                onClick = onAiCoachSettingsClick
            )

            HorizontalDivider(color = OutlineVariant, thickness = 1.dp)

            MenuItemRow(
                icon = Icons.Outlined.Notifications,
                title = "Notifications & Alerts",
                onClick = onNotificationsClick
            )

            HorizontalDivider(color = OutlineVariant, thickness = 1.dp)

            MenuItemRow(
                icon = Icons.Outlined.Lock,
                title = "Privacy & Security",
                onClick = onPrivacySecurityClick
            )

            HorizontalDivider(color = OutlineVariant, thickness = 1.dp)

            MenuItemRow(
                icon = Icons.AutoMirrored.Outlined.Logout,
                title = "Sign Out",
                titleColor = Color(0xFFDC2626),
                iconColor = Color(0xFFDC2626),
                showArrow = false,
                onClick = onSignOutClick
            )
        }
    }
}

@Composable
private fun MenuItemRow(
    icon: ImageVector,
    title: String,
    badgeText: String? = null,
    titleColor: Color = OnSurface,
    iconColor: Color = OnSurfaceVariant,
    showArrow: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = titleColor,
                    fontSize = 13.5.sp
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (badgeText != null) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            if (showArrow) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                    contentDescription = null,
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7)
@Composable
fun ProfileScreenPreview() {
    Project308Theme {
        ProfileScreen(
            state = ProfileUiState(),
            onAction = {}
        )
    }
}
