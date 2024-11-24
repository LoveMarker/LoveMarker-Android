package com.capstone.lovemarker.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.lovemarker.core.designsystem.component.textfield.SearchTextField
import com.capstone.lovemarker.core.designsystem.theme.Gray100
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme

@Composable
fun SearchRoute(
    navigateUp: () -> Unit,
    viewModel: SearchViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        keyword = state.keyword,
        onKeywordChanged = viewModel::updateSearchKeyword,
        navigateUp = navigateUp,
        onClearButtonClick = {
            viewModel.updateSearchKeyword("")
        },
        onSearchActionDone = {
            // todo: 뷰모델에서 장소 검색
        },
        onSearchItemClick = { place ->
            // todo: 이전 화면의 텍스트 필드에 주소 반영
            //  place.address
        }
    )
}

@Composable
fun SearchScreen(
    keyword: String,
    onKeywordChanged: (String) -> Unit,
    navigateUp: () -> Unit,
    onClearButtonClick: () -> Unit,
    onSearchActionDone: () -> Unit,
    onSearchItemClick: (Place) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        SearchTextField(
            value = keyword,
            onValueChanged = onKeywordChanged,
            onBackButtonClick = navigateUp,
            onClearButtonClick = onClearButtonClick,
            onSearchActionDone = onSearchActionDone
        )
        HorizontalDivider(
            thickness = 6.dp,
            color = Gray100
        )
        if (places.isEmpty()) {
            EmptySearchResult()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(places) { place ->
                    SearchItem(
                        place = place,
                        onItemClick = onSearchItemClick
                    )
                }
            }
        }
    }
}

@Composable
fun SearchItem(
    place: Place,
    onItemClick: (Place) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(place)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = stringResource(R.string.upload_place_search_location_icon_desc),
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 16.dp,
                    top = 26.dp,
                    bottom = 26.dp
                ),
                tint = Gray300,
            )
            Column {
                Text(
                    text = place.name,
                    style = LoveMarkerTheme.typography.body15M,
                )
                Text(
                    text = place.address,
                    modifier = Modifier.padding(top = 6.dp),
                    style = LoveMarkerTheme.typography.label13M,
                    color = Gray400,
                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Gray100
        )
    }
}

@Composable
fun EmptySearchResult(
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_place_search_nothing),
            contentDescription = stringResource(R.string.upload_place_search_warning_icon_desc),
            tint = Gray300
        )
        Text(
            text = stringResource(R.string.upload_place_search_nothing_text),
            style = LoveMarkerTheme.typography.body14M,
            color = Gray300,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 22.dp)
        )
    }
}

val places = listOf(
    Place("암사역 8호선", "서울특별시 강동구 올림픽로 776 암사역"),
    Place("암사역 8호선", "서울특별시 강동구 올림픽로 776 암사역"),
    Place("암사역 8호선", "서울특별시 강동구 올림픽로 776 암사역"),
    Place("암사역 8호선", "서울특별시 강동구 올림픽로 776 암사역"),
    Place("암사역 8호선", "서울특별시 강동구 올림픽로 776 암사역"),
)

data class Place(
    val name: String,
    val address: String
)

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    LoveMarkerTheme {
        SearchScreen(
            keyword = "암사역",
            onKeywordChanged = {},
            navigateUp = {},
            onClearButtonClick = {},
            onSearchItemClick = {},
            onSearchActionDone = {}
        )
    }
}