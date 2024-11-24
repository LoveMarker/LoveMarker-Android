package com.capstone.lovemarker.feature.upload

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.capstone.lovemarker.core.model.SearchPlace
import com.capstone.lovemarker.core.navigation.SearchPlaceNavType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.typeOf

class UploadViewModel : ViewModel() {
    private val _state = MutableStateFlow(UploadState())
    val state: StateFlow<UploadState> = _state.asStateFlow()

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
        updateButtonEnabled()
    }

    fun updateTitle(title: String) {
        _state.update {
            it.copy(title = title)
        }
        updateButtonEnabled()
    }

    fun updateContent(content: String) {
        _state.update {
            it.copy(content = content)
        }
        updateButtonEnabled()
    }

    private fun updateButtonEnabled() {
        _state.update {
            it.copy(
                completeButtonEnabled = it.address.isNotBlank() &&
                        it.date.isNotBlank() &&
                        it.title.isNotBlank() &&
                        it.content.isNotBlank()
            )
        }
    }
}
