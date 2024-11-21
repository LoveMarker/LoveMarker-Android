package com.capstone.lovemarker.core.designsystem.component.textfield

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Brown700
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.Gray500
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.Red200
import com.capstone.lovemarker.core.designsystem.theme.White

@Composable
fun ReadOnlyTextField(
    value: String,
    onTextFieldClick: () -> Unit,
    placeholder: String,
    indicatorColor: Color,
    containerColor: Color,
    placeholderColor: Color,
    trailingIcon: @Composable () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = {}, // 주의: 읽기 전용일 때는 호출되지 않는다.
        readOnly = true,
        placeholder = {
            Text(
                text = placeholder,
                color = placeholderColor,
                style = LoveMarkerTheme.typography.body14M
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = indicatorColor,
            unfocusedIndicatorColor = indicatorColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
        ),
        trailingIcon = { trailingIcon() },
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(value) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        onTextFieldClick()
                    }
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun ReadOnlyTextFieldPreview() {
    LoveMarkerTheme {
        ReadOnlyTextField(
            value = "",
            onTextFieldClick = {},
            placeholder = "YYYY-MM-DD",
            indicatorColor = Gray400,
            containerColor = Color.White,
            placeholderColor = Gray300
        )
    }
}