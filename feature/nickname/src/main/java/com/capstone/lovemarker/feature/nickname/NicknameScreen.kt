package com.capstone.lovemarker.feature.nickname

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    UpdateStateByPreviousRoute(prevRoute, viewModel)

    NicknameScreen(
        nickname = state.nickname,
        onNicknameChanged = {
            viewModel.apply {
                updateNickname(it)
                validateNickname(it) // todo: 입력할 때마다 상태가 잘 변하는지 확인
            }
        },
        isError = state.uiState is InputUiState.Error,
        supportingText = state.supportingText,
        guideTitle = state.guideTitle,
        completeButtonText = state.completeButtonText,
        completeButtonEnabled = state.completeButtonEnabled,
        onCompleteButtonClick = {
            viewModel.patchNickname(state.nickname)
        },
        closeButtonVisible = state.closeButtonVisible,
        onCloseButtonClick = navigateUp
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
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    isError: Boolean,
    guideTitle: String,
    supportingText: String,
    completeButtonText: String,
    completeButtonEnabled: Boolean,
    onCompleteButtonClick: () -> Unit,
    closeButtonVisible: Boolean,
    onCloseButtonClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
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
                    color = LoveMarkerTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 13.dp)
                )
                OutlinedTextField(
                    value = nickname,
                    onValueChange = onNicknameChanged,
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(text = supportingText)
                        }
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_input_clear),
                            contentDescription = stringResource(R.string.nickname_clear_icon_desc)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 38.dp)

                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onCompleteButtonClick,
                enabled = completeButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = completeButtonText,
                    color = LoveMarkerTheme.colorScheme.onSurface
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
            completeButtonText = stringResource(id = R.string.nickname_complete_btn_text_from_login),
            completeButtonEnabled = false,
            onCompleteButtonClick = {},
            closeButtonVisible = true,
            onCloseButtonClick = {},
        )
    }
}