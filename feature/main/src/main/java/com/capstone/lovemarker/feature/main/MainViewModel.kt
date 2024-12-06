package com.capstone.lovemarker.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.datastore.source.user.UserDataStore
import com.capstone.lovemarker.feature.main.splash.SplashSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<MainSideEffect>()
    val sideEffect: SharedFlow<MainSideEffect> = _sideEffect.asSharedFlow()

    fun checkAutoLogin() = viewModelScope.async {
        userDataStore.userData.first().autoLoginConfigured
    }
}
