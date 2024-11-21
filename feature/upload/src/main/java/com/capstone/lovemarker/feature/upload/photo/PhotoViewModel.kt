package com.capstone.lovemarker.feature.upload.photo

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PhotoViewModel : ViewModel() {
    private val _state = MutableStateFlow(PhotoState())
    val state: StateFlow<PhotoState> = _state.asStateFlow()

    fun updateImages(uris: List<Uri>) {
        _state.update {
            it.copy(imageUris = uris.toPersistentList())
        }
    }
}
