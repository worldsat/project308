package com.uilover.project308.ui.profile

import com.uilover.project308.data.model.NavTab

/**
 * Immutable UI State for ProfileScreen per rules.md §18 and §19.
 */
data class ProfileUiState(
    val userName: String = "Alex Morgan",
    val userHeadline: String = "Senior Full-Stack & Mobile Engineer",
    val location: String = "San Francisco, CA",
    val email: String = "alex.morgan@techmail.io",
    val profileMatchScore: Int = 94,
    val applicationsCount: Int = 12,
    val interviewsScheduled: Int = 4,
    val savedJobsCount: Int = 8,
    val skills: List<String> = listOf(
        "Kotlin",
        "Jetpack Compose",
        "Flutter UI",
        "TypeScript",
        "System Architecture",
        "AWS Cloud",
        "Kubernetes",
        "CI/CD"
    ),
    val targetRole: String = "Senior / Staff Mobile Engineer",
    val targetSalaryRange: String = "$160,000 – $210,000 / yr",
    val workStylePreference: String = "Hybrid • Remote-first",
    val aiResumeOptimized: Boolean = true,
    val aiResumeScore: Int = 98,
    val selectedNavTab: NavTab = NavTab.PROFILE,
    val feedbackMessage: String? = null
)
