package com.capstone.lovemarker.feature.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import com.capstone.lovemarker.core.designsystem.theme.Gray100
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailRoute(
    memoryId: Int,
    navigateUp: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.getDetailInfo(memoryId)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is DetailSideEffect.ShowErrorSnackbar -> {
                        showErrorSnackbar(sideEffect.throwable)
                    }
                }
            }
    }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DetailTopAppBar(
                onBackButtonClick = navigateUp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(scrollState)
            ) {
                HorizontalDivider(color = Gray100, thickness = 2.dp)
                DetailHeader(
                    title = state.detailModel.title,
                    writer = state.detailModel.writer
                )
                ImageSection(
                    imageUrls = state.detailModel.images
                )
                DetailFooter(
                    address = state.detailModel.address,
                    date = state.detailModel.date
                )
                HorizontalDivider(color = Gray100, thickness = 6.dp)
                ContentSection(
                    content = state.detailModel.content
                )
            }
        }
    }
}

@Composable
fun DetailTopAppBar(
    onBackButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp)
                .clickable {
                    onBackButtonClick()
                }
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back button icon",
            )
        }
    }
}

@Composable
fun DetailHeader(
    title: String,
    writer: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = LoveMarkerTheme.typography.body16B,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_detail_more),
                contentDescription = "more button icon",
                tint = Gray300
            )
        }
        Text(
            text = writer,
            style = LoveMarkerTheme.typography.body14M,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

@Composable
fun ImageSection(
    imageUrls: List<String>
) {
    val pagerState = rememberPagerState(pageCount = {
        imageUrls.size
    })

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) { pageIdx ->
            AsyncImage(
                model = imageUrls[pageIdx],
                contentDescription = "detail feed image"
            )
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Gray700 else Gray300
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(4.dp)
                )
            }
        }
    }
}

@Composable
fun DetailFooter(
    address: String,
    date: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = address,
            style = LoveMarkerTheme.typography.label13M
        )
        Text(
            text = date,
            style = LoveMarkerTheme.typography.label13M,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun ContentSection(
    content: String
) {
    Text(
        text = content,
        style = LoveMarkerTheme.typography.label13M,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    LoveMarkerTheme {
        DetailRoute(
            memoryId = 1,
            navigateUp = {},
            showErrorSnackbar = {}
        )
    }
}
