package com.capstone.lovemarker.feature.search

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.core.designsystem.component.progressbar.LoadingProgressBar
import com.capstone.lovemarker.core.designsystem.component.textfield.SearchTextField
import com.capstone.lovemarker.core.designsystem.theme.Gray100
import com.capstone.lovemarker.core.designsystem.theme.Gray200
import com.capstone.lovemarker.core.designsystem.theme.Gray300
import com.capstone.lovemarker.core.designsystem.theme.Gray400
import com.capstone.lovemarker.core.designsystem.theme.LoveMarkerTheme
import com.capstone.lovemarker.core.model.SearchPlace
import com.capstone.lovemarker.domain.search.service.PlaceSearchService
import com.capstone.lovemarker.feature.search.di.PlaceSearchEntryPoint
import com.capstone.lovemarker.feature.search.model.PlaceSearchModel
import dagger.hilt.android.EntryPointAccessors
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun SearchRoute(
    navigateUp: () -> Unit,
    navigateToContent: (SearchPlace) -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val placeSearchService = rememberPlaceSearchService() ?: return

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is SearchSideEffect.NavigateToContent -> {
                        navigateToContent(
                            sideEffect.place.toCoreModel()
                        )
                    }
                }
            }
    }

    SearchScreen(
        keyword = state.keyword,
        uiState = state.uiState,
        onKeywordChanged = viewModel::updateSearchKeyword,
        navigateUp = navigateUp,
        onClearButtonClick = {
            viewModel.updateSearchKeyword("")
        },
        onSearchActionDone = {
            if (state.keyword.isBlank()) return@SearchScreen
            lifecycleOwner.lifecycleScope.launch {
                runCatching {
                    viewModel.updateUiState(UiState.Loading)
                    placeSearchService.getSearchPlaces(state.keyword)
                }.onSuccess { places ->
                    viewModel.updateUiState(UiState.Success(
                        places.map {
                            PlaceSearchModel(
                                name = it.name,
                                address = it.address,
                                latitude = it.latitude,
                                longitude = it.longitude
                            )
                        }.toPersistentList()
                    ))
                }.onFailure {
                    showErrorSnackbar(it)
                }
            }
        },
        onSearchItemClick = { place ->
            viewModel.triggerContentNavigationEffect(place)
        }
    )
}

@Composable
private fun rememberPlaceSearchService(): PlaceSearchService? {
    val activityContext = LocalContext.current as? ComponentActivity

    return runCatching {
        requireNotNull(activityContext) { "Activity context is required for getting PlaceSearchService" }
        val entryPoint = EntryPointAccessors.fromActivity<PlaceSearchEntryPoint>(activityContext)
        entryPoint.placeSearchService()
    }.getOrElse { throwable ->
        Timber.e(throwable.message)
        null
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    keyword: String,
    uiState: UiState<PersistentList<PlaceSearchModel>>,
    onKeywordChanged: (String) -> Unit,
    navigateUp: () -> Unit,
    onClearButtonClick: () -> Unit,
    onSearchActionDone: () -> Unit,
    onSearchItemClick: (PlaceSearchModel) -> Unit,
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

        when (uiState) {
            is UiState.Loading -> {
                LoadingProgressBar()
            }

            is UiState.Success -> {
                val places = uiState.data
                if (places.isEmpty()) {
                    EmptySearchResult()
                } else {
                    CompositionLocalProvider(
                        LocalOverscrollConfiguration provides null
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
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

            else -> {}
        }
    }
}

@Composable
fun SearchItem(
    place: PlaceSearchModel,
    onItemClick: (PlaceSearchModel) -> Unit,
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
fun EmptySearchResult() {
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

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    LoveMarkerTheme {
        SearchScreen(
            keyword = "암사역",
            uiState = UiState.Empty,
            onKeywordChanged = {},
            navigateUp = {},
            onClearButtonClick = {},
            onSearchItemClick = {},
            onSearchActionDone = {}
        )
    }
}