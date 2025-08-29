package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.navcomponent

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.SealedClassNavName
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav.AlertEnterCode
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav.loginScreen
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav.HomeScreen

@Composable
fun NavController(navController: NavHostController, viewModel: LoginViewModel) {

    NavHost(navController = navController, startDestination = SealedClassNavName.Login.Route) {
        composable(SealedClassNavName.Login.Route) { loginScreen(navController, viewModel) }
        composable(SealedClassNavName.Home.Route) { HomeScreen(navController, viewModel) }
        composable(SealedClassNavName.Alert.Route) { AlertEnterCode(0, navController, viewModel) }

    }


}



