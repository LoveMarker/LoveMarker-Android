package com.capstone.lovemarker

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val _photoUris = mutableStateOf(emptyList<Uri>())
    val photoUris : State<List<Uri>> = _photoUris

    fun fetchPhotos(uris: List<Uri>) {
        _photoUris.value = uris
    }
}
