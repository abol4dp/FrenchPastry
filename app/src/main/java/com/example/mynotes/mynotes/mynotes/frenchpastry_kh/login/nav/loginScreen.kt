package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.R
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel
import kotlinx.coroutines.delay


val steepLogin = mutableIntStateOf(1)

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val imgUr = listOf(
        "https://raw.githubusercontent.com/ihoseinam/video-shop/main/pastry/head_login.png",
        "https://raw.githubusercontent.com/ihoseinam/video-shop/main/pastry/logo_login.png",
    )


    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }
    val loadingsen by loginViewModel.loadingsen.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()
    var code by remember { mutableStateOf("") }
    val verifyStatus by loginViewModel.verifyStatus.collectAsState()
    val sendCodeStatus by loginViewModel.sendCodeStatus.collectAsState()
    var timeLeft by remember { mutableIntStateOf(150) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)

    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.55f),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                painter = painterResource(id = R.drawable.img_pastry_login),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )


// glide
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
                contentAlignment = Alignment.BottomStart
            ) {

                Text(
                    modifier = Modifier.padding(start = 13.dp),
                    text = "ورود به برنامه",
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 20.sp,
                    lineHeight = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .weight(0.45f)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "شیرینی فرانسوی",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                fontSize = 30.sp,
                lineHeight = 45.sp,
            )
            Spacer(modifier = Modifier.height(20.dp))

            MyEditText(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = "شماره موبایل خود را وارد کنید",
            )

            LaunchedEffect(sendCodeStatus) {
                when (sendCodeStatus) {
                      LoginViewModel.SendCodeStatus.Success -> {
                        steepLogin.intValue = 2
                    }

                    LoginViewModel.SendCodeStatus.Failure -> {

                    }

                    LoginViewModel.SendCodeStatus.Idle -> Unit
                }

            }
                LaunchedEffect(isTimerRunning) {
                if (isTimerRunning) {

                    while (timeLeft > 0 && isTimerRunning) {
                        delay(1000)
                        timeLeft--
                    }
                    isTimerRunning = false
                }
            }

            AlertEnterCode(timeLeft, navController)


            //دکمه
            Button(


                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {

                    if (isValidPhoneNumber(phoneNumber)) {
                        loginViewModel.sendCodeNumber(phoneNumber, context)


                    }


                }


            ) {

                Text("ارسال کد")
            }


        }


    }
}

fun isValidPhoneNumber(phone: String): Boolean {
    return phone.length == 11 && phone.startsWith("0")
}