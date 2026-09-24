package com.uilover.project308.ui.assistant

import com.uilover.project308.data.model.AiChatMessage
import com.uilover.project308.data.model.MessageSender

/**
 * Immutable UI State for AiAssistantChatScreen per rules.md §16 and §19.
 */
data class AiAssistantUiState(
    val statusTitle: String = "Sorce Intelligence Online",
    val vaultStatus: String = "Private Career Vault",
    val inputText: String = "",
    val messages: List<AiChatMessage> = defaultMessages(),
    val feedbackMessage: String? = null
) {
    companion object {
        fun defaultMessages(): List<AiChatMessage> = listOf(
            AiChatMessage(
                id = "msg_welcome",
                sender = MessageSender.BOT,
                text = "Hi! I'm your AI career assistant. I can help you find the best job matches, answer your questions, and even improve your resume.",
                timestamp = "9:38 AM",
                subText = "How can I help you today?"
            ),
            AiChatMessage(
                id = "msg_user_q1",
                sender = MessageSender.USER,
                text = "What skills should I highlight for a Senior Software Engineer role at Amazon?",
                timestamp = "9:40 AM",
                hasReadReceipt = true
            ),
            AiChatMessage(
                id = "msg_bot_a1",
                sender = MessageSender.BOT,
                text = "",
                timestamp = "9:40 AM",
                checklistHeader = "Great question!",
                checklistSubheader = "For a Senior Software Engineer role at Amazon, you should highlight:",
                skillChecklist = listOf(
                    "System Design & Architecture",
                    "React / Node.js / TypeScript",
                    "AWS & Cloud Technologies",
                    "Problem Solving & Leadership",
                    "Team Collaboration"
                ),
                promptQuestion = "Would you like me to help you update your resume with these skills?",
                hasActionButtons = true
            )
        )
    }
}
