package com.capstone.lovemarker.feature.main.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun SplashScreen(
    navigateToMap: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        val autoLoginConfigured = viewModel.getAutoLoginConfiguration()
        if (autoLoginConfigured) {
            navigateToMap()
        } else {
            navigateToLogin()
        }
    }
}
