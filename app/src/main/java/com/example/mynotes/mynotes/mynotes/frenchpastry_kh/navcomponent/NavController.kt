package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.navcomponent

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav.LoginScreen
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.SealedClassNavName

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav.HomeScreen
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.nav.SplashScreen

@Composable
fun NavController(navController: NavHostController, viewModel: LoginViewModel) {

    NavHost(navController = navController, startDestination = SealedClassNavName.Splash.Route) {
        composable(SealedClassNavName.Login.Route) { LoginScreen(navController, viewModel) }
        composable(SealedClassNavName.Home.Route) { HomeScreen(navController, viewModel) }
        composable(SealedClassNavName.Splash.Route) { SplashScreen(navController, viewModel) }

    }


}



