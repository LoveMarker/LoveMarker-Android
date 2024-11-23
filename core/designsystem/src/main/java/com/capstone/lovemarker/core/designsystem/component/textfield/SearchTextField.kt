package com.capstone.lovemarker.core.designsystem.component.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
    val textSelectionColors = TextSelectionColors(
        handleColor = Brown700,
        backgroundColor = Brown600.copy(alpha = 0.4f)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    style = LoveMarkerTheme.typography.body15M,
                    color = Gray300
                )
            },
            textStyle = LoveMarkerTheme.typography.body15M,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Black,
                unfocusedTextColor = Black,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchActionDone()
                }
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "navigate up",
                    modifier = Modifier.clickable {
                        onBackButtonClick()
                    },
                    tint = Black
                )
            },
            trailingIcon = {
                if (value.isNotEmpty()) {
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
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    LoveMarkerTheme {
        SearchTextField(
            value = "서울시 중구 만리재로",
            onValueChanged = {},
            onBackButtonClick = {},
            onClearButtonClick = {},
            onSearchActionDone = {}
        )
    }
}
