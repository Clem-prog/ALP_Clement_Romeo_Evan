package com.example.alp_clement_romeo_evan.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.WonderOfU
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel


@Composable
fun WonderOfU(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
) {
    val localContext = LocalContext.current

    NavHost(navController = navController, startDestination = PagesEnum.Start.name) {
        composable(route = PagesEnum.Start.name) {
            // Here you need to define what should be shown when Start is the destination.
            StartView(
                navController = navController,
                context = localContext
            )
        }
        composable(route = PagesEnum.Login.name) {
            LoginView(

                authenticationViewModel = authenticationViewModel,
                navController = navController,
                context = localContext
            )
        }

        composable(route = PagesEnum.Register.name) {
            SignUpView(
                authenticationViewModel = authenticationViewModel,
                navController = navController,
                context = localContext
            )
        }
    }
}