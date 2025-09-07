@file:Suppress("UNREACHABLE_CODE")

package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.R
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.PastryHelper
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val loadingsen by loginViewModel.loadingsen.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()
    var phoneNumber by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 7.dp)
                    .padding(end = 35.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "ورود به برنامه",
                    fontSize = 20.sp,
                    lineHeight = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(end = 20.dp)
                .weight(0.45f),
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "شیرینی فرانسوی",
                fontWeight = FontWeight.Black,
                color = Color.Black,
                fontSize = 28.sp,
                lineHeight = 40.sp,
                textAlign = TextAlign.Right,
            )
            Spacer(modifier = Modifier.height(20.dp))

            MyEditText(
                value = phoneNumber,
                placeholder = "شماره موبایل خود را وارد کنید",
                onValueChange = { phoneNumber = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                onError = error
            )

            if (error) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "فرمت شماره تلفن وارد شده صحیح نمی باشد",
                    textAlign = TextAlign.End,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Spacer(modifier = Modifier.padding(6.dp))
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(210.dp)
                    .height(45.dp)
                    .padding(vertical = 2.dp)
                    .padding(end = 7.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    if (isValidPhoneNumber(phoneNumber)) {
                        CoroutineScope(Dispatchers.Main).launch {
                            loginViewModel.sendCodeNumber(phoneNumber, context)

                            loginViewModel.sendCodeStatus.collect { status ->
                                when (status) {
                                    LoginViewModel.SendCodeStatus.Success -> {
                                        showDialog = true
                                    }

                                    LoginViewModel.SendCodeStatus.Failure -> {
                                        Toast.makeText(context, "نادرست", Toast.LENGTH_SHORT).show()
                                        cancel()
                                    }

                                    else -> {}
                                }
                            }
                        }
                    }
                }
            ) {
                Text(
                    if (loadingsen) "... درحال ارسال کد "
                    else "ارسال کد به شماره موبایل من"
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Right
                    )
                }
                if (showDialog) {
                    AlertEnterCode(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        phoneNumber = phoneNumber,
                        onDismiss = { showDialog = false }
                    )
                }
            }
        }
    }
}

fun isValidPhoneNumber(phone: String): Boolean {
    return phone.length == 11 && phone.startsWith("0")
}