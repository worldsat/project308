package com.uilover.project308.ui.search

import com.uilover.project308.data.repository.DemoCareerRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class JobSearchViewModelTest {

    private lateinit var viewModel: JobSearchViewModel

    @Before
    fun setUp() {
        viewModel = JobSearchViewModel()
    }

    @Test
    fun initialState_hasAllJobsAndDefaultCategory() {
        val state = viewModel.uiState.value
        assertEquals("", state.query)
        assertEquals("All", state.selectedCategory)
        assertEquals(DemoCareerRepository.getAllJobs().size, state.filteredJobs.size)
        assertEquals(7, state.allJobs.size)
    }

    @Test
    fun queryChanged_filtersCorrectly() {
        viewModel.onAction(JobSearchAction.QueryChanged("Google"))
        val state = viewModel.uiState.value
        assertEquals("Google", state.query)
        assertTrue(state.filteredJobs.all { it.companyName.contains("Google", ignoreCase = true) })
        assertEquals(2, state.filteredJobs.size)
    }

    @Test
    fun categorySelected_filtersEngineeringRoles() {
        viewModel.onAction(JobSearchAction.CategorySelected("Engineering"))
        val state = viewModel.uiState.value
        assertEquals("Engineering", state.selectedCategory)
        assertTrue(state.filteredJobs.isNotEmpty())
        assertTrue(state.filteredJobs.all {
            it.roleTitle.contains("engineer", ignoreCase = true) || it.roleTitle.contains("swe", ignoreCase = true)
        })
    }

    @Test
    fun categorySelected_filtersDesignRoles() {
        viewModel.onAction(JobSearchAction.CategorySelected("Design"))
        val state = viewModel.uiState.value
        assertEquals("Design", state.selectedCategory)
        assertEquals(1, state.filteredJobs.size)
        assertEquals("google_staff_ux", state.filteredJobs.first().id)
    }

    @Test
    fun clearFilters_resetsState() {
        viewModel.onAction(JobSearchAction.QueryChanged("React"))
        viewModel.onAction(JobSearchAction.CategorySelected("Engineering"))
        viewModel.onAction(JobSearchAction.ClearFiltersClicked)

        val state = viewModel.uiState.value
        assertEquals("", state.query)
        assertEquals("All", state.selectedCategory)
        assertEquals(DemoCareerRepository.getAllJobs().size, state.filteredJobs.size)
    }

    @Test
    fun bookmarkToggled_updatesStatus() {
        val targetJobId = "amazon_senior_swe"
        val initialStatus = viewModel.uiState.value.allJobs.first { it.id == targetJobId }.isBookmarked

        viewModel.onAction(JobSearchAction.BookmarkToggled(targetJobId))
        val newStatus = viewModel.uiState.value.allJobs.first { it.id == targetJobId }.isBookmarked
        assertEquals(!initialStatus, newStatus)

        // Toggle back
        viewModel.onAction(JobSearchAction.BookmarkToggled(targetJobId))
        val restoredStatus = viewModel.uiState.value.allJobs.first { it.id == targetJobId }.isBookmarked
        assertEquals(initialStatus, restoredStatus)
    }

    @Test
    fun aiFilterClicked_triggersSnackbarMessage() {
        viewModel.onAction(JobSearchAction.AiFilterClicked)
        val state = viewModel.uiState.value
        assertEquals("AI matching filters applied.", state.snackbarMessage)

        viewModel.onAction(JobSearchAction.SnackbarDismissed)
        assertEquals(null, viewModel.uiState.value.snackbarMessage)
    }
}
