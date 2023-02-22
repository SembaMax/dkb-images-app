package com.semba.dkbimages.feature.detailsceen.domain

import com.semba.dkbimages.data.Repository
import com.semba.dkbimages.data.model.ImageModel
import com.semba.dkbimages.feature.detailsceen.ui.DetailUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetImageDetailUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(imageId: Int): Flow<DetailUiState> =
        repository.loadImageDetail(imageId)
            .map {
                if (it.errorCode != null)
                    DetailUiState.Error(it.errorCode)
                else {
                    DetailUiState.Success(it.data ?: ImageModel.empty())
                }
            }
            .catch { emit(DetailUiState.Error(-1)) }
            .onStart { emit(DetailUiState.Loading) }
}