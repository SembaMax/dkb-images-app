package com.semba.dkbimages.feature.homescreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.semba.dkbimages.design.navigation.ScreenDestination
import com.semba.dkbimages.data.model.ImageModel
import com.semba.dkbimages.design.component.ErrorView
import com.semba.dkbimages.design.component.LoadingView

@Composable
fun HomeRoute(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel(), navigateTo: (screenDestination: ScreenDestination, args: Map<String, String>) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(uiState = uiState, modifier = modifier, navigateTo = navigateTo)
}

@Composable
fun HomeScreen(uiState: HomeScreenUiState, modifier: Modifier = Modifier, navigateTo: (screenDestination: ScreenDestination, args: Map<String, String>) -> Unit = {_,_->}) {


    Box(modifier = Modifier.fillMaxSize())
    {
        when (uiState)
        {
            is HomeScreenUiState.Error -> ErrorView(modifier = Modifier.align(Alignment.Center))
            HomeScreenUiState.Loading -> LoadingView(modifier = Modifier.align(Alignment.Center))
            is HomeScreenUiState.Success ->
            {
                HomeContent(imageItems = uiState.imageItems,
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
        modifier = modifier.padding(start = 7.dp, end = 7.dp, bottom = 7.dp, top = 7.dp).testTag("images_lazy_grid"),
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