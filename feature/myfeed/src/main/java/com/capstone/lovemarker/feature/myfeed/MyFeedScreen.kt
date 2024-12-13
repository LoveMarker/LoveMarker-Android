package com.capstone.lovemarker.feature.myfeed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.capstone.lovemarker.core.common.extension.dropShadow
import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.core.designsystem.component.progressbar.LoadingProgressBar
import com.capstone.lovemarker.core.designsystem.theme.Gray200
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White
import com.capstone.lovemarker.domain.myfeed.entity.MemoryEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyFeedRoute(
    navigateUp: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: MyFeedViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val memories = viewModel.memories.collectAsLazyPagingItems()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is MyFeedSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    when (val refreshState = memories.loadState.refresh) {
        is LoadState.Loading -> {
            viewModel.updateUiState(UiState.Loading)
        }

        is LoadState.Error -> {
            showErrorSnackbar(refreshState.error)
        }

        is LoadState.NotLoading -> {
            viewModel.updateUiState(UiState.Success(Unit))
        }
    }

    when (state.uiState) {
        is UiState.Loading -> {
            LoadingProgressBar()
        }

        is UiState.Success -> {
            MyFeedScreen(
                memories = memories.itemSnapshotList.items.toPersistentList(),
                navigateUp = navigateUp,
                navigateToDetail = { memoryId ->
                    navigateToDetail(memoryId)
                }
            )
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFeedScreen(
    memories: PersistentList<MemoryEntity>,
    navigateUp: () -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.myfeed_back_btn_desc)
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.myfeed_top_app_bar_title),
                    style = LoveMarkerTheme.typography.body16B,
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            )
        )
        HorizontalDivider(
            color = Gray200,
            thickness = 1.dp
        )
        MyFeedItems(
            memories = memories,
            onMemoryItemClick = navigateToDetail
        )
    }
}

@Composable
fun MyFeedItems(
    memories: PersistentList<MemoryEntity>,
    onMemoryItemClick: (Int) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            key = { memory -> memory.memoryId },
            items = memories,
        ) { memory ->
            MemoryItem(
                item = memory,
                onItemClick = onMemoryItemClick
            )
        }
    }
}

@Composable
fun MemoryItem(
    item: MemoryEntity,
    onItemClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(6.dp),
                offsetY = 2.dp,
                blur = 2.dp
            )
            .clip(RoundedCornerShape(6.dp))
            .background(White)
            .clickable {
                onItemClick(item.memoryId)
            }
    ) {
        Spacer(modifier = Modifier.padding(start = 16.dp))
        AsyncImage(
            model = item.imageUrl,
            contentDescription = stringResource(
                id = com.capstone.lovemarker.core.designsystem.R.string.memory_item_image_desc
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(6.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 14.dp, bottom = 14.dp, end = 16.dp)
        ) {
            Text(
                text = item.title,
                style = LoveMarkerTheme.typography.body16B,
            )
            Text(
                text = item.address,
                style = LoveMarkerTheme.typography.body14M,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = item.date,
                style = LoveMarkerTheme.typography.label13M,
                modifier = Modifier
                    .align(Alignment.End)
            )
        }
    }
}

@Composable
fun EmptyArchiveResult() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(
                id = com.capstone.lovemarker.core.designsystem.R.drawable.img_empty_memory
            ),
            contentDescription = stringResource(
                id = com.capstone.lovemarker.core.designsystem.R.string.empty_memory_image_desc
            ),
        )
        Text(
            text = stringResource(
                id = com.capstone.lovemarker.core.designsystem.R.string.empty_memory_guide_text
            ),
            style = LoveMarkerTheme.typography.body15M,
            color = Gray700,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 72.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyFeedPreview() {
    LoveMarkerTheme {
        MyFeedRoute(
            navigateUp = {},
            navigateToDetail = {},
            showErrorSnackbar = {}
        )
    }
}