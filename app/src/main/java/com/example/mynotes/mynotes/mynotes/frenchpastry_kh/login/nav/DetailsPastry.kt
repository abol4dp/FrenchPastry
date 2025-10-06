package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.SealedClassNavName
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.ProductViewModel
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail.Material
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.observeOn

@Composable
fun DetailsPastry(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel(),
    productid: Int
) {
    val product by viewModel.product.collectAsState()
    Log.d("DETAILS/SCREEN", "Collected product -> $product")
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()


    LaunchedEffect(productid) {
        viewModel.loadProduct(productid)

    }

    when {
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center

            ) {
                CircularProgressIndicator()
            }

        }

        error != null -> {
            Text(
                text = error ?: "خطای ناشناخته",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        product != null -> {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product!!.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)


                )
                LazyColumn {
                    items(product!!.materials) { material ->
                        Text(
                            text = "${material.material} : ${material.amount}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(4.dp)
                        )


                    }


                }

            }


        }


    }


}