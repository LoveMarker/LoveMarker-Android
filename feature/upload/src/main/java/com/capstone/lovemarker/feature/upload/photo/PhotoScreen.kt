package com.capstone.lovemarker.feature.upload.photo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.capstone.lovemarker.core.designsystem.component.button.LoveMarkerButton
import com.capstone.lovemarker.core.designsystem.theme.Beige400
import com.capstone.lovemarker.core.designsystem.theme.Gray800
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.feature.upload.R
import kotlinx.collections.immutable.PersistentList
import timber.log.Timber

private const val MAX_IMAGES_LIMIT = 5

@Composable
fun PhotoRoute(
    navigateUp: () -> Unit,
    navigateToContent: (PersistentList<Uri>) -> Unit,
    viewModel: PhotoViewModel = viewModel()
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
        imageUris = state.imageUris,
        photoSelected = state.imageUris.isNotEmpty(),
        onAddButtonClick = {
            pickMultipleMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onNextButtonClick = {
            navigateToContent(state.imageUris)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(
    navigateUp: () -> Unit,
    imageUris: PersistentList<Uri>,
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
                text = "\n0/5",
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
                imageUris = imageUris
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
    imageUris: PersistentList<Uri>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(24.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(imageUris) { imageUri ->
            SelectedImageItem(
                imageUri = imageUri
            )
        }
    }
}

@Composable
fun SelectedImageItem(imageUri: Uri) {
    AsyncImage(
        model = imageUri,
        contentDescription = stringResource(R.string.upload_photo_selected_image_desc),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoPreview() {
    LoveMarkerTheme {

    }
}
