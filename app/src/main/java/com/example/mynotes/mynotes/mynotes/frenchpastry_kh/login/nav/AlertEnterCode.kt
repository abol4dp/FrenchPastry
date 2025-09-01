
package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.R
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel




@Composable
fun AlertEnterCode(
    time: Int,
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    if (steepLogin.intValue == 2) {
        val context = LocalContext.current
        val loading by loginViewModel.loadingver.collectAsState()
        val verifyStatus by loginViewModel.verifyStatus.collectAsState()
        var code by remember { mutableStateOf("") }
        val errorVerifyCode by loginViewModel.errorVerifyCode.collectAsState()


        LaunchedEffect(verifyStatus) {
            when (verifyStatus) {
                LoginViewModel.VerifyStatus.Success -> {
                    navController.navigate("homescreen")
                    loginViewModel.resetVerifyStatus()
                }
                LoginViewModel.VerifyStatus.Failure -> {
                    Toast.makeText(context, "کد اشتباه است", Toast.LENGTH_SHORT).show()
                    loginViewModel.resetVerifyStatus()
                    steepLogin.intValue = 0
                }
                LoginViewModel.VerifyStatus.Idle -> Unit
            }
        }

        AlertDialog(
            onDismissRequest = { },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = { steepLogin.intValue = 1 }
                ) {
                    Text("لغو")
                }
            },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    IconButton(
                        onClick = { steepLogin.intValue = 1 }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "",
                            modifier = Modifier.size(25.dp),
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = "کد تأیید",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    MyEditText(
                        value = code,
                        placeholder = "کد تأیید را وارد کنید",
                        onValueChange = { code = it },
                        timer = time.toString(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onError = errorVerifyCode
                    )
                    if (errorVerifyCode) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = "کد وارد شده اشتباه است",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        //تایید کد
                        Button(
                            onClick = {

                                    loginViewModel.verifyCode(context)

                                    Toast.makeText(context, "شماره یا کد خالی است", Toast.LENGTH_SHORT).show()

                            }
                        ) {
                            if (loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("تأیید و ادامه", color = Color.White)
                            }
                        }



                        // ویرایش باتن


                    }





                }
            },

        )
    }
}