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
import coil.compose.AsyncImage
import com.capstone.lovemarker.core.common.extension.dropShadow
import com.capstone.lovemarker.core.designsystem.theme.Gray200
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.designsystem.theme.White
import com.capstone.lovemarker.domain.myfeed.entity.MemoryEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MyFeedRoute(
    navigateUp: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
) {
    MyFeedScreen(
        navigateUp = navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFeedScreen(
    navigateUp: () -> Unit,
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
                        contentDescription = "navigate up"
                    )
                }
            },
            title = {
                Text(
                    text = "내가 올린 추억",
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
            memories = persistentListOf(),
            onMemoryItemClick = { /* todo: navigate to detail */ }
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
            showErrorSnackbar = {}
        )
    }
}