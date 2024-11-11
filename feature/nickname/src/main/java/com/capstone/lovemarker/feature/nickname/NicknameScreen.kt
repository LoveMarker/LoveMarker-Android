package com.capstone.lovemarker.feature.nickname

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.lovemarker.core.common.extension.addFocusCleaner
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
    viewModel: NicknameViewModel = viewModel(),
) {
    val state by viewModel.nicknameState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

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
                is Route.Login -> navigateToMatching()
                is MainTabRoute.MyPage -> navigateUp()
                else -> {
                    Timber.e("invalid navigation path on the nickname screen.")
                }
            }
        }
    }

    // todo: 키보드 위에 하단 버튼이 보이도록 변경
    //  텍스트 필드 핸들러 색상 변경
    //  닉네임 변경하는 서버 api 호출 (뷰모델 코드 수정)
    NicknameScreen(
        guideTitle = state.guideTitle,
        nickname = state.nickname,
        onNicknameChanged = {
            viewModel.apply {
                updateNickname(it)
                validateNickname(it)
            }
        },
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
            viewModel.updateNickname("")
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
    completeButtonText: String,
    completeButtonEnabled: Boolean,
    onCompleteButtonClick: () -> Unit,
    closeButtonVisible: Boolean,
    onCloseButtonClick: () -> Unit,
    onClearIconClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
        color = LoveMarkerTheme.colorScheme.surfaceContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
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
                OutlinedTextField(
                    value = nickname,
                    onValueChange = onNicknameChanged,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = LoveMarkerTheme.colorScheme.surfaceContainer,
                        unfocusedIndicatorColor = LoveMarkerTheme.colorScheme.onSurface400,
                        unfocusedTextColor = LoveMarkerTheme.colorScheme.onSurface400,
                        focusedContainerColor = LoveMarkerTheme.colorScheme.surfaceContainer,
                        focusedIndicatorColor = LoveMarkerTheme.colorScheme.outlineBrown,
                        focusedTextColor = LoveMarkerTheme.colorScheme.onSurface700,
                        focusedTrailingIconColor = LoveMarkerTheme.colorScheme.outlineBrown,
                        unfocusedTrailingIconColor = LoveMarkerTheme.colorScheme.onSurface400,
                        errorContainerColor = LoveMarkerTheme.colorScheme.surfaceContainer,
                        errorIndicatorColor = LoveMarkerTheme.colorScheme.error,
                        errorSupportingTextColor = LoveMarkerTheme.colorScheme.error,
                        errorTrailingIconColor = LoveMarkerTheme.colorScheme.error,
                        cursorColor = LoveMarkerTheme.colorScheme.outlineBrown,
                        errorCursorColor = LoveMarkerTheme.colorScheme.error
                    ),
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(text = supportingText)
                        }
                    },
                    trailingIcon = {
                        if (nickname.isNotBlank()) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_input_clear),
                                contentDescription = stringResource(R.string.nickname_clear_icon_desc),
                                modifier = Modifier.clickable {
                                    onClearIconClick()
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 38.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onCompleteButtonClick,
                colors = ButtonColors(
                    containerColor = LoveMarkerTheme.colorScheme.secondaryContainer,
                    contentColor = LoveMarkerTheme.colorScheme.onSecondaryContainer,
                    disabledContainerColor = LoveMarkerTheme.colorScheme.onSurface200,
                    disabledContentColor = LoveMarkerTheme.colorScheme.onSurface500
                ),
                enabled = completeButtonEnabled,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = completeButtonText,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknamePreview() {
    LoveMarkerTheme {
        NicknameScreen(
            nickname = "leeeha",
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