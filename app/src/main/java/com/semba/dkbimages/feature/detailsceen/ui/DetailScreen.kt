package com.semba.dkbimages.feature.detailsceen.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.semba.dkbimages.R
import com.semba.dkbimages.design.component.ErrorView
import com.semba.dkbimages.design.component.LoadingView
import com.semba.dkbimages.data.model.ImageModel

const val DETAIL_BOTTOM_BAR_HEIGHT = 100
const val DETAIL_CARD_CONTENT_PADDING = 5

@Composable
fun DetailScreen(imageId: Int) {
    val viewModel: DetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.loadImageDetail(imageId)
    }

    Box(modifier = Modifier.fillMaxSize())
    {
        when (uiState) {
            is DetailUiState.Error -> ErrorView(modifier = Modifier.align(Alignment.Center))
            DetailUiState.Loading -> LoadingView(modifier = Modifier.align(Alignment.Center))
            is DetailUiState.Success -> { DetailContent(imageItem = (uiState as DetailUiState.Success).imageItem) }
        }
    }
}

@Composable
fun DetailContent(imageItem: ImageModel, showLoading: Boolean = false, showError: Boolean = false) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background))
    {
        Box(modifier = Modifier
            .fillMaxSize()) {
            DetailImageSection(imageItem)
            DetailTopBar()
        }

        if (showLoading)
        {
            CircularProgressIndicator(modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
                .align(Alignment.Center))
        }
    }
}

@Composable
fun DetailTopBar() {
    Box(modifier = Modifier.padding(10.dp)) {
        val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

        IconButton(onClick = { dispatcher?.onBackPressed() }, modifier = Modifier
            .size(35.dp)
            .align(Alignment.CenterStart)) {
            Icon(modifier = Modifier.size(40.dp), painter = painterResource(id = R.drawable.ic_back), contentDescription = "back_button", tint = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun DetailImageSection(imageItem: ImageModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        ImagePreview(imageItem.imageURL)
        BottomDetailsSection(modifier = Modifier.align(Alignment.BottomCenter), imageItem.title)
    }
}

@Composable
fun ImagePreview(imageURL: String) {

    var scale by remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    scale *= zoom
                }
            },
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(1.5f, minOf(3f, scale)),
                    scaleY = maxOf(1.5f, minOf(3f, scale))
                ),
            model = imageURL,
            contentDescription = ""
        )
    }
}

@Composable
fun BottomDetailsSection(modifier: Modifier = Modifier, title: String) {
    Column(modifier = modifier
        .fillMaxWidth()
        .height(DETAIL_BOTTOM_BAR_HEIGHT.dp)
        .background(MaterialTheme.colorScheme.outline),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = DETAIL_CARD_CONTENT_PADDING.dp,
                    end = DETAIL_CARD_CONTENT_PADDING.dp
                )
        )
    }
}