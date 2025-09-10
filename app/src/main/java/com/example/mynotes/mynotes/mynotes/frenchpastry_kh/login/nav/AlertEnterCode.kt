@file:Suppress("NAME_SHADOWING")

package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    phoneNumber: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val loading by loginViewModel.loadingver.collectAsState()
    val verifyStatus by loginViewModel.verifyStatus.collectAsState()
    var code by remember { mutableStateOf("") }
    val errorVerifyCode by loginViewModel.errorVerifyCode.collectAsState()







    LaunchedEffect(verifyStatus) {
        when (verifyStatus) {
            LoginViewModel.VerifyStatus.Success -> {
                loginViewModel.savePhone(phoneNumber)
                navController.navigate("homescreen"){
                    popUpTo("loginScreen" ) {inclusive = true}
                }
                loginViewModel.resetVerifyStatus()
                onDismiss()
            }

            LoginViewModel.VerifyStatus.Failure -> {
                Toast.makeText(context, "کد اشتباه است", Toast.LENGTH_SHORT).show()
                loginViewModel.resetVerifyStatus()
            }

            LoginViewModel.VerifyStatus.Idle -> Unit
        }
    }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("لغو")
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                IconButton(onClick = { onDismiss() }) {
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
                    textAlign = TextAlign.End,
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
                ) {
                    Button(
                        shape = RoundedCornerShape(9.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ), modifier = Modifier
                            .fillMaxWidth()
                            .height(43.dp)
                            .padding(vertical = 3.dp),
                        onClick = {
                            if (code.isNotEmpty() && phoneNumber.isNotEmpty()) {
                                loginViewModel.verifyCode(code, phoneNumber, context)
                            } else {
                                Toast.makeText(
                                    context,
                                    if (code.isEmpty()) "لطفاً کد را وارد کنید" else "شماره موبایل نامعتبر است",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .padding(end = 10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.clickable { onDismiss() },
                            text = "ویرایش شماره",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    )
}