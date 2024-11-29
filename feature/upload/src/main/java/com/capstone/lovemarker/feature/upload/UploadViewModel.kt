package com.capstone.lovemarker.feature.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.upload.entity.UploadRequestEntity
import com.capstone.lovemarker.domain.upload.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadRepository: UploadRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UploadState())
    val state: StateFlow<UploadState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UploadSideEffect>()
    val sideEffect: SharedFlow<UploadSideEffect> = _sideEffect.asSharedFlow()

    fun updateImages(uris: List<Uri>) {
        _state.update { state ->
            state.copy(
                images = uris.map { it.toString() }.toPersistentList()
            )
        }
    }

    fun updatePlace(address: String, latLng: Pair<Double, Double>) {
        _state.update {
            it.copy(
                address = address,
                latLng = latLng
            )
        }
    }

    fun updateDate(date: String) {
        _state.update {
            it.copy(date = date)
        }
        updateButtonEnabled(enabled = checkAllFilled())
    }

    fun updateTitle(title: String) {
        _state.update {
            it.copy(title = title)
        }
        updateButtonEnabled(enabled = checkAllFilled())
    }

    fun updateContent(content: String) {
        _state.update {
            it.copy(content = content)
        }
        updateButtonEnabled(enabled = checkAllFilled())
    }

    private fun checkAllFilled() = with(state.value) {
        address.isNotBlank() && date.isNotBlank() && title.isNotBlank() && content.isNotBlank()
    }

    private fun updateButtonEnabled(enabled: Boolean) {
        _state.update {
            it.copy(
                completeButtonEnabled = enabled
            )
        }
    }

    fun postMemory() {
        updateButtonEnabled(false)

        viewModelScope.launch {
            uploadRepository.postMemory(
                images = state.value.images,
                requestEntity = with(state.value) {
                    UploadRequestEntity(
                        latitude = latLng.first,
                        longitude = latLng.second,
                        address = address,
                        date = date,
                        title = title,
                        content = content,
                    )
                }
            ).onSuccess { response ->
                _sideEffect.emit(UploadSideEffect.ShowSuccessSnackbar("업로드에 성공했습니다."))

//                _sideEffect.emit(
//                    UploadSideEffect.NavigateToMap(
//                        memoryId = response.memoryId
//                    )
//                )
            }.onFailure {
                updateButtonEnabled(true)
                Timber.e(it.message)
                _sideEffect.emit(UploadSideEffect.ShowErrorSnackbar(it))
            }
        }
    }
}
