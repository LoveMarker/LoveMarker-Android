package com.capstone.lovemarker

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions.getExtensionVersion
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val singlePicker =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri -> }
    private val multiplePicker =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(MAX_ITEMS)) { uris ->
            if (uris.isNotEmpty()) {
                viewModel.fetchPhotos(uris)
            }
        }
    private val imageContentLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            if (viewModel.photoUris.value.isEmpty()) {
                HomeScreen {
                    if (isPhotoPickerAvailable()) {
                        launchMultiplePhotoPicker()
                    } else {
                        launchGallery()
                    }
                }
            } else {
                PhotoScreen(photoUris = viewModel.photoUris.value)
            }
        }
    }

    private fun isPhotoPickerAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getExtensionVersion(Build.VERSION_CODES.R) >= 2
        } else {
            false
        }
    }

    private fun launchSinglePhotoPicker() {
        singlePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun launchMultiplePhotoPicker() {
        multiplePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun launchGallery() {
        imageContentLauncher.launch("image/*")
    }

    companion object {
        private const val MAX_ITEMS = 3
    }
}

@Composable
fun HomeScreen(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("이미지 고르기")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoScreen(
    photoUris: List<Uri>,
) {
    val pagerState = rememberPagerState(pageCount = { photoUris.size })

    Column(Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1F)
                .padding(16.dp)
                .fillMaxSize()
        ) { pageIndex ->
            Card() {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = photoUris[pageIndex]
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}
