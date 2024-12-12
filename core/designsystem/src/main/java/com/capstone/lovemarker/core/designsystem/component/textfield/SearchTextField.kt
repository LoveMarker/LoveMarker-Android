package com.capstone.lovemarker.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.designsystem.R
import com.capstone.lovemarker.core.designsystem.theme.Black
import com.capstone.lovemarker.core.designsystem.theme.Brown600
import com.capstone.lovemarker.core.designsystem.theme.Brown700
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White

@Composable
fun SearchTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    onClearButtonClick: () -> Unit,
    onSearchActionDone: () -> Unit,
    placeholder: String = "장소 검색",
) {
    val focusManager = LocalFocusManager.current
    val textSelectionColors = TextSelectionColors(
        handleColor = Brown700,
        backgroundColor = Brown600.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        BasicTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(color = White),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    onSearchActionDone()
                }
            ),
            textStyle = LoveMarkerTheme.typography.body15M,
            cursorBrush = SolidColor(value = Brown700),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    LeadingIcon(
                        onBackButtonClick = onBackButtonClick
                    )
                    Spacer(modifier = Modifier.padding(start = 10.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = LoveMarkerTheme.typography.body15M,
                                color = Gray300
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(start = 10.dp))
                    if (value.isNotEmpty()) {
                        TrailingIcon(
                            onClearButtonClick = onClearButtonClick
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun LeadingIcon(
    onBackButtonClick: () -> Unit,
) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "navigate up",
        modifier = Modifier.clickable {
            onBackButtonClick()
        },
        tint = Black
    )
}

@Composable
fun TrailingIcon(
    onClearButtonClick: () -> Unit,
) {
    Icon(
        painter = painterResource(
            id = R.drawable.ic_clear_filled_20
        ),
        contentDescription = "clear",
        modifier = Modifier.clickable {
            onClearButtonClick()
        },
        tint = Gray300
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    LoveMarkerTheme {
        SearchTextField(
            value = "카페",
            onValueChanged = {},
            onBackButtonClick = {},
            onClearButtonClick = {},
            onSearchActionDone = {}
        )
    }
}
