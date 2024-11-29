package com.capstone.lovemarker.feature.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
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
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.capstone.lovemarker.core.designsystem.theme.Gray100
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray700
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailRoute(
    navigateUp: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val imageUrls = listOf("", "", "")
    val fakeContent =
        "인생은 끊임없는 변화와 도전의 연속입니다. 우리는 매일 새로운 경험을 통해 성장하고, 때로는 어려움에 부딪히기도 합니다. 이러한 순간들은 우리에게 중요한 교훈을 주며, 더 나은 사람으로 발전할 수 있는 기회를 제공합니다.\n" +
                "\n" +
                "예를 들어, 실패는 종종 두려움의 원천이지만, 그 속에는 배움의 씨앗이 숨겨져 있습니다. 실패를 통해 우리는 자신의 한계를 인식하고, 더 나아가 목표를 향해 나아갈 수 있는 원동력을 얻습니다.\n" +
                "\n" +
                "또한, 주변 사람들과의 관계도 인생의 중요한 부분입니다. 친구, 가족, 동료와의 소통은 우리의 정서적 안정을 돕고, 서로의 지지를 통해 어려움을 극복할 수 있게 합니다.\n" +
                "\n" +
                "결국, 인생은 단순한 여정이 아니라, 우리가 만드는 이야기입니다. 매 순간을 소중히 여기고, 긍정적인 마음으로 임한다면, 어떤 어려움도 극복할 수 있을 것입니다. 인생의 아름다움을 발견하고, 그 속에서 진정한 행복을 찾아가는 것이 중요합니다."

    val pagerState = rememberPagerState(pageCount = {
        imageUrls.size
    })
    val scrollState = rememberScrollState()

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DetailTopAppBar()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(scrollState)
            ) {
                HorizontalDivider(color = Gray100, thickness = 2.dp)
                DetailHeader(
                    title = "제목",
                    author = "작성자"
                )
                ImageSection(
                    pagerState = pagerState,
                    imageUrls = imageUrls
                )
                DetailFooter(
                    address = "경상북도 경주시 인왕동 517",
                    date = "2024-03-24"
                )
                HorizontalDivider(color = Gray100, thickness = 6.dp)
                ContentSection(
                    description = fakeContent
                )
            }
        }
    }
}

@Composable
fun DetailTopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp),
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
    author: String,
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
            text = author,
            style = LoveMarkerTheme.typography.body14M,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

@Composable
fun ImageSection(
    pagerState: PagerState,
    imageUrls: List<String>
) {
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
    description: String
) {
    Text(
        text = description,
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
            navigateUp = {}
        )
    }
}
