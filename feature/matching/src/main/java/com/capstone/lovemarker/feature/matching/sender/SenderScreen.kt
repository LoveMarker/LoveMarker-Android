package com.capstone.lovemarker.feature.matching.sender

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.component.dialog.DoubleButtonDialog
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Brown700
import com.capstone.lovemarker.core.designsystem.theme.Gray500
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.matching.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SenderScreen(
    navigateUp: () -> Unit,
) {
    var buttonEnabled by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LoveMarkerTheme.colorScheme.surfaceContainer
    ) {
        Column {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.matching_sender_up_btn_desc)
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 42.dp)
            ) {
                Text(
                    text = stringResource(R.string.matching_sender_anniversary_title),
                    style = LoveMarkerTheme.typography.headline20B,
                )
                Text(
                    text = stringResource(R.string.matching_sender_guide_detail),
                    style = LoveMarkerTheme.typography.label13M,
                    color = LoveMarkerTheme.colorScheme.onSurface700,
                    modifier = Modifier.padding(top = 13.dp)
                )
                Spacer(modifier = Modifier.padding(top = 24.dp))
                DatePickerFieldToModal(
                    onDateSelected = { buttonEnabled = it }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            LoveMarkerButton(
                onClick = { showDialog = true },
                buttonText = stringResource(R.string.matching_sender_complete_btn_text),
                enabled = buttonEnabled,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            )

            if (showDialog) {
                DoubleButtonDialog(
                    title = "상대방에게 코드를 공유해주세요",
                    description = "", // TODO: 기념일 바탕으로, 서버로부터 초대 코드 받기
                    confirmButtonText = "공유",
                    dismissButtonText = "취소",
                    onConfirmButtonClick = { /* TODO: 초대 코드 공유하는 바텀시트 띄우기 */ },
                    onDismissButtonClick = { showDialog = false },
                )
            }
        }
    }
}

@Composable
fun DatePickerFieldToModal(
    onDateSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let {
            onDateSelected(true)
            convertMillisToDate(it)
        } ?: "",
        onValueChange = {}, // 주의: 읽기 전용일 때는 호출되지 않는다.
        readOnly = true,
        placeholder = {
            Text(
                text = stringResource(R.string.matching_sender_input_placeholder),
                color = Gray500
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(R.string.matching_sender_trailing_icon_desc),
                tint = Brown700
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Brown700,
            unfocusedIndicatorColor = Brown700,
            focusedContainerColor = Beige400,
            unfocusedContainerColor = Beige400,
        ),
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = {
                selectedDate = it
             },
            onDismiss = { showModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(
                    text = stringResource(R.string.matching_sender_confirm_btn_text),
                    color = Brown700
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.matching_sender_dismiss_btn_text),
                    color = Brown700
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Beige400
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = Beige400,
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Brown700,
                    unfocusedIndicatorColor = Brown700,
                    focusedContainerColor = Beige400,
                    unfocusedContainerColor = Beige400,
                    cursorColor = Brown700,
                    focusedLabelColor = Brown700
                ),
                todayContentColor = Brown700,
                todayDateBorderColor = Brown700,
                selectedDayContainerColor = Brown700
            )
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
private fun SenderPreview() {
    LoveMarkerTheme {
        SenderScreen(
            navigateUp = {}
        )
    }
}
