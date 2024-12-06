package com.capstone.lovemarker.feature.upload.photo

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.upload.R
import com.capstone.lovemarker.feature.upload.UploadViewModel
import kotlinx.collections.immutable.PersistentList
import timber.log.Timber

private const val MAX_IMAGES_LIMIT = 5

@Composable
fun PhotoRoute(
    navigateUp: () -> Unit,
    navigateToContent: () -> Unit,
    viewModel: UploadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val pickMultipleMedia =
        rememberLauncherForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGES_LIMIT)
        ) { uris ->
            if (uris.isNotEmpty()) {
                viewModel.updateImages(uris)
            } else {
                Timber.d("No media selected")
            }
        }

    PhotoScreen(
        navigateUp = navigateUp,
        images = state.images,
        photoSelected = state.images.isNotEmpty(),
        onAddButtonClick = {
            pickMultipleMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onNextButtonClick = {
            navigateToContent()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(
    navigateUp: () -> Unit,
    images: PersistentList<String>,
    photoSelected: Boolean,
    onAddButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
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
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.upload_photo_guide_text),
                style = LoveMarkerTheme.typography.label13M,
                color = Gray800
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${images.size}/${MAX_IMAGES_LIMIT}",
                style = LoveMarkerTheme.typography.label13M,
                color = Gray800,
            )
        }
        Spacer(modifier = Modifier.padding(top = 18.dp))
        Row {
            Spacer(modifier = Modifier.padding(start = 24.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Beige400)
                    .weight(1f)
                    .clickable {
                        onAddButtonClick()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_photo),
                    contentDescription = stringResource(R.string.upload_photo_btn_desc),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.padding(end = 24.dp))
        }

        if (photoSelected) {
            SelectedImageGrid(
                images = images
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        LoveMarkerButton(
            onClick = onNextButtonClick,
            buttonText = stringResource(R.string.upload_photo_next_btn_text),
            enabled = photoSelected,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )
    }
}

@Composable
fun SelectedImageGrid(
    images: PersistentList<String>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(24.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(images) { imageUrl ->
            SelectedImageItem(
                imageUrl = imageUrl
            )
        }
    }
}

@Composable
fun SelectedImageItem(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.upload_photo_selected_image_desc),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoPreview() {
    LoveMarkerTheme {

    }
}
