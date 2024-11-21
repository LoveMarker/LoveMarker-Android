package com.capstone.lovemarker.feature.upload.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.lovemarker.feature.upload.UploadViewModel
import timber.log.Timber

@Composable
fun ContentRoute(
    navigateUp: () -> Unit,
    viewModel: UploadViewModel = hiltViewModel()
) {
    Timber.d("viewModel: ${viewModel.hashCode()}")

    val state by viewModel.state.collectAsStateWithLifecycle()
    Timber.d("images: ${state.images}")
}

@Composable
fun ContentScreen(

) {

}
