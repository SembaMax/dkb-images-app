package com.semba.dkbimages.feature.detailsceen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semba.dkbimages.data.model.ImageModel
import com.semba.dkbimages.feature.detailsceen.domain.GetImageDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getImageDetailUseCase: GetImageDetailUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)

    val uiState =_uiState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        DetailUiState.Loading
    )

    fun loadImageDetail(imageId: Int) {
        getImageDetailUseCase(imageId)
            .onEach {
                _uiState.value = it
            }.launchIn(viewModelScope)
    }
}

sealed interface DetailUiState {
    object Loading: DetailUiState
    data class Success(val imageItem: ImageModel): DetailUiState
    data class Error(val errorCode: Int?): DetailUiState
}