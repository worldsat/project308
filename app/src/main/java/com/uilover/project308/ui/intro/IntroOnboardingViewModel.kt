package com.uilover.project308.ui.intro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for IntroOnboardingScreen managing state and user actions per rules.md §18 and §19.
 */
class IntroOnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(IntroOnboardingUiState())
    val uiState: StateFlow<IntroOnboardingUiState> = _uiState.asStateFlow()

    fun onAction(action: IntroOnboardingAction) {
        when (action) {
            is IntroOnboardingAction.GetStartedClicked -> {
                // Handled in navigation layer
            }
            is IntroOnboardingAction.SkipClicked -> {
                // Handled in navigation layer
            }
            is IntroOnboardingAction.SignInClicked -> {
                // Sign in callback - no dedicated sign-in screen per rules.md §4
            }
        }
    }
}
