package com.capstone.lovemarker.feature.nickname

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.component.textfield.LoveMarkerTextField
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.core.navigation.Route
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val NICKNAME_MAX_LENGTH = 8

@Composable
fun NicknameRoute(
    prevRouteName: String,
    navigateToMatching: () -> Unit,
    navigateToMyPage: (String) -> Unit,
    navigateUp: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    currentNickname: String? = null,
    viewModel: NicknameViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        currentNickname?.let { nickname ->
            viewModel.updatePlaceholder(nickname)
        }
    }

    val prevRoute: Route = if (prevRouteName == "mypage") MainTabRoute.MyPage() else Route.Login
    UpdateStateByPreviousRoute(prevRoute, viewModel)

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is NicknameSideEffect.NavigateToMyPage -> {
                        navigateToMyPage(sideEffect.nickname)
                    }

                    is NicknameSideEffect.NavigateToMatching -> navigateToMatching()

                    is NicknameSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    when (val uiState = state.uiState) {
        is InputUiState.Empty -> {
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
            keyboardController?.hide()

            when (prevRoute) {
                is Route.Login -> {
                    viewModel.triggerMatchingNavigationEffect()
                }

                is MainTabRoute.MyPage -> {
                    viewModel.triggerMyPageNavigationEffect(uiState.nickname)
                }

                else -> {
                    Timber.e("invalid navigation path on the nickname screen.")
                }
            }
        }
    }

    NicknameScreen(
        nickname = state.nickname,
        onNicknameChanged = {
            viewModel.updateNickname(nickname = it)
        },
        guideTitle = state.guideTitle,
        placeholder = state.placeholder,
        isError = state.uiState is InputUiState.Error,
        supportingText = state.supportingText,
        completeButtonText = state.completeButtonText,
        completeButtonEnabled = state.completeButtonEnabled,
        onCompleteButtonClick = {
            viewModel.patchNickname(state.nickname)
        },
        closeButtonVisible = state.closeButtonVisible,
        onCloseButtonClick = {
            keyboardController?.hide()
            navigateUp()
        },
        onClearIconClick = {
            viewModel.updateNickname(nickname = "")
        },
    )
}

@Composable
fun UpdateStateByPreviousRoute(
    prevRoute: Route,
    viewModel: NicknameViewModel
) {
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
    guideTitle: String,
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    placeholder: String,
    isError: Boolean,
    supportingText: String,
    onClearIconClick: () -> Unit,
    completeButtonText: String,
    completeButtonEnabled: Boolean,
    onCompleteButtonClick: () -> Unit,
    closeButtonVisible: Boolean,
    onCloseButtonClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    if (isError) {
                        keyboardController?.hide()
                    } else {
                        focusManager.clearFocus()
                    }
                })
            },
        color = LoveMarkerTheme.colorScheme.surfaceContainer
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (closeButtonVisible) {
                IconButton(onClick = onCloseButtonClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.nickname_close_icon_desc)
                    )
                }
            }
            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = if (closeButtonVisible) 24.dp else 77.dp
                )
            ) {
                Text(
                    text = guideTitle,
                    style = LoveMarkerTheme.typography.headline20B,
                )
                Text(
                    text = stringResource(id = R.string.nickname_guide_detail),
                    style = LoveMarkerTheme.typography.label13M,
                    color = LoveMarkerTheme.colorScheme.onSurface700,
                    modifier = Modifier.padding(top = 16.dp)
                )
                LoveMarkerTextField(
                    value = nickname,
                    onValueChanged = onNicknameChanged,
                    placeholder = placeholder,
                    modifier = Modifier.padding(top = 24.dp),
                    maxLength = NICKNAME_MAX_LENGTH,
                    isError = isError,
                    supportingText = supportingText,
                    trailingIcon = { isFocused, iconTint ->
                        if (nickname.isNotBlank()) {
                            Icon(
                                painter = painterResource(id = com.capstone.lovemarker.core.designsystem.R.drawable.ic_clear_outlined_24),
                                contentDescription = stringResource(R.string.nickname_clear_icon_desc),
                                tint = iconTint,
                                modifier = Modifier.clickable(enabled = isFocused) {
                                    onClearIconClick()
                                }
                            )
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            LoveMarkerButton(
                onClick = onCompleteButtonClick,
                buttonText = completeButtonText,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                enabled = completeButtonEnabled
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknamePreview() {
    LoveMarkerTheme {
        NicknameScreen(
            nickname = "",
            onNicknameChanged = {},
            placeholder = "닉네임",
            isError = false,
            guideTitle = stringResource(id = R.string.nickname_guide_title_from_login),
            supportingText = stringResource(id = R.string.nickname_duplicate_error_msg),
            onClearIconClick = {},
            completeButtonText = stringResource(id = R.string.nickname_complete_btn_text_from_login),
            completeButtonEnabled = false,
            onCompleteButtonClick = {},
            closeButtonVisible = true,
            onCloseButtonClick = {},
        )
    }
}