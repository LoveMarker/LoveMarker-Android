package com.capstone.lovemarker.core.designsystem.component.datepicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Brown700

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
                    text = "완료",
                    color = Brown700
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "취소",
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
