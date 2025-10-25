package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.materialIcon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.transform.CircleCropTransformation
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.Resource
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.viewModel.ProductViewModel

@Composable
fun DetailsPastry(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel(),
    productid: Int
) {
    val state by viewModel.productState.collectAsState()


    LaunchedEffect(productid) {
        viewModel.loadProduct(productid)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        when (state) {

            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()

                }


            }


            is Resource.Error -> {
                val message = (state as Resource.Error).message
                Text("خطا: $message", color = Color.Red)
            }


            is Resource.Success -> {

                val pastry = (state as Resource.Success).data.pastry

                AsyncImage(
                    model = pastry!!.thumbnail,
                    contentDescription = "مواد به‌کاررفته در شیرینی",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                if (pastry != null) {
                    Text(pastry.title)

                }
                LazyColumn {
                    items(pastry!!.materials) { material ->

                        Text(
                            "${material.material} : ${material.amount}",
                            modifier = Modifier.padding(4.dp)
                        )


                    }

                }

            }

        }


    }
}









