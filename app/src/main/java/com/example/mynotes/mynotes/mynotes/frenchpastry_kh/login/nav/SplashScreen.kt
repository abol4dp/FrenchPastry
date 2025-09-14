package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.R
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()

) {

    val savedPhone by loginViewModel.getPhoneNumber().observeAsState()


    LaunchedEffect(savedPhone) {
        Log.d("HILT-COMPOSE", "Saved phone = $savedPhone")
        delay(1000)
        if (!savedPhone.isNullOrEmpty()) {
            navController.navigate("homescreen") {
                popUpTo("splashscreen") { inclusive = true }
            }
        }else {

            navController.navigate("loginscreen"){
                popUpTo("splashscreen") { inclusive = true }
            }
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen_logo),
            contentDescription = "App Logo",
            modifier = Modifier

        )
    }
}

