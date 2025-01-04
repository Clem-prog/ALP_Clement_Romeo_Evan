package com.example.alp_clement_romeo_evan.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.WonderOfU
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel
import com.example.alp_clement_romeo_evan.viewModels.HomeViewModel


@Composable
fun WonderOfU(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
) {
    val localContext = LocalContext.current
    val token = homeViewModel.token.collectAsState()
    val userId = homeViewModel.id.collectAsState()


    NavHost(navController = navController, startDestination = PagesEnum.Start.name) {
        composable(route = PagesEnum.Start.name) {
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

        composable(route = PagesEnum.Home.name) {
            ScaffoldMain(
                navController = navController,
                content = {
                    HomeView()
                },
                title = "Home",
            )
        }
        composable(route = PagesEnum.Profile.name) {
            ScaffoldMain(
                navController = navController,
                content = {
                    ProfileView(
                        navController = navController,
                        token = token.value,
                        userId = userId.value,
                        authenticationViewModel = authenticationViewModel,
                        context = localContext,
                    )
                },
                title = "Profile",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldMain(
    navController: NavHostController,
    content: @Composable () -> Unit,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    title: String,
) {
    val isAdmin = homeViewModel.isAdmin.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD1EBFF),
                    titleContentColor = Color.Black,
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text =  title,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(start = 35.dp)
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = { navController.navigate(PagesEnum.Profile.name) },
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .size(40.dp),
                        contentPadding = PaddingValues(1.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = "profile",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)) // Optional rounded corners
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFD1EBFF),
                contentColor = Color.Black,
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 25.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { navController.navigate(PagesEnum.Home.name) },
                            modifier = Modifier.size(50.dp),
                            contentPadding = PaddingValues(1.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "home",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Button(
                            onClick = { /* Handle add click */ },
                            modifier = Modifier.size(50.dp),
                            contentPadding = PaddingValues(1.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.event),
                                contentDescription = "add",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Button(
                            onClick = { /* Handle event click */ },
                            modifier = Modifier.size(50.dp),
                            contentPadding = PaddingValues(1.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.notifications),
                                contentDescription = "events",
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        if (isAdmin) {
                            Spacer(Modifier.weight(1f))
                            Button(
                                onClick = { /* Handle add click */ },
                                modifier = Modifier.size(50.dp),
                                contentPadding = PaddingValues(1.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.add),
                                    contentDescription = "add",
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}