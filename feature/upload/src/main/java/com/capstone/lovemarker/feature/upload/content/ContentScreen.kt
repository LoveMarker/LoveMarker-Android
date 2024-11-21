package com.capstone.lovemarker.feature.upload.content

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.component.datepicker.DatePickerModal
import com.capstone.lovemarker.core.designsystem.component.textfield.CounterTextField
import com.capstone.lovemarker.core.designsystem.component.textfield.ReadOnlyTextField
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Brown700
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.Gray500
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White
import com.capstone.lovemarker.feature.upload.R
import com.capstone.lovemarker.feature.upload.UploadViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ContentRoute(
    navigateUp: () -> Unit,
    navigateToPlaceSearch: () -> Unit,
    viewModel: UploadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    ContentScreen(
        navigateUp = navigateUp,
        scrollState = scrollState,
        selectedDate = state.date,
        onDateSelected = {
            viewModel.updateDate(it)
        },
        completeButtonEnabled = false,
        onSearchButtonClick = navigateToPlaceSearch,
        onCompleteButtonClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    navigateUp: () -> Unit,
    scrollState: ScrollState,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    completeButtonEnabled: Boolean,
    onSearchButtonClick: () -> Unit,
    onCompleteButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.upload_navigate_up_btn_desc)
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.upload_title),
                    style = LoveMarkerTheme.typography.body16B,
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            )
        )
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 14.dp)
                .verticalScroll(scrollState)
        ) {
            InputSection(
                category = R.string.upload_content_place
            ) {
                PlaceTextField(onSearchButtonClick = onSearchButtonClick)
            }
            Spacer(modifier = Modifier.padding(top = 24.dp))
            InputSection(
                category = R.string.upload_content_date
            ) {
                DatePickerFieldToModal(
                    selectedDate = selectedDate,
                    onDateSelected = onDateSelected
                )
            }
            Spacer(modifier = Modifier.padding(top = 24.dp))
            InputSection(
                category = R.string.upload_content_title
            ) {
                TitleTextField()
            }
            Spacer(modifier = Modifier.padding(top = 24.dp))
            InputSection(
                category = R.string.upload_content_detail
            ) {
                ContentTextField()
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        LoveMarkerButton(
            onClick = onCompleteButtonClick,
            buttonText = stringResource(R.string.upload_content_complete_btn_text),
            enabled = completeButtonEnabled,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )
    }
}

@Composable
fun InputSection(
    @StringRes category: Int,
    content: @Composable () -> Unit,
) {
    Column {
        Text(
            text = stringResource(id = category),
            style = LoveMarkerTheme.typography.body14B,
            color = Gray800
        )
        Spacer(modifier = Modifier.padding(top = 14.dp))
        content()
    }
}

@Composable
fun PlaceTextField(
    onSearchButtonClick: () -> Unit
) {
    ReadOnlyTextField(
        value = "",
        onTextFieldClick = onSearchButtonClick,
        placeholder = stringResource(R.string.upload_content_place_placeholder),
        placeholderColor = Gray300,
        indicatorColor = Gray400,
        containerColor = White,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.upload_content_search_icon_desc),
                tint = Gray400
            )
        }
    )
}

@Composable
fun DatePickerFieldToModal(
    selectedDate: String,
    onDateSelected: (String) -> Unit,
) {
    var showModal by remember { mutableStateOf(false) }

    ReadOnlyTextField(
        value = selectedDate,
        onTextFieldClick = {
            showModal = true
        },
        placeholder = stringResource(R.string.upload_content_date_placeholder),
        placeholderColor = Gray300,
        indicatorColor = Gray400,
        containerColor = White,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(R.string.upload_content_date_icon_desc),
                tint = Gray400
            )
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

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun TitleTextField() {
    CounterTextField(
        value = "",
        onValueChanged = {},
        singleLine = true,
        minHeight = 54.dp,
        currentLength = 0, // todo: 현재 글자 수 반영
        maxLength = 20,
        placeholder = stringResource(id = R.string.upload_content_title_placeholder),
    )
}

@Composable
fun ContentTextField() {
    CounterTextField(
        value = "",
        onValueChanged = {},
        singleLine = false,
        currentLength = 0, // todo: 현재 글자 수 반영
        maxLength = 200,
        placeholder = stringResource(id = R.string.upload_content_detail_placeholder),
        minHeight = 150.dp,
    )
}

@Preview
@Composable
private fun ContentPreview() {
    LoveMarkerTheme {
        val scrollState = rememberScrollState()
        ContentScreen(
            scrollState = scrollState,
            selectedDate = "2023-10-14",
            onDateSelected = {},
            navigateUp = {},
            completeButtonEnabled = false,
            onSearchButtonClick = {},
            onCompleteButtonClick = {}
        )
    }
}
