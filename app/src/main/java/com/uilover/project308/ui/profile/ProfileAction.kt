package com.uilover.project308.ui.profile

import com.uilover.project308.data.model.NavTab

/**
 * User actions and events for ProfileScreen per rules.md §18 and §25.
 */
sealed interface ProfileAction {
    data object BackClicked : ProfileAction
    data object EditProfileClicked : ProfileAction
    data object ShareProfileClicked : ProfileAction
    data object AddSkillClicked : ProfileAction
    data class SkillClicked(val skill: String) : ProfileAction
    data object SavedJobsClicked : ProfileAction
    data object ApplicationsClicked : ProfileAction
    data object InterviewsClicked : ProfileAction
    data object AiResumeOptimizationClicked : ProfileAction
    data object SettingsClicked : ProfileAction
    data object NotificationsClicked : ProfileAction
    data object PrivacySecurityClicked : ProfileAction
    data object SignOutClicked : ProfileAction
    data class NavTabSelected(val tab: NavTab) : ProfileAction
    data object DismissFeedback : ProfileAction
}
