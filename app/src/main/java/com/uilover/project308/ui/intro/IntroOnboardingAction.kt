package com.uilover.project308.ui.intro

/**
 * User actions and events for IntroOnboardingScreen per rules.md §12 and §18.
 */
sealed interface IntroOnboardingAction {
    data object GetStartedClicked : IntroOnboardingAction
    data object SkipClicked : IntroOnboardingAction
    data object SignInClicked : IntroOnboardingAction
}
