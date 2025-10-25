package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TopSlider(sliderList: List<Int>) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    val currentIndex = remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .padding(top = 18.dp)
            .fillMaxWidth()
            .height(180.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sliderList.size) { index ->
            Image(
                painter = painterResource(id = sliderList[index]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))


    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        repeat(sliderList.size) { index ->
            val isSelected = index == currentIndex.value
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(if (isSelected) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color(0xFF5D5D5D) else Color.LightGray
                    )
            )
        }


        // auto-scroll
        LaunchedEffect(Unit) {
            var index = 0
            while (true) {
                delay(2000)
                index = (index + 1) % sliderList.size
                listState.animateScrollToItem(index)
            }
        }
    }
}