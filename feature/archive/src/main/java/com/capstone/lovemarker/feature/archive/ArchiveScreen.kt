package com.capstone.lovemarker.feature.archive

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.collectAsLazyPagingItems
import com.capstone.lovemarker.core.common.extension.dropShadow
import com.capstone.lovemarker.core.designsystem.theme.Gray200
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White
import com.capstone.lovemarker.domain.archive.entity.MemoryEntity
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun ArchiveRoute(
    innerPadding: PaddingValues,
    navigateToDetail: (Int) -> Unit, // todo: 사이드 이펙트로 처리 안하고, 컴포저블 콜백에서 직접 호출하면... 이전 화면이 그대로 남아있는 건가?????
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val memories = viewModel.memories.collectAsLazyPagingItems()
    Timber.d("memories: ${memories.itemSnapshotList}")

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is ArchiveSideEffect.NavigateToDetail -> {

                    }

                    is ArchiveSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    ArchiveScreen(
        innerPadding = innerPadding,
        memories = memories.itemSnapshotList,
        onMemoryItemClick = { memoryId ->
            Timber.d("item click: $memoryId")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    innerPadding: PaddingValues,
    memories: ItemSnapshotList<MemoryEntity>,
    onMemoryItemClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(innerPadding)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = com.capstone.lovemarker.core.designsystem.R.string.app_name),
                    fontFamily = FontFamily(Font(resId = com.capstone.lovemarker.core.designsystem.R.font.ribeye_regular)),
                    fontSize = 16.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = White
            )
        )
        HorizontalDivider(
            color = Gray200
        )
        if (memories.isEmpty()) {
            EmptyArchiveResult()
        } else {
            ArchiveItems(
                memories = memories,
                onMemoryItemClick = onMemoryItemClick
            )
        }
    }
}

@Composable
fun ArchiveItems(
    memories: ItemSnapshotList<MemoryEntity>,
    onMemoryItemClick: (Int) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(memories) { memory ->
            if (memory != null) {
                MemoryItem(
                    item = memory,
                    onItemClick = onMemoryItemClick
                )
            }
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
        Image(
            painter = painterResource(R.drawable.img_test),
            contentDescription = null,
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
        Icon(
            painter = painterResource(id = R.drawable.img_archive_empty),
            contentDescription = stringResource(R.string.archive_empty_image_desc),
            tint = Color.Unspecified
        )
        Text(
            text = stringResource(R.string.archive_empty_guide_text),
            style = LoveMarkerTheme.typography.body14M,
            color = Gray700,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 72.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArchivePreview() {
    LoveMarkerTheme {

    }
}
