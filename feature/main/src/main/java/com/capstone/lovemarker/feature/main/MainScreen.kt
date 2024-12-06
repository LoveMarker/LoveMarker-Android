package com.capstone.lovemarker.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navOptions
import com.capstone.lovemarker.core.designsystem.component.snackbar.LoveMarkerSnackbar
import com.capstone.lovemarker.feature.main.component.MainBottomBar
import com.capstone.lovemarker.feature.main.navigation.MainNavHost
import com.capstone.lovemarker.feature.main.navigation.MainNavigator
import com.capstone.lovemarker.feature.main.navigation.MainTab
import com.capstone.lovemarker.feature.main.navigation.rememberMainNavigator
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

private const val SNACK_BAR_DURATION = 2000L

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val resources = LocalContext.current.resources

    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            val job = launch {
                snackBarHostState.showSnackbar(
                    when (throwable) {
                        is UnknownHostException -> resources.getString(R.string.error_message_network)
                        else -> throwable?.message.toString()
                    }
                )
            }
            delay(SNACK_BAR_DURATION)
            job.cancel()
        }
    }

    LaunchedEffect(Unit) {
        if (viewModel.checkAutoLogin().await()) {
            navigator.navigateToMap(
                navOptions {
                    popUpTo(navigator.navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            )
        } else {
            navigator.navigateToLogin(
                navOptions {
                    popUpTo(navigator.navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            )
        }
    }

    MainScreenContent(
        navigator = navigator,
        showErrorSnackbar = onShowErrorSnackBar,
        snackbarHostState = snackBarHostState
    )
}

@Composable
fun MainScreenContent(
    navigator: MainNavigator,
    showErrorSnackbar: (throwable: Throwable?) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
            ) {
                MainNavHost(
                    navigator = navigator,
                    innerPadding = innerPadding,
                    showErrorSnackbar = showErrorSnackbar,
                )
            }
        },
        bottomBar = {
            // todo: false에서 끝나야 하는데, 왜 다시 true로 돌아가는 걸까??
            val isVisible = navigator.shouldShowBottomBar()
            Timber.tag("recomposition").d("$isVisible")

            MainBottomBar(
                visible = isVisible,
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab
            ) { selectedTab ->
                navigator.navigate(selectedTab)
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    LoveMarkerSnackbar(message = snackbarData.visuals.message)
                }
            )
        }
    )
}