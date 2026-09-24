package com.uilover.project308.data.model

/**
 * Sender type for messages in the AI Career Assistant chat.
 */
enum class MessageSender {
    BOT,
    USER
}

/**
 * Chat message model representing bot greetings, user queries, and rich structured AI responses.
 * Follows design.md §2 and rules.md §16.
 */
data class AiChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: String,
    val subText: String? = null,
    val hasReadReceipt: Boolean = false,
    val checklistHeader: String? = null,
    val checklistSubheader: String? = null,
    val skillChecklist: List<String> = emptyList(),
    val promptQuestion: String? = null,
    val hasActionButtons: Boolean = false
)
