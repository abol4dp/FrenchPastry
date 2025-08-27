package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel


@Composable
fun loginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }
    val loadingsen by loginViewModel.loadingsen.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()
    var code by remember { mutableStateOf("") }
    val verifyStatus by loginViewModel.verifyStatus.collectAsState()






    LaunchedEffect(verifyStatus) {
        when (verifyStatus) {
            LoginViewModel.VerifyStatus.Success -> {
                navController.navigate("homescreen")
                loginViewModel.resetVerifyStatus()
            }

            LoginViewModel.VerifyStatus.Failure -> {
                Toast.makeText(
                    context,
                    "کد اشتباه است",
                    Toast.LENGTH_SHORT
                ).show()
                loginViewModel.resetVerifyStatus()

            }

            LoginViewModel.VerifyStatus.Idle -> Unit


        }
    }







    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ورود به شیرینی فرانسوی",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ورودی شماره تلفن
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("شماره موبایل خود را وارد کنید") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.padding(bottom = 8.dp)
        )




        Spacer(modifier = Modifier.height(16.dp))

        // دکمه ارسال کد
        Button(
            onClick = {
                if (isValidPhoneNumber(phoneNumber)) {
                    Log.d("LOGIN/UI", "sendCode tapped | phone=$phoneNumber")
                    loginViewModel.sendCodeNumber(phoneNumber, context)

                } else {

                    Log.e("LOGIN/UI", "invalid phone format: $phoneNumber") // LOG
                    Toast.makeText(context, "شماره معتبر نیست", Toast.LENGTH_SHORT).show()
                }


            }, enabled = !loadingsen

        ) {

            Text(if (loadingsen) "درحال ارسال " else "ارسال کد ")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("کد ورود را وارد کنید") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(


            onClick = {

                if (code.isNotEmpty()) {
                    loginViewModel.verifyCode(code, phoneNumber, context)


                }


            },
            modifier = Modifier.width(95.dp)
        ) {
            Text("ورود")

        }

    }


}


// تابع ساده برای اعتبارسنجی شماره
fun isValidPhoneNumber(phone: String): Boolean {
    return phone.length == 11 && phone.startsWith("0")
}
