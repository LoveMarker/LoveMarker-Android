package com.capstone.lovemarker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _url = mutableStateOf("https://www.google.com")
    val url : State<String> = _url

    private val _undoSharedFlow = MutableSharedFlow<Boolean>()
    val undoSharedFlow = _undoSharedFlow.asSharedFlow()

    private val _redoSharedFlow = MutableSharedFlow<Boolean>()
    val redoSharedFlow = _redoSharedFlow.asSharedFlow()

    fun updateUrl(url: String) {
        _url.value = url
    }

    fun undo() {
        viewModelScope.launch {
            _undoSharedFlow.emit(true)
        }
    }

    fun redo() {
        viewModelScope.launch {
            _redoSharedFlow.emit(true)
        }
    }
}