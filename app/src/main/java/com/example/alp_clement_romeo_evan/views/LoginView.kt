package com.example.alp_clement_romeo_evan.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel

@Composable
fun LoginView(
    authenticationViewModel: AuthenticationViewModel,
    navController: NavHostController,
    context: Context
) {

    LaunchedEffect(authenticationViewModel.dataStatus) {
        val dataStatus = authenticationViewModel.dataStatus
        if (dataStatus is AuthenticationStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            authenticationViewModel.clearErrorMessage()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE7C9))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 180.dp)
                .background(
                    Color(0xFFFFD2D9),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .size(700.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 170.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFFA7C3),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Login to your account",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                OutlinedTextField(
                    value = authenticationViewModel.emailInput,
                    onValueChange = {
                        authenticationViewModel.changeEmailInput(it)
                        authenticationViewModel.checkLoginForm()
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFA1FDF6),
                        focusedContainerColor = Color(0xFFA1FDF6)
                    )
                )

                OutlinedTextField(
                    value = authenticationViewModel.passwordInput,
                    onValueChange = {
                        authenticationViewModel.changePasswordInput(it)
                        authenticationViewModel.checkLoginForm()
                    },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFA1FDF6),
                        focusedContainerColor = Color(0xFFA1FDF6)
                    )
                )

                Button(
                    onClick = {
                        authenticationViewModel.loginUser(navController = navController)
                    },
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFA1FDF6),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Log In")
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    ALP_Clement_Romeo_EvanTheme {
        LoginView(
            authenticationViewModel = viewModel(),
            navController = rememberNavController(),
            context = LocalContext.current
        )
    }
}


