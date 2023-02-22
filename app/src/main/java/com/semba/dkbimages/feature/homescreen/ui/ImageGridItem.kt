package com.semba.dkbimages.feature.homescreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.semba.dkbimages.data.model.ImageModel


const val CARD_CONTENT_PADDING = 5
const val CARD_HEIGHT = 200
const val BOTTOM_BAR_HEIGHT = 50

@Composable
fun ImageGridItem(modifier: Modifier = Modifier, item: ImageModel) {
    Card(modifier = modifier, shape = RoundedCornerShape(10.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(modifier = Modifier.fillMaxSize().height(CARD_HEIGHT.dp), model = item.thumbnailUrl, contentDescription = null, contentScale = ContentScale.Crop)

            BottomBar(title = item.title)
        }
    }
}

@Composable
private fun BoxScope.BottomBar(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(BOTTOM_BAR_HEIGHT.dp)
            .background(MaterialTheme.colorScheme.outline)
            .align(Alignment.BottomCenter),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = title,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CARD_CONTENT_PADDING.dp, end = CARD_CONTENT_PADDING.dp)
        )
    }
}