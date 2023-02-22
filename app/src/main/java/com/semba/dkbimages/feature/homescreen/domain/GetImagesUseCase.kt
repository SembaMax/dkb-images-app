package com.semba.dkbimages.feature.homescreen.domain

import com.semba.dkbimages.data.Repository
import com.semba.dkbimages.feature.homescreen.ui.HomeScreenUiState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke() : Flow<HomeScreenUiState> =
    repository.loadImages()
    .map {
        if (it.errorCode != null)
            HomeScreenUiState.Error(it.errorCode)
        else {
            HomeScreenUiState.Success(it.data ?: emptyList())
        }
    }
    .catch { emit(HomeScreenUiState.Error(-1)) }
    .onStart { emit(HomeScreenUiState.Loading) }
}