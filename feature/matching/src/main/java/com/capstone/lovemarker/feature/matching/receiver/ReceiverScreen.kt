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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.common.extension.clearFocus
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.component.textfield.LoveMarkerTextField
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.matching.R

@Composable
fun ReceiverScreen(
    navigateUp: () -> Unit,
    navigateToMap: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var invitationCode by remember { mutableStateOf("") }

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
                    onValueChanged = { invitationCode = it },
                    placeholder = stringResource(R.string.matching_receiver_text_field_placeholder),
                    trailingIcon = { isFocused, iconTint ->
                        if (invitationCode.isNotBlank()) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_input_clear),
                                contentDescription = stringResource(R.string.matching_receiver_clear_btn_desc),
                                tint = iconTint,
                                modifier = Modifier.clickable(enabled = isFocused) {
                                    invitationCode = ""
                                }
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            LoveMarkerButton(
                onClick = {
                    keyboardController?.hide()
                    navigateToMap() // todo: 지도 화면에서 매칭 결과 스낵바로 표시
                },
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
        ReceiverScreen(
            navigateUp = {},
            navigateToMap = {}
        )
    }
}