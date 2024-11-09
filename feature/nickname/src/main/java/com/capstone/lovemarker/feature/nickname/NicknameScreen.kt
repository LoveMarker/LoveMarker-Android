package com.capstone.lovemarker.feature.nickname

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NicknameRoute(
    navigateUp: () -> Unit,
    navigateToMatching: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: NicknameViewModel = NicknameViewModel()
) {
    val state by viewModel.nicknameState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.nicknameSideEffect, lifecycleOwner) {
        viewModel.nicknameSideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is NicknameSideEffect.NavigateToMyPage -> navigateUp()
                    is NicknameSideEffect.NavigateToMatching -> navigateToMatching()
                    is NicknameSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    NicknameScreen(
        navigateUp = navigateUp,
        nickname = state.nickname,
        onNicknameChanged = {
            viewModel.updateNickname(it)
        },
        validateNickname = {
            viewModel.validateNickname(it)
        },
        guideTitle = state.guideTitle,
        completeButtonText = state.completeButtonText,
        completeButtonEnabled = state.completeButtonEnabled,
        onCompleteButtonClick = {
            viewModel.patchNickname(it)
        },
        closeButtonVisible = state.closeButtonVisible,

    )
}

@Composable
fun NicknameScreen(
    navigateUp: () -> Unit,
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    validateNickname: (String) -> Boolean,
    guideTitle: String,
    completeButtonText: String,
    completeButtonEnabled: Boolean,
    onCompleteButtonClick: (String) -> Unit,
    closeButtonVisible: Boolean,
    modifier: Modifier = Modifier
) {

}