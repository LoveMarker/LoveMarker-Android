package com.capstone.lovemarker.feature.main.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navigateToMap: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    navigateToLogin()

//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    LaunchedEffect(Unit) {
//        viewModel.checkAutoLogin()
//    }
//
//    LaunchedEffect(viewModel.splashSideEffect, lifecycleOwner) {
//        viewModel.splashSideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
//            .collectLatest { sideEffect ->
//                when (sideEffect) {
//                    is SplashSideEffect.NavigateToMap -> navigateToMap()
//                    is SplashSideEffect.NavigateToLogin -> navigateToLogin()
//                }
//            }
//    }
}
