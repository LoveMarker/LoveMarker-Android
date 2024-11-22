package com.capstone.lovemarker.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.designsystem.theme.Brown700
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme

@Composable
fun CounterTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    currentLength: Int,
    maxLength: Int,
    height: Dp,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val borderColor = if (isFocused) Brown700 else Gray400
    val textSelectionColors = TextSelectionColors(
        handleColor = Brown700,
        backgroundColor = Brown700.copy(alpha = 0.4f)
    )

    Column(
        modifier = modifier
    ) {
        CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
            BasicTextField(
                value = value,
                onValueChange = { newValue ->
                    if (newValue.length <= maxLength) {
                        onValueChanged(newValue)
                    }
                },
                modifier = Modifier
                    .height(height)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(color = Color.White)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                singleLine = singleLine,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                    onSearch = {
                        focusManager.clearFocus()
                    }
                ),
                textStyle = LoveMarkerTheme.typography.body14M.copy(color = Gray700),
                cursorBrush = SolidColor(value = Brown700),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            innerTextField()
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Gray300,
                                    style = LoveMarkerTheme.typography.body14M
                                )
                            }
                        }
                    }
                }
            )
        }
        Text(
            text = "${currentLength}/${maxLength}",
            color = Gray800,
            style = LoveMarkerTheme.typography.label12M,
            modifier = Modifier.padding(top = 4.dp, start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CounterTextFieldPreview() {
    LoveMarkerTheme {
        CounterTextField(
            value = "",
            onValueChanged = {},
            currentLength = 120,
            maxLength = 200,
            height = 150.dp,
            singleLine = false,
            placeholder = "내용을 입력해주세요",
        )
    }
}