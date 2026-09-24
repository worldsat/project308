package com.uilover.project308.ui.assistant

/**
 * User actions and events for AiAssistantChatScreen per rules.md §16, §18, and §25.
 */
sealed interface AiAssistantAction {
    data object BackClicked : AiAssistantAction
    data object ProfileClicked : AiAssistantAction
    data class InputChanged(val text: String) : AiAssistantAction
    data object SendMessageClicked : AiAssistantAction
    data object AttachmentClicked : AiAssistantAction
    data object MicClicked : AiAssistantAction
    data object UpdateResumeClicked : AiAssistantAction
    data object ShowMoreTipsClicked : AiAssistantAction
    data class SuggestionPromptClicked(val prompt: String) : AiAssistantAction
    data object DismissFeedback : AiAssistantAction
}
