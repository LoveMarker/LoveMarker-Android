package com.capstone.lovemarker.feature.matching.sender

import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.component.dialog.DoubleButtonDialog
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Brown700
import com.capstone.lovemarker.core.designsystem.theme.Gray500
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.matching.R
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SenderRoute(
    navigateUp: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: SenderViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is SenderSideEffect.ShowShareDialog -> {
                        viewModel.apply {
                            updateInvitationCode(sideEffect.invitationCode)
                            updateDialogState(showDialog = true)
                        }
                    }

                    is SenderSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    SenderScreen(
        navigateUp = navigateUp,
        selectedDate = state.anniversary,
        onDateSelected = {
            viewModel.apply {
                updateAnniversary(it)
                updateButtonEnabled(it.isNotEmpty())
            }
        },
        completeButtonEnabled = state.buttonEnabled,
        onCompleteButtonClick = {
            viewModel.postInvitationCode(state.anniversary)
        },
        invitationCode = state.invitationCode,
        showDialog = state.showDialog,
        onShareButtonClick = {
            viewModel.updateDialogState(showDialog = false)
            showShareSheet(context = context, text = state.invitationCode)
        },
        onDismissButtonClick = {
            viewModel.updateDialogState(showDialog = false)
        }
    )
}

private fun showShareSheet(context: Context, text: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(context, shareIntent, null)
}

@Composable
fun SenderScreen(
    navigateUp: () -> Unit,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    completeButtonEnabled: Boolean,
    onCompleteButtonClick: () -> Unit,
    invitationCode: String,
    showDialog: Boolean,
    onShareButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
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
                    selectedDate = selectedDate,
                    onDateSelected = onDateSelected
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            LoveMarkerButton(
                onClick = onCompleteButtonClick,
                buttonText = stringResource(R.string.matching_complete_btn_text),
                enabled = completeButtonEnabled,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            )

            if (showDialog) {
                DoubleButtonDialog(
                    title = stringResource(R.string.matching_sender_share_dialog_title),
                    description = invitationCode,
                    confirmButtonText = stringResource(R.string.matching_sender_share_dialog_confirm_text),
                    dismissButtonText = stringResource(R.string.matching_sender_share_dialog_cancel_text),
                    onConfirmButtonClick = onShareButtonClick,
                    onDismissButtonClick = onDismissButtonClick,
                )
            }
        }
    }
}

@Composable
fun DatePickerFieldToModal(
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate,
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
            onDateSelected = { date ->
                onDateSelected(date?.let(::convertMillisToDate).orEmpty())
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
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
private fun SenderPreview() {
    LoveMarkerTheme {

    }
}

