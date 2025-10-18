package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.viewModel.ProductViewModel

@Composable
fun DetailsPastry(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel(),
    productid: Int
) {
    val product by viewModel.product.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(productid) {
        viewModel.loadProduct(productid)
    }

    Box(
        modifier = Modifier.fillMaxSize(), Alignment.Center
    ) {


        when {

            loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center

                ) { CircularProgressIndicator() }

            }

            error != null -> {
                Text(
                    text = "Error", color = Color.Red, fontSize = 30.sp
                )


            }

            product != null -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = product!!.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LazyColumn {
                        items(product!!.materials) { materials ->


                            Text(
                                text = "${materials.material} : ${materials.amount}",
                                modifier = Modifier.padding(4.dp)

                            )
                        }


                    }


                }


            }


        }


    }


}