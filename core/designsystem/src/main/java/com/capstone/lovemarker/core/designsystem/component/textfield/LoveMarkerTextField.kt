package com.capstone.lovemarker.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Brown700
import com.capstone.lovemarker.core.designsystem.theme.Error
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme

@Composable
fun LoveMarkerTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    supportingText: String = "",
    leadingIcon: @Composable (isFocused: Boolean, iconTint: Color) -> Unit = { _, _ -> },
    trailingIcon: @Composable (isFocused: Boolean, iconTint: Color) -> Unit = { _, _ -> },
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val borderColor = if (isError) Error else Brown700
    val supportingTextColor = if (isError) Error else Gray700
    val iconTint = if (isError) Error else Brown700
    val textSelectionColors = TextSelectionColors(
        handleColor = if (isError) Error else Brown700,
        backgroundColor = if (isError) Error.copy(alpha = 0.4f) else Brown700.copy(alpha = 0.4f)
    )

    Column(
        modifier = modifier
    ) {
        CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
            BasicTextField(
                value = value,
                onValueChange = onValueChanged,
                modifier = Modifier
                    .height(54.dp)
                    .border(
                        width = 1.5.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = Beige400)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                singleLine = true,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                    }
                ),
                textStyle = LoveMarkerTheme.typography.body14M.copy(color = Gray700),
                cursorBrush = SolidColor(value = if (isError) Error else Brown700),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        leadingIcon(isFocused, iconTint)
                        Box(modifier = Modifier.weight(1f)) {
                            innerTextField()
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Gray400,
                                    style = LoveMarkerTheme.typography.body14M
                                )
                            }
                        }
                        trailingIcon(isFocused, iconTint)
                    }
                }
            )
        }

        if (isError) {
            Text(
                text = supportingText,
                color = supportingTextColor,
                style = LoveMarkerTheme.typography.label12M,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoveMarkerTextFieldPreview() {
    LoveMarkerTheme {
        Column {
            LoveMarkerTextField(
                value = "",
                onValueChanged = {},
                isError = true,
                placeholder = "닉네임을 입력해주세요",
                supportingText = "supporting text",
                trailingIcon = { isFocused, iconTint ->
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.clickable(enabled = isFocused) {}
                    )
                }
            )
        }
    }
}