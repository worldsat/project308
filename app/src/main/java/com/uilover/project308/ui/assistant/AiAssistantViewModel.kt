package com.uilover.project308.ui.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uilover.project308.data.model.AiChatMessage
import com.uilover.project308.data.model.MessageSender
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for AiAssistantChatScreen per rules.md §16 and §18.
 * Manages deterministic conversation flow, user input draft, and follow-up actions.
 */
class AiAssistantViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AiAssistantUiState())
    val uiState: StateFlow<AiAssistantUiState> = _uiState.asStateFlow()

    fun onAction(action: AiAssistantAction) {
        when (action) {
            is AiAssistantAction.InputChanged -> {
                _uiState.update { it.copy(inputText = action.text) }
            }
            is AiAssistantAction.SendMessageClicked -> {
                val trimmed = _uiState.value.inputText.trim()
                if (trimmed.isEmpty()) return

                val userMessage = AiChatMessage(
                    id = "user_${System.currentTimeMillis()}",
                    sender = MessageSender.USER,
                    text = trimmed,
                    timestamp = "9:41 AM",
                    hasReadReceipt = true
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        inputText = "",
                        messages = currentState.messages + userMessage
                    )
                }

                // Deterministic local assistant response per rules.md §16.3
                viewModelScope.launch {
                    delay(800L)
                    val botReply = AiChatMessage(
                        id = "bot_${System.currentTimeMillis()}",
                        sender = MessageSender.BOT,
                        text = "I've analyzed your profile against Amazon's bar raiser criteria. Emphasize distributed architecture scale and deliver results under tight constraints.",
                        timestamp = "9:41 AM"
                    )
                    _uiState.update { it.copy(messages = it.messages + botReply) }
                }
            }
            is AiAssistantAction.UpdateResumeClicked -> {
                viewModelScope.launch {
                    val updateNotice = AiChatMessage(
                        id = "bot_resume_${System.currentTimeMillis()}",
                        sender = MessageSender.BOT,
                        text = "Resume updated with highlighted Amazon AWS & System Design keywords. Your profile resonance is now 96%!",
                        timestamp = "9:42 AM"
                    )
                    _uiState.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + updateNotice,
                            feedbackMessage = "Resume skills updated successfully"
                        )
                    }
                }
            }
            is AiAssistantAction.ShowMoreTipsClicked -> {
                viewModelScope.launch {
                    val tipsMessage = AiChatMessage(
                        id = "bot_tips_${System.currentTimeMillis()}",
                        sender = MessageSender.BOT,
                        text = "",
                        timestamp = "9:42 AM",
                        checklistHeader = "Amazon Interview Tips:",
                        checklistSubheader = "Key areas to prepare for technical & leadership loops:",
                        skillChecklist = listOf(
                            "Customer Obsession STAR Stories",
                            "Low-Level Object Oriented Design",
                            "Data Partitioning & Caching Strategies",
                            "Trade-off Analysis & Failure Recovery"
                        )
                    )
                    _uiState.update { it.copy(messages = it.messages + tipsMessage) }
                }
            }
            is AiAssistantAction.SuggestionPromptClicked -> {
                _uiState.update { it.copy(inputText = action.prompt) }
            }
            is AiAssistantAction.AttachmentClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Attachment selector opened") }
            }
            is AiAssistantAction.MicClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Voice dictation active") }
            }
            is AiAssistantAction.DismissFeedback -> {
                _uiState.update { it.copy(feedbackMessage = null) }
            }
            is AiAssistantAction.BackClicked,
            is AiAssistantAction.ProfileClicked -> {
                // Handled at navigation / route level
            }
        }
    }
}
