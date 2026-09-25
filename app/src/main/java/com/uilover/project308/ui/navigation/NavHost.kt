package com.uilover.project308.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uilover.project308.data.model.NavTab
import com.uilover.project308.ui.assistant.AiAssistantAction
import com.uilover.project308.ui.assistant.AiAssistantChatScreen
import com.uilover.project308.ui.assistant.AiAssistantViewModel
import com.uilover.project308.ui.applications.ApplicationsAction
import com.uilover.project308.ui.applications.ApplicationsScreen
import com.uilover.project308.ui.applications.ApplicationsViewModel
import com.uilover.project308.ui.saved.SavedJobsAction
import com.uilover.project308.ui.saved.SavedJobsScreen
import com.uilover.project308.ui.saved.SavedJobsViewModel
import com.uilover.project308.ui.detail.JobDetailAction
import com.uilover.project308.ui.detail.JobDetailScreen
import com.uilover.project308.ui.detail.JobDetailViewModel
import com.uilover.project308.ui.home.HomeAction
import com.uilover.project308.ui.home.HomeScreen
import com.uilover.project308.ui.home.HomeViewModel
import com.uilover.project308.ui.intro.IntroOnboardingAction
import com.uilover.project308.ui.intro.IntroOnboardingScreen
import com.uilover.project308.ui.intro.IntroOnboardingViewModel
import com.uilover.project308.ui.profile.ProfileAction
import com.uilover.project308.ui.profile.ProfileScreen
import com.uilover.project308.ui.profile.ProfileViewModel
import com.uilover.project308.ui.search.JobSearchAction
import com.uilover.project308.ui.search.JobSearchScreen
import com.uilover.project308.ui.search.JobSearchViewModel

/**
 * Centralized Route definitions for Sorce Career AI per design.md §2 and rules.md §3, §20.
 */
sealed interface AppRoute {
    data object Onboarding : AppRoute {
        const val ROUTE = "onboarding"
    }

    data object Home : AppRoute {
        const val ROUTE = "home"
    }

    data object Search : AppRoute {
        const val ROUTE = "search"
    }

    data object AiAssistant : AppRoute {
        const val ROUTE = "ai_assistant"
    }

    data object Profile : AppRoute {
        const val ROUTE = "profile"
    }

    data object Applications : AppRoute {
        const val ROUTE = "applications"
    }

    data object Saved : AppRoute {
        const val ROUTE = "saved"
    }

    data class JobDetail(val jobId: String = "amazon_senior_swe") : AppRoute {
        companion object {
            const val ARG_JOB_ID = "jobId"
            const val ROUTE_PATTERN = "job_detail/{jobId}"
            fun createRoute(jobId: String): String = "job_detail/$jobId"
        }
    }
}

/**
 * Main NavHost composable managing navigation transitions between screens.
 */
@Composable
fun SorceNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppRoute.Onboarding.ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Intro & Onboarding Screen (design.md §2 & rules.md §12)
        composable(AppRoute.Onboarding.ROUTE) {
            val introViewModel: IntroOnboardingViewModel = viewModel()
            val introState by introViewModel.uiState.collectAsStateWithLifecycle()

            IntroOnboardingScreen(
                state = introState,
                onAction = { action ->
                    when (action) {
                        is IntroOnboardingAction.GetStartedClicked,
                        is IntroOnboardingAction.SkipClicked -> {
                            navController.navigate(AppRoute.Home.ROUTE) {
                                popUpTo(AppRoute.Onboarding.ROUTE) {
                                    inclusive = true
                                }
                            }
                        }
                        is IntroOnboardingAction.SignInClicked -> {
                            introViewModel.onAction(action)
                        }
                    }
                }
            )
        }
        // Discovery / Home Screen
        composable(AppRoute.Home.ROUTE) {
            val homeViewModel: HomeViewModel = viewModel()
            val homeState by homeViewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                state = homeState,
                onAction = { action ->
                    when (action) {
                        is HomeAction.JobClicked -> {
                            navController.navigate(AppRoute.JobDetail.createRoute(action.jobId))
                        }
                        is HomeAction.ApplyClicked -> {
                            navController.navigate(AppRoute.JobDetail.createRoute(action.jobId))
                        }
                        is HomeAction.SearchSubmitted,
                        is HomeAction.FindJobsWithAiClicked -> {
                            navController.navigate(AppRoute.Search.ROUTE)
                        }
                        is HomeAction.NavTabSelected -> {
                            if (action.tab == NavTab.SEARCH) {
                                navController.navigate(AppRoute.Search.ROUTE)
                            } else if (action.tab == NavTab.SAVED) {
                                navController.navigate(AppRoute.Saved.ROUTE)
                            } else if (action.tab == NavTab.PROFILE) {
                                navController.navigate(AppRoute.Profile.ROUTE)
                            } else if (action.tab == NavTab.APPLICATIONS) {
                                navController.navigate(AppRoute.Applications.ROUTE)
                            } else {
                                homeViewModel.onAction(action)
                            }
                        }
                        is HomeAction.ProfileClicked -> {
                            navController.navigate(AppRoute.Profile.ROUTE)
                        }
                        is HomeAction.CompanyClicked -> {
                            val targetJobId = when (action.companyId.lowercase()) {
                                "amazon" -> "amazon_senior_swe"
                                "google" -> "google_staff_ux"
                                "microsoft" -> "microsoft_senior_frontend"
                                "apple" -> "apple_ios_swe"
                                "meta" -> "meta_ai_engineer"
                                "netflix" -> "netflix_senior_backend"
                                else -> "amazon_senior_swe"
                            }
                            navController.navigate(AppRoute.JobDetail.createRoute(targetJobId))
                        }
                        is HomeAction.SeeAllCompaniesClicked -> {
                            navController.navigate(AppRoute.Search.ROUTE)
                        }
                        is HomeAction.CategoryClicked -> {
                            val targetJobId = when (action.categoryId.lowercase()) {
                                "engineering" -> "amazon_senior_swe"
                                "design" -> "google_staff_ux"
                                "data_ai" -> "google_staff_cloud"
                                "marketing" -> "microsoft_senior_frontend"
                                else -> "amazon_senior_swe"
                            }
                            navController.navigate(AppRoute.JobDetail.createRoute(targetJobId))
                        }
                        is HomeAction.SeeAllCategoriesClicked -> {
                            navController.navigate(AppRoute.Search.ROUTE)
                        }
                        is HomeAction.AskAiAssistantClicked -> {
                            navController.navigate(AppRoute.AiAssistant.ROUTE)
                        }
                        else -> {
                            homeViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // Search & Explore Jobs Screen (design.md §2 & rules.md §14)
        composable(AppRoute.Search.ROUTE) {
            val searchViewModel: JobSearchViewModel = viewModel()
            val searchState by searchViewModel.uiState.collectAsStateWithLifecycle()

            JobSearchScreen(
                state = searchState,
                onAction = { action ->
                    when (action) {
                        is JobSearchAction.NavTabSelected -> {
                            if (action.tab == NavTab.HOME) {
                                navController.navigate(AppRoute.Home.ROUTE) {
                                    popUpTo(AppRoute.Home.ROUTE) {
                                        inclusive = true
                                    }
                                }
                            } else if (action.tab == NavTab.SAVED) {
                                navController.navigate(AppRoute.Saved.ROUTE)
                            } else if (action.tab == NavTab.PROFILE) {
                                navController.navigate(AppRoute.Profile.ROUTE)
                            } else if (action.tab == NavTab.APPLICATIONS) {
                                navController.navigate(AppRoute.Applications.ROUTE)
                            } else {
                                searchViewModel.onAction(action)
                            }
                        }
                        is JobSearchAction.ProfileClicked -> {
                            navController.navigate(AppRoute.Profile.ROUTE)
                        }
                        is JobSearchAction.JobClicked -> {
                            navController.navigate(AppRoute.JobDetail.createRoute(action.jobId))
                        }
                        is JobSearchAction.ApplyClicked -> {
                            navController.navigate(AppRoute.JobDetail.createRoute(action.jobId))
                        }
                        else -> {
                            searchViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // Job Details Screen (rules.md §15)
        composable(
            route = AppRoute.JobDetail.ROUTE_PATTERN,
            arguments = listOf(
                navArgument(AppRoute.JobDetail.ARG_JOB_ID) {
                    type = NavType.StringType
                    defaultValue = "amazon_senior_swe"
                }
            )
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString(AppRoute.JobDetail.ARG_JOB_ID) ?: "amazon_senior_swe"
            val detailViewModel: JobDetailViewModel = viewModel(
                key = "job_detail_$jobId"
            ) {
                JobDetailViewModel(jobId = jobId)
            }
            val detailState by detailViewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(jobId) {
                detailViewModel.loadJob(jobId)
            }

            JobDetailScreen(
                state = detailState,
                onAction = { action ->
                    when (action) {
                        is JobDetailAction.BackClicked -> {
                            navController.popBackStack()
                        }
                        else -> {
                            detailViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // AI Career Assistant Screen (design.md §2 & rules.md §16)
        composable(AppRoute.AiAssistant.ROUTE) {
            val assistantViewModel: AiAssistantViewModel = viewModel()
            val assistantState by assistantViewModel.uiState.collectAsStateWithLifecycle()

            AiAssistantChatScreen(
                state = assistantState,
                onAction = { action ->
                    when (action) {
                        is AiAssistantAction.BackClicked -> {
                            navController.popBackStack()
                        }
                        else -> {
                            assistantViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // User Profile Screen
        composable(AppRoute.Profile.ROUTE) {
            val profileViewModel: ProfileViewModel = viewModel()
            val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()

            ProfileScreen(
                state = profileState,
                onAction = { action ->
                    when (action) {
                        is ProfileAction.BackClicked -> {
                            navController.popBackStack()
                        }
                        is ProfileAction.NavTabSelected -> {
                            when (action.tab) {
                                NavTab.HOME -> {
                                    navController.navigate(AppRoute.Home.ROUTE) {
                                        popUpTo(AppRoute.Home.ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                }
                                NavTab.SEARCH -> {
                                    navController.navigate(AppRoute.Search.ROUTE)
                                }
                                NavTab.SAVED -> {
                                    navController.navigate(AppRoute.Saved.ROUTE)
                                }
                                NavTab.APPLICATIONS -> {
                                    navController.navigate(AppRoute.Applications.ROUTE)
                                }
                                else -> {
                                    profileViewModel.onAction(action)
                                }
                            }
                        }
                        is ProfileAction.SavedJobsClicked -> {
                            navController.navigate(AppRoute.Saved.ROUTE)
                        }
                        is ProfileAction.ApplicationsClicked -> {
                            navController.navigate(AppRoute.Applications.ROUTE)
                        }
                        is ProfileAction.AiResumeOptimizationClicked -> {
                            navController.navigate(AppRoute.AiAssistant.ROUTE)
                        }
                        else -> {
                            profileViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // Applications & Interview Pipeline Screen
        composable(AppRoute.Applications.ROUTE) {
            val applicationsViewModel: ApplicationsViewModel = viewModel()
            val applicationsState by applicationsViewModel.uiState.collectAsStateWithLifecycle()

            ApplicationsScreen(
                state = applicationsState,
                onAction = { action ->
                    when (action) {
                        is ApplicationsAction.BackClicked -> {
                            navController.popBackStack()
                        }
                        is ApplicationsAction.NavTabSelected -> {
                            when (action.tab) {
                                NavTab.HOME -> {
                                    navController.navigate(AppRoute.Home.ROUTE) {
                                        popUpTo(AppRoute.Home.ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                }
                                NavTab.SEARCH -> {
                                    navController.navigate(AppRoute.Search.ROUTE)
                                }
                                NavTab.SAVED -> {
                                    navController.navigate(AppRoute.Saved.ROUTE)
                                }
                                NavTab.PROFILE -> {
                                    navController.navigate(AppRoute.Profile.ROUTE)
                                }
                                else -> {
                                    applicationsViewModel.onAction(action)
                                }
                            }
                        }
                        is ApplicationsAction.ApplicationClicked -> {
                            navController.navigate(AppRoute.JobDetail.createRoute(action.jobId))
                        }
                        is ApplicationsAction.ViewJobDetailsClicked -> {
                            navController.navigate(AppRoute.JobDetail.createRoute(action.jobId))
                        }
                        is ApplicationsAction.AiInterviewPrepClicked,
                        is ApplicationsAction.StartMockInterviewClicked -> {
                            navController.navigate(AppRoute.AiAssistant.ROUTE)
                        }
                        else -> {
                            applicationsViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // Saved & Bookmarked Jobs Screen
        composable(AppRoute.Saved.ROUTE) {
            val savedViewModel: SavedJobsViewModel = viewModel()
            val savedState by savedViewModel.uiState.collectAsStateWithLifecycle()

            SavedJobsScreen(
                state = savedState,
                onAction = { action ->
                    when (action) {
                        is SavedJobsAction.BackClicked -> {
                            navController.popBackStack()
                        }
                        is SavedJobsAction.JobClicked -> {
                            navController.navigate(AppRoute.JobDetail.createRoute(action.jobId))
                        }
                        is SavedJobsAction.ApplyClicked -> {
                            navController.navigate(AppRoute.JobDetail.createRoute(action.jobId))
                        }
                        is SavedJobsAction.CompareWithAiClicked -> {
                            navController.navigate(AppRoute.AiAssistant.ROUTE)
                        }
                        is SavedJobsAction.ExploreJobsClicked -> {
                            navController.navigate(AppRoute.Search.ROUTE)
                        }
                        is SavedJobsAction.NavTabSelected -> {
                            when (action.tab) {
                                NavTab.HOME -> {
                                    navController.navigate(AppRoute.Home.ROUTE) {
                                        popUpTo(AppRoute.Home.ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                }
                                NavTab.SEARCH -> {
                                    navController.navigate(AppRoute.Search.ROUTE)
                                }
                                NavTab.APPLICATIONS -> {
                                    navController.navigate(AppRoute.Applications.ROUTE)
                                }
                                NavTab.PROFILE -> {
                                    navController.navigate(AppRoute.Profile.ROUTE)
                                }
                                else -> {
                                    savedViewModel.onAction(action)
                                }
                            }
                        }
                        else -> {
                            savedViewModel.onAction(action)
                        }
                    }
                }
            )
        }
    }
}
