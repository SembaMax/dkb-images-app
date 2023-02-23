package com.semba.dkbimages

import com.semba.dkbimages.data.model.toModel
import com.semba.dkbimages.feature.homescreen.domain.GetImagesUseCase
import com.semba.dkbimages.feature.homescreen.ui.HomeScreenUiState
import com.semba.dkbimages.feature.homescreen.ui.HomeViewModel
import com.semba.dkbimages.testing.TestImagesRepository
import com.semba.dkbimages.util.MainDispatcherRule
import com.semba.dkbimages.util.Utils
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TestImagesRepository
    private lateinit var getImagesUseCase: GetImagesUseCase
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        repository = TestImagesRepository()
        getImagesUseCase = GetImagesUseCase(repository)
        homeViewModel = HomeViewModel(getImagesUseCase)
    }

    @Test
    fun `state is initially loading`() = runTest {
        assertEquals(HomeScreenUiState.Loading, homeViewModel.uiState.value)
    }

    @Test
    fun `state is success on successful request`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.uiState.collect() }

        repository.setIsSuccessful(true)
        repository.setImageItems(emptyList())

        homeViewModel.loadData()

        assert(homeViewModel.uiState.value is HomeScreenUiState.Success)

        collectJob.cancel()
    }

    @Test
    fun `state is error on failure request`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.uiState.collect() }

        repository.setIsSuccessful(false)
        repository.setImageItems(emptyList())

        homeViewModel.loadData()

        assert(homeViewModel.uiState.value is HomeScreenUiState.Error)

        collectJob.cancel()
    }

    @Test
    fun `emit correct data on successful request`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.uiState.collect() }

        val result = Utils.imagesJsonAsItems()
        repository.setIsSuccessful(true)
        repository.setImageItems(result)

        homeViewModel.loadData()

        assert(homeViewModel.uiState.value is HomeScreenUiState.Success)
        assertEquals(result.map { it.toModel() }, (homeViewModel.uiState.value as HomeScreenUiState.Success).imageItems)

        collectJob.cancel()
    }

    @Test
    fun `emit correct data on failure request`() = runTest {

        val collectJob =
            launch(UnconfinedTestDispatcher()) { homeViewModel.uiState.collect() }

        repository.setIsSuccessful(false)
        repository.setImageItems(emptyList())

        homeViewModel.loadData()

        assert(homeViewModel.uiState.value is HomeScreenUiState.Error)
        assertEquals(-1, (homeViewModel.uiState.value as HomeScreenUiState.Error).errorCode)

        collectJob.cancel()
    }

}