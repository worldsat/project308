package com.uilover.project308.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for ProfileScreen managing state and actions per rules.md §18 and §19.
 */
class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.DismissFeedback -> {
                _uiState.update { it.copy(feedbackMessage = null) }
            }
            is ProfileAction.EditProfileClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Edit profile mode coming soon") }
            }
            is ProfileAction.ShareProfileClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Profile link copied to clipboard") }
            }
            is ProfileAction.AddSkillClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Skill dialog opened") }
            }
            is ProfileAction.SkillClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Filtering roles for ${action.skill}") }
            }
            is ProfileAction.SavedJobsClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Viewing saved jobs (${_uiState.value.savedJobsCount})") }
            }
            is ProfileAction.ApplicationsClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Viewing active applications (${_uiState.value.applicationsCount})") }
            }
            is ProfileAction.InterviewsClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Viewing interview schedule (${_uiState.value.interviewsScheduled})") }
            }
            is ProfileAction.AiResumeOptimizationClicked -> {
                _uiState.update { it.copy(feedbackMessage = "AI Resume match score: ${_uiState.value.aiResumeScore}%") }
            }
            is ProfileAction.SettingsClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Settings opened") }
            }
            is ProfileAction.NotificationsClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Notification preferences opened") }
            }
            is ProfileAction.PrivacySecurityClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Security settings active") }
            }
            is ProfileAction.SignOutClicked -> {
                _uiState.update { it.copy(feedbackMessage = "Signing out...") }
            }
            is ProfileAction.NavTabSelected -> {
                _uiState.update { it.copy(selectedNavTab = action.tab) }
            }
            is ProfileAction.BackClicked -> {
                // Handled in navigation host
            }
        }
    }
}
