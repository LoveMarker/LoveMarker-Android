package com.capstone.lovemarker.feature.upload.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.lovemarker.feature.upload.UploadViewModel

@Composable
fun ContentRoute(
    navigateUp: () -> Unit,
    viewModel: UploadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
}

@Composable
fun ContentScreen(

) {

}
