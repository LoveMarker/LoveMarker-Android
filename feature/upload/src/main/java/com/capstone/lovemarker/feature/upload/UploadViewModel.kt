package com.capstone.lovemarker.feature.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

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
}
