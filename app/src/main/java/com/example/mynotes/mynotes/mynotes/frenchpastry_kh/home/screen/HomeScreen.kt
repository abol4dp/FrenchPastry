package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.R
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.SealedClassNavName
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.viewModel.HomeViewModel
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.PastryItem


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val mainResponse by viewModel.mainResponse.collectAsState()
    val loading by viewModel.loading.collectAsState()


    val sliderImages = listOf(
        R.drawable.b1,
        R.drawable.b2,
        R.drawable.b3
    )



    LaunchedEffect(Unit) {
        viewModel.getMain()
        Log.d(
            "HOME/SCREEN",
            "Pastries IDs: ${mainResponse.pastries.flatMap { it.pastries.map { item -> item.ID } }}"
        )
    }
    when {
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        mainResponse != null -> {
            val response = mainResponse
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                //Slider
                item {
                    TopSlider(sliderList = sliderImages)
                    Spacer(modifier = Modifier.height(16.dp))
                }



                items(response.pastries) { pastriesItem ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = pastriesItem.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxSize(),
                            maxItemsInEachRow = 2,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            pastriesItem.pastries.forEach { item ->
                                PastriesItemCard(
                                    item = item,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("محصولی یافت نشد")
            }
        }
    }
}

@Composable
fun PastriesItemCard(
    item: PastryItem,
    navController: NavHostController
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(6.dp)
            .width(160.dp)
            .height(230.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {
            Log.d("DETAILS/NAV", "Navigating to details with id=${item.ID}")
            navController.navigate("${SealedClassNavName.DetailsPastry.Route}/${item.ID}")
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = item.thumbnail,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (item.has_discount) {
                Text(
                    text = "${item.price} تومان",
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    fontWeight = FontWeight.Normal,
                    style = TextStyle(
                        textDecoration = TextDecoration.LineThrough
                    )
                )
            }
            Text(
                text = "${item.sale_price} تومان",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 6.dp)
            )
        }
    }
}