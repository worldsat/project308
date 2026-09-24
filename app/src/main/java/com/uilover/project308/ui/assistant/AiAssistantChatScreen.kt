package com.uilover.project308.ui.assistant

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project308.R
import com.uilover.project308.data.model.AiChatMessage
import com.uilover.project308.data.model.MessageSender
import com.uilover.project308.ui.theme.OnPrimary
import com.uilover.project308.ui.theme.OnSurface
import com.uilover.project308.ui.theme.OnSurfaceVariant
import com.uilover.project308.ui.theme.Outline
import com.uilover.project308.ui.theme.Primary
import com.uilover.project308.ui.theme.PrimaryContainer
import com.uilover.project308.ui.theme.Project308Theme
import com.uilover.project308.ui.theme.Spacing
import com.uilover.project308.ui.theme.Surface
import com.uilover.project308.ui.theme.SurfaceContainerHigh
import com.uilover.project308.ui.theme.SurfaceContainerLow

/**
 * AiAssistantChatScreen for Sorce Career AI per design.md §2 and rules.md §16.
 * Faithfully matches the visual reference and interactive specifications.
 */
@Composable
fun AiAssistantChatScreen(
    state: AiAssistantUiState,
    onAction: (AiAssistantAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    LaunchedEffect(state.feedbackMessage) {
        state.feedbackMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            onAction(AiAssistantAction.DismissFeedback)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Surface,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AiAssistantTopAppBar(
                onBackClick = { onAction(AiAssistantAction.BackClicked) },
                onProfileClick = { onAction(AiAssistantAction.ProfileClicked) }
            )
        },
        bottomBar = {
            AiAssistantComposerDock(
                inputText = state.inputText,
                onInputChanged = { onAction(AiAssistantAction.InputChanged(it)) },
                onSendClick = { onAction(AiAssistantAction.SendMessageClicked) },
                onAttachmentClick = { onAction(AiAssistantAction.AttachmentClicked) },
                onMicClick = { onAction(AiAssistantAction.MicClicked) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Status & Topic Header Row
            AiAssistantStatusRow(
                statusTitle = state.statusTitle,
                vaultStatus = state.vaultStatus
            )

            // Chat Messages Stream
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    start = Spacing.md,
                    end = Spacing.md,
                    top = 4.dp,
                    bottom = Spacing.md
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.messages, key = { it.id }) { message ->
                    when (message.sender) {
                        MessageSender.USER -> {
                            UserMessageItem(message = message)
                        }
                        MessageSender.BOT -> {
                            BotMessageItem(
                                message = message,
                                onUpdateResumeClick = { onAction(AiAssistantAction.UpdateResumeClicked) },
                                onShowMoreTipsClick = { onAction(AiAssistantAction.ShowMoreTipsClicked) }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Top App Bar for AI Career Assistant per rules.md §11.1 and §16.
 */
@Composable
private fun AiAssistantTopAppBar(
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Surface,
        shadowElevation = 0.5.dp
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back",
                        tint = OnSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.sorce_ai_official_logo),
                    contentDescription = "Sorce App Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                )

                Text(
                    text = "Ai Career Assistant",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 18.sp
                    )
                )
            }

            // Trailing User Profile Avatar
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .border(1.5.dp, Primary.copy(alpha = 0.25f), CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .clickable(role = Role.Button, onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

/**
 * Status & Topic Header Row with pulsing online dot and private vault lock indicator.
 */
@Composable
private fun AiAssistantStatusRow(
    statusTitle: String,
    vaultStatus: String,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status Pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(SurfaceContainerHigh)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Primary.copy(alpha = pulseAlpha))
                )
                Text(
                    text = statusTitle,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.5.sp
                    )
                )
            }
        }

        // Vault Pill
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Vault",
                tint = OnSurfaceVariant,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = vaultStatus,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.5.sp
                )
            )
        }
    }
}

/**
 * User Message bubble aligned to the right with read receipt checkmark.
 */
@Composable
private fun UserMessageItem(
    message: AiChatMessage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 40.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 4.dp,
                bottomEnd = 18.dp,
                bottomStart = 18.dp
            ),
            color = Primary,
            shadowElevation = 1.dp
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = OnPrimary,
                    fontSize = 14.5.sp,
                    lineHeight = 21.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        // Timestamp & Read Receipt
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(end = 4.dp)
        ) {
            Text(
                text = message.timestamp,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    fontSize = 11.sp
                )
            )
            if (message.hasReadReceipt) {
                Icon(
                    imageVector = Icons.Filled.DoneAll,
                    contentDescription = "Read",
                    tint = Primary,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

/**
 * Bot Message item with AI sparkle avatar and structured content bubble.
 */
@Composable
private fun BotMessageItem(
    message: AiChatMessage,
    onUpdateResumeClick: () -> Unit,
    onShowMoreTipsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        // AI Sparkle Circular Avatar
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(PrimaryContainer)
                .shadow(1.dp, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.AutoAwesome,
                contentDescription = "AI Assistant",
                tint = Primary,
                modifier = Modifier.size(19.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            // Main Card Bubble
            Surface(
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 18.dp,
                    bottomEnd = 18.dp,
                    bottomStart = 18.dp
                ),
                color = Color.White,
                shadowElevation = 1.5.dp,
                border = BorderStroke(1.dp, Outline.copy(alpha = 0.35f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Regular text content
                    if (message.text.isNotEmpty()) {
                        Text(
                            text = message.text,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = OnSurface,
                                fontSize = 14.5.sp,
                                lineHeight = 22.sp
                            )
                        )
                    }

                    // Welcome prompt subtitle row
                    if (message.subText != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(17.dp)
                            )
                            Text(
                                text = message.subText,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Primary,
                                    fontSize = 13.5.sp
                                )
                            )
                        }
                    }

                    // Structured Checklist Header
                    if (message.checklistHeader != null || message.checklistSubheader != null) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            message.checklistHeader?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                            message.checklistSubheader?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = OnSurfaceVariant,
                                        fontSize = 13.5.sp,
                                        lineHeight = 19.sp
                                    )
                                )
                            }
                        }
                    }

                    // Structured Checklist Container
                    if (message.skillChecklist.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SurfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                message.skillChecklist.forEach { skill ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            tint = Primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = skill,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = OnSurface,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 13.5.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Prompt question
                    message.promptQuestion?.let { question ->
                        Text(
                            text = question,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = OnSurface,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        )
                    }

                    // Interactive Action Triggers
                    if (message.hasActionButtons) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Primary Pill Button: Update Resume with AI
                            Button(
                                onClick = onUpdateResumeClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Primary,
                                    contentColor = OnPrimary
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AutoAwesome,
                                        contentDescription = null,
                                        tint = OnPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "Update Resume with AI",
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 14.5.sp
                                        )
                                    )
                                }
                            }

                            // Secondary Pill Button: Show More Tips
                            Button(
                                onClick = onShowMoreTipsClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SurfaceContainerHigh,
                                    contentColor = Primary
                                )
                            ) {
                                Text(
                                    text = "Show More Tips",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = Primary,
                                        fontSize = 13.5.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Timestamp under bubble
            Text(
                text = message.timestamp,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    fontSize = 11.sp
                ),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

/**
 * Bottom Message Composer Dock per rules.md §16.3 and the reference design.
 */
@Composable
private fun AiAssistantComposerDock(
    inputText: String,
    onInputChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    onAttachmentClick: () -> Unit,
    onMicClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp,
        border = BorderStroke(1.dp, Outline.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = Spacing.md, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Attachment Trigger Button
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(SurfaceContainerHigh)
                    .clickable(role = Role.Button, onClick = onAttachmentClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Attachment",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Text Input Pill
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(50),
                color = SurfaceContainerLow,
                border = BorderStroke(1.dp, Outline.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (inputText.isEmpty()) {
                            Text(
                                text = "Type a message...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = OnSurfaceVariant.copy(alpha = 0.65f),
                                    fontSize = 14.sp
                                )
                            )
                        }

                        BasicTextField(
                            value = inputText,
                            onValueChange = onInputChanged,
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = OnSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            cursorBrush = SolidColor(Primary),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = { onSendClick() })
                        )
                    }

                    // Voice Input Trigger Button
                    IconButton(
                        onClick = onMicClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Mic,
                            contentDescription = "Voice Input",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Send CTA Button
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .shadow(elevation = 2.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .background(Primary)
                    .clickable(role = Role.Button, onClick = onSendClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send Message",
                    tint = OnPrimary,
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7)
@Composable
fun AiAssistantChatScreenPreview() {
    Project308Theme {
        AiAssistantChatScreen(
            state = AiAssistantUiState(),
            onAction = {}
        )
    }
}
