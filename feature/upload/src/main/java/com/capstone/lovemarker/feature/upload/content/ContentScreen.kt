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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.capstone.lovemarker.core.common.extension.clearFocus
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.component.datepicker.DatePickerModal
import com.capstone.lovemarker.core.designsystem.component.textfield.CounterTextField
import com.capstone.lovemarker.core.designsystem.component.textfield.ReadOnlyTextField
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White
import com.capstone.lovemarker.feature.upload.R
import com.capstone.lovemarker.feature.upload.UploadSideEffect
import com.capstone.lovemarker.feature.upload.UploadViewModel
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ContentRoute(
    navigateUp: () -> Unit,
    navigateToPlaceSearch: () -> Unit,
    navigateToMap: (Int) -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
//    searchPlace: SearchPlace? = null,
    viewModel: UploadViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

//    LaunchedEffect(searchPlace) {
//        if (searchPlace != null) {
//            viewModel.updatePlace(
//                address = searchPlace.address,
//                latLng = Pair(searchPlace.latitude, searchPlace.longitude)
//            )
//        }
//    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is UploadSideEffect.NavigateToMap -> {
                         navigateToMap(sideEffect.memoryId)
                    }

                    is UploadSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    ContentScreen(
        address = state.address,
        selectedDate = state.date,
        title = state.title,
        content = state.content,
        completeButtonEnabled = state.completeButtonEnabled,
        scrollState = scrollState,
        navigateUp = navigateUp,
        onDateSelected = viewModel::updateDate,
        onTitleChanged = viewModel::updateTitle,
        onContentChanged = viewModel::updateContent,
        onSearchButtonClick = navigateToPlaceSearch,
        onCompleteButtonClick = viewModel::postMemory
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    address: String,
    selectedDate: String,
    title: String,
    content: String,
    completeButtonEnabled: Boolean,
    scrollState: ScrollState,
    navigateUp: () -> Unit,
    onDateSelected: (String) -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onSearchButtonClick: () -> Unit,
    onCompleteButtonClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .clearFocus(focusManager)
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
                PlaceTextField(
                    address = address,
                    onSearchButtonClick = onSearchButtonClick
                )
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
                TitleTextField(
                    title = title,
                    onTitleChanged = onTitleChanged
                )
            }
            Spacer(modifier = Modifier.padding(top = 24.dp))
            InputSection(
                category = R.string.upload_content_detail
            ) {
                ContentTextField(
                    content = content,
                    onContentChanged = onContentChanged
                )
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
    address: String,
    onSearchButtonClick: () -> Unit
) {
    ReadOnlyTextField(
        value = address,
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
fun TitleTextField(
    title: String,
    onTitleChanged: (String) -> Unit,
) {
    CounterTextField(
        value = title,
        onValueChanged = onTitleChanged,
        currentLength = title.length,
        singleLine = true,
        height = 54.dp,
        maxLength = 20,
        placeholder = stringResource(id = R.string.upload_content_title_placeholder),
    )
}

@Composable
fun ContentTextField(
    content: String,
    onContentChanged: (String) -> Unit,
) {
    CounterTextField(
        value = content,
        onValueChanged = onContentChanged,
        currentLength = content.length,
        maxLength = 200,
        height = 180.dp,
        placeholder = stringResource(id = R.string.upload_content_detail_placeholder),
    )
}

@Preview
@Composable
private fun ContentPreview() {
    LoveMarkerTheme {
        val scrollState = rememberScrollState()
        ContentScreen(
            address = "",
            selectedDate = "2023-10-14",
            title = "제목",
            content = "내용",
            scrollState = scrollState,
            navigateUp = {},
            onDateSelected = {},
            onTitleChanged = {},
            onContentChanged = {},
            completeButtonEnabled = false,
            onSearchButtonClick = {},
            onCompleteButtonClick = {}
        )
    }
}
