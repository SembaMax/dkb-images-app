package com.semba.dkbimages.feature.homescreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.semba.dkbimages.compose.navigation.ScreenDestination
import com.semba.dkbimages.data.model.ImageModel

@Composable
fun HomeScreen(navigateTo: (screenDestination: ScreenDestination, args: Map<String, String>) -> Unit) {

    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize())
    {
        when (uiState)
        {
            is HomeScreenUiState.Error -> ErrorView()
            HomeScreenUiState.Loading -> LoadingView()
            is HomeScreenUiState.Success ->
            {
                HomeContent(imageItems = (uiState as HomeScreenUiState.Success).imageItems,
                    onItemClick = { imageItem -> navigateTo(ScreenDestination.DETAIL, imageItem.toArgs()) })
            }
        }
    }
}

const val CELL_COUNT = 2
@Composable
fun HomeContent(modifier: Modifier = Modifier, gridState: LazyGridState = rememberLazyGridState(), imageItems: List<ImageModel>, onItemClick: (ImageModel) -> Unit)
{
    LazyVerticalGrid(
        modifier = modifier.padding(start = 7.dp, end = 7.dp, bottom = 7.dp, top = 7.dp),
        state = gridState,
        columns = GridCells.Fixed(CELL_COUNT),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(imageItems.size) {
            ImageGridItem(modifier = Modifier.clickable { onItemClick(imageItems[it]) }, item = imageItems[it])
        }
    }
}

@Composable
fun LoadingView() {

}

@Composable
fun ErrorView() {

}