package com.example.alp_clement_romeo_evan.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel

@Composable
fun SignUpView(
    authenticationViewModel: AuthenticationViewModel,
    navController: NavHostController,
    context: Context
) {
    val registerUIState by authenticationViewModel.authenticationUIState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE7C9))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 180.dp)
                .background(Color(0xFFFFD2D9), shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .size(700.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFFA7C3),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Sign up to our app",
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
                        authenticationViewModel.checkRegisterForm()
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
                    value = authenticationViewModel.usernameInput,
                    onValueChange = {
                        authenticationViewModel.changeUsernameInput(it)
                        authenticationViewModel.checkRegisterForm()
                    },
                    label = { Text("Username") },
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
                        authenticationViewModel.checkRegisterForm()
                    },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor =Color(0xFFA1FDF6),
                        focusedContainerColor = Color(0xFFA1FDF6)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = authenticationViewModel.isAdminInput,
                        onCheckedChange = {
                            authenticationViewModel.changeIsAdminInput(it)
                            authenticationViewModel.checkRegisterForm()
                        }
                    )
                    Text("I am an Admin")
                }

                Button(
                    onClick = {
                        authenticationViewModel.registerUser(navController)
                    },
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFA1FDF6),
                        contentColor = Color.Black
                    ),
                    enabled = registerUIState.buttonEnabled,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Sign up")
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpPreview() {
    ALP_Clement_Romeo_EvanTheme {
        SignUpView(
            authenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
            navController = rememberNavController(),
            context = LocalContext.current
        )
    }
}
