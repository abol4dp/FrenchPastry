@file:Suppress("UNREACHABLE_CODE")

package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("شماره موبایل خود را وارد کنید") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
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
                                    Toast.makeText(context, "نه", Toast.LENGTH_SHORT).show()
                                    cancel()
                                }

                                else -> {}
                            }

                        }
                    }


                }


            },
            enabled = !loadingsen
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
        if (showDialog) {
            AlertEnterCode(
                time = 120, // مثلا ۲ دقیقه
                navController = navController,
                loginViewModel = loginViewModel,
                phoneNumber = phoneNumber,
                onDismiss = {showDialog = false}
            )
        }


        Spacer(modifier = Modifier.height(16.dp))


    }
}

fun isValidPhoneNumber(phone: String): Boolean {
    return phone.length == 11 && phone.startsWith("0")
}
