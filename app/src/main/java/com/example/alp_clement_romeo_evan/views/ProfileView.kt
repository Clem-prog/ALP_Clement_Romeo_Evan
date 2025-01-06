package com.example.alp_clement_romeo_evan.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel
import com.example.alp_clement_romeo_evan.viewModels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    navController: NavHostController,
    token: String,
    userId: Int,
    authenticationViewModel: AuthenticationViewModel,
    homeViewModel: HomeViewModel,
    context: Context,
) {
    LaunchedEffect(token) {
        authenticationViewModel.getUserInfo(token, userId)
    }

    val dataStatus = authenticationViewModel.dataStatus
    when(dataStatus) {
        is AuthenticationStatusUIState.GotUser -> Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFE7C9))
                .padding(20.dp)
                .padding(top = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.character_yi), // Replace with your image resource
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable { /* this handles profile pic editing */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column (
                modifier = Modifier.align(Alignment.Start)
            ){
                if (dataStatus.userModelData.isAdmin) {
                    Text(
                        text = "Username",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row {
                        Text(
                            text = dataStatus.userModelData.username,
                            fontSize = 18.sp,
                        )
                        Image(
                            painter = painterResource(R.drawable.admin),
                            contentDescription = "admin",
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                } else {
                    Text(
                        text = "Username",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = dataStatus.userModelData.username,
                        fontSize = 18.sp,
                    )
                }
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 1.dp)
                )

                Spacer(modifier = Modifier.height(19.dp))

                Text(
                    text = "Email",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dataStatus.userModelData.email,
                    fontSize = 18.sp,
                )
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 1.dp)
                )

                Spacer(modifier = Modifier.height(19.dp))

                Text(
                    text = "Password",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dataStatus.userModelData.password,
                    fontSize = 18.sp,
                )
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 1.dp)
                )

                Spacer(modifier = Modifier.height(19.dp))

                Row {
                    Button(
                        onClick = { homeViewModel.logoutUser(token, navController) },
                        modifier = Modifier
                            .width(120.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA1FDF6),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Log Out")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { navController.navigate(PagesEnum.ProfileEdit.name) },
                        modifier = Modifier
                            .width(120.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA1FDF6),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
        }
        else -> Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No Data Found!",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileViewPreview() {
    ALP_Clement_Romeo_EvanTheme {
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
                                text = "Home",
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
                            onClick = { /* Handle profile click */ },
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
                                onClick = {  },
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
                        }
                    }
                }
            },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                ProfileView(
                    authenticationViewModel = viewModel(),
                    homeViewModel = viewModel(),
                    navController = rememberNavController(),
                    token = "",
                    userId = 0,
                    context = LocalContext.current
                )
            }
        }
    }
}
