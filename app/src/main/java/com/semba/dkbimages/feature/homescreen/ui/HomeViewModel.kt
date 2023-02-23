package com.semba.dkbimages.feature.homescreen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semba.dkbimages.design.navigation.IMAGE_ID_ARG
import com.semba.dkbimages.data.model.ImageModel
import com.semba.dkbimages.feature.homescreen.domain.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getImagesUseCase: GetImagesUseCase): ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUiState> = MutableStateFlow(HomeScreenUiState.Loading)

    val uiState =_uiState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        HomeScreenUiState.Loading
    )

    init {
        loadData()
    }

    fun loadData() {
        fetchImages()
    }

    private fun fetchImages() {
        getImagesUseCase()
            .onEach { result -> _uiState.value = result }
            .launchIn(viewModelScope)
    }

}

fun ImageModel.toArgs(): Map<String,String> = mapOf(
    IMAGE_ID_ARG to this.id.toString()
)

sealed interface HomeScreenUiState {
    object Loading: HomeScreenUiState
    data class Success(val imageItems: List<ImageModel> = emptyList()): HomeScreenUiState
    class Error(val errorCode: Int?): HomeScreenUiState
}