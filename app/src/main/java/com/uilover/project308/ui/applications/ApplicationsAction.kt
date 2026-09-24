package com.uilover.project308.ui.applications

import com.uilover.project308.data.model.NavTab

/**
 * User actions and events for ApplicationsScreen per rules.md §18 and §25.
 */
sealed interface ApplicationsAction {
    data object BackClicked : ApplicationsAction
    data class FilterSelected(val filter: ApplicationFilter) : ApplicationsAction
    data class ApplicationClicked(val applicationId: String, val jobId: String) : ApplicationsAction
    data class AiInterviewPrepClicked(val applicationId: String, val roleTitle: String) : ApplicationsAction
    data class ViewJobDetailsClicked(val jobId: String) : ApplicationsAction
    data class WithdrawApplicationClicked(val applicationId: String) : ApplicationsAction
    data object StartMockInterviewClicked : ApplicationsAction
    data class NavTabSelected(val tab: NavTab) : ApplicationsAction
    data object DismissFeedback : ApplicationsAction
}
