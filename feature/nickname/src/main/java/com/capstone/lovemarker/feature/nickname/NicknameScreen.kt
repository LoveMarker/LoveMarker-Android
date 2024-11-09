package com.capstone.lovemarker.feature.nickname

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.core.navigation.Route
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun NicknameRoute(
    prevRoute: Route,
    navigateUp: () -> Unit,
    navigateToMatching: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: NicknameViewModel = NicknameViewModel(),
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

    when (val uiState = state.uiState) {
        is InputUiState.Blank -> {
            viewModel.updateCompleteButtonEnabled(enabled = false)
        }

        is InputUiState.Valid -> {
            viewModel.updateCompleteButtonEnabled(enabled = true)
        }

        is InputUiState.Error -> {
            viewModel.updateCompleteButtonEnabled(enabled = false)

            when (uiState) {
                InputUiState.Error.NOT_ALLOWED_CHAR -> {
                    viewModel.updateSupportingText(text = stringResource(id = R.string.nickname_not_allowed_char_error_msg))
                }

                InputUiState.Error.DUPLICATED -> {
                    viewModel.updateSupportingText(text = stringResource(id = R.string.nickname_duplicate_error_msg))
                }
            }
        }

        is InputUiState.Success -> {
            when (prevRoute) {
                is Route.Login -> navigateToMatching()
                is MainTabRoute.MyPage -> navigateUp()
                else -> {
                    Timber.e("invalid navigation path on the nickname screen.")
                }
            }
        }
    }

    updateStateByPreviousRoute(prevRoute, viewModel)

    NicknameScreen(
        nickname = state.nickname,
        onNicknameChanged = {
            viewModel.apply {
                updateNickname(it)
                validateNickname(it) // todo: 입력할 때마다 상태가 잘 변하는지 확인
            }
        },
        isError = state.uiState is InputUiState.Error,
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
fun updateStateByPreviousRoute(prevRoute: Route, viewModel: NicknameViewModel) {
    when (prevRoute) {
        is Route.Login -> {
            viewModel.apply {
                updateGuideTitle(stringResource(id = R.string.nickname_guide_title_from_login))
                updateCompleteButtonText(stringResource(id = R.string.nickname_complete_btn_text_from_login))
                updateCloseButtonVisibility(visible = false)
            }
        }

        is MainTabRoute.MyPage -> {
            viewModel.apply {
                updateGuideTitle(stringResource(id = R.string.nickname_guide_title_from_mypage))
                updateCompleteButtonText(stringResource(id = R.string.nickname_complete_btn_text_from_mypage))
                updateCloseButtonVisibility(visible = true)
            }
        }

        else -> {
            Timber.e("invalid navigation path on the nickname screen.")
        }
    }
}

@Composable
fun NicknameScreen(
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    isError: Boolean,
    guideTitle: String,
    completeButtonText: String,
    completeButtonEnabled: Boolean,
    onCompleteButtonClick: (String) -> Unit,
    closeButtonVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = nickname,
        onValueChange = onNicknameChanged,
    )
}

@Preview(showBackground = true)
@Composable
private fun NicknamePreview() {
    LoveMarkerTheme {

    }
}