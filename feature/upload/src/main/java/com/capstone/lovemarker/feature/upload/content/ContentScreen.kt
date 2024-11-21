package com.capstone.lovemarker.feature.upload.content

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.lovemarker.core.designsystem.component.textfield.LoveMarkerTextField
import com.capstone.lovemarker.core.designsystem.component.textfield.ReadOnlyTextField
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Brown700
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.Gray500
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White
import com.capstone.lovemarker.feature.upload.R
import com.capstone.lovemarker.feature.upload.UploadViewModel

private const val INPUT_CATEGORY = 4

@Composable
fun ContentRoute(
    navigateUp: () -> Unit,
    viewModel: UploadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    navigateUp: () -> Unit,
    onSearchButtonClick: () -> Unit,
    onDateButtonClick: () -> Unit,
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
        Spacer(modifier = Modifier.padding(top = 14.dp))
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
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
                DateTextField(onDateButtonClick = onDateButtonClick)
            }
            Spacer(modifier = Modifier.padding(top = 24.dp))
            InputSection(
                category = R.string.upload_content_title
            ) {
                TitleTextField()
            }
        }
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
fun DateTextField(
    onDateButtonClick: () -> Unit
) {
    ReadOnlyTextField(
        value = "",
        onTextFieldClick = onDateButtonClick,
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
}

@Composable
fun TitleTextField() {

}

@Composable
fun ContentTextField() {
    TODO("Not yet implemented")
}

@Preview
@Composable
private fun ContentPreview() {
    LoveMarkerTheme {
        ContentScreen(
            navigateUp = {},
            onSearchButtonClick = {},
            onDateButtonClick = {}
        )
    }
}
