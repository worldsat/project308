package com.uilover.project308.ui.intro

/**
 * Immutable UI State for IntroOnboardingScreen per rules.md §18 and §19.
 */
data class IntroOnboardingUiState(
    val activeStep: Int = 0,
    val totalSteps: Int = 3
)
