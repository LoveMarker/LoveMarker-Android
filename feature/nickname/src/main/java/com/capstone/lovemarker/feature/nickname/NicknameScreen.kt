package com.capstone.lovemarker.feature.nickname

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.lovemarker.core.common.extension.hideKeyboard
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.component.textfield.LoveMarkerTextField
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
    viewModel: NicknameViewModel = hiltViewModel(),
) {
    val state by viewModel.nicknameState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current

    UpdateStateByPreviousRoute(prevRoute, viewModel)

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
            when (prevRoute) {
                is Route.Login -> {
                    keyboardController?.hide()
                    navigateToMatching()
                }

                is MainTabRoute.MyPage -> navigateUp()
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
        isError = state.uiState is InputUiState.Error,
        supportingText = state.supportingText,
        completeButtonText = state.completeButtonText,
        completeButtonEnabled = state.completeButtonEnabled,
        onCompleteButtonClick = {
            viewModel.patchNickname(state.nickname)
        },
        closeButtonVisible = state.closeButtonVisible,
        onCloseButtonClick = navigateUp,
        onClearIconClick = {
            viewModel.updateNickname(nickname = "")
        },
    )
}

@Composable
fun UpdateStateByPreviousRoute(prevRoute: Route, viewModel: NicknameViewModel) {
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
                    modifier = Modifier.padding(top = 13.dp)
                )
                LoveMarkerTextField(
                    value = nickname,
                    onValueChanged = onNicknameChanged,
                    placeholder = stringResource(id = R.string.nickname_placeholder),
                    modifier = Modifier.padding(top = 38.dp),
                    isError = isError,
                    supportingText = supportingText,
                    trailingIcon = { isFocused, iconTint ->
                        if (nickname.isNotBlank()) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_input_clear),
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