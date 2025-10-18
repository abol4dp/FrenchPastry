package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.navcomponent

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.screens.LoginScreen
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.SealedClassNavName
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.screen.DetailsPastry


import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel.LoginViewModel
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.screen.HomeScreen
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.SplashScreen

@Composable
fun NavController(navController: NavHostController, viewModel: LoginViewModel) {

    NavHost(navController = navController, startDestination = SealedClassNavName.Splash.Route) {
        composable(SealedClassNavName.Login.Route) { LoginScreen(navController, viewModel) }
        composable(SealedClassNavName.Home.Route) {
            HomeScreen(navController)
        }
        composable(SealedClassNavName.Splash.Route) { SplashScreen(navController, viewModel) }


        composable(
            route = SealedClassNavName.DetailsPastry.Route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailsPastry(navController, productid = id)
        }

    }


}



