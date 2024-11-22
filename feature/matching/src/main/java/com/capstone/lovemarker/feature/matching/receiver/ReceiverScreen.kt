package com.capstone.lovemarker.feature.matching.receiver

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.capstone.lovemarker.core.common.extension.clearFocus
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.component.textfield.LoveMarkerTextField
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.matching.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReceiverRoute(
    navigateUp: () -> Unit,
    navigateToMap: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: ReceiverViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is ReceiverSideEffect.NavigateToMap -> {
                        keyboardController?.hide()
                        navigateToMap()
                    }

                    is ReceiverSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    ReceiverScreen(
        navigateUp = navigateUp,
        invitationCode = state.invitationCode,
        onInvitationCodeChanged = viewModel::updateInvitationCode,
        onClearIconClick = { viewModel.updateInvitationCode("") },
        onCompleteButtonClick = {
            viewModel.postCouple(state.invitationCode)
        }
    )
}

@Composable
fun ReceiverScreen(
    navigateUp: () -> Unit,
    invitationCode: String,
    onInvitationCodeChanged: (String) -> Unit,
    onClearIconClick: () -> Unit,
    onCompleteButtonClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clearFocus(focusManager),
        color = LoveMarkerTheme.colorScheme.surfaceContainer
    ) {
        Column {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.matching_navigate_up_btn_desc)
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 42.dp)
            ) {
                Text(
                    text = stringResource(R.string.matching_receiver_title),
                    style = LoveMarkerTheme.typography.headline20B,
                )
                Spacer(modifier = Modifier.padding(top = 24.dp))
                LoveMarkerTextField(
                    value = invitationCode,
                    onValueChanged = onInvitationCodeChanged,
                    placeholder = stringResource(R.string.matching_receiver_text_field_placeholder),
                    trailingIcon = { isFocused, iconTint ->
                        if (invitationCode.isNotBlank()) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_input_clear),
                                contentDescription = stringResource(R.string.matching_receiver_clear_btn_desc),
                                tint = iconTint,
                                modifier = Modifier.clickable(enabled = isFocused) {
                                    onClearIconClick()
                                }
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            LoveMarkerButton(
                onClick = onCompleteButtonClick,
                buttonText = stringResource(R.string.matching_complete_btn_text),
                enabled = invitationCode.isNotBlank(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ReceiverPreview() {
    LoveMarkerTheme {

    }
}