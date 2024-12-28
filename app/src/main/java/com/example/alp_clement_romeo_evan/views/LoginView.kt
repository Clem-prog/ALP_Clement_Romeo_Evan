package com.example.alp_clement_romeo_evan.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme

@Composable
fun LoginView() {
    Column (
        modifier = Modifier
            .background(Color(0xFFFFE7C9))
    ){
        Text (
            text = "Wonder of U",
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 250.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 180.dp)
                .background(Color(0xFFFFD2D9), shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .size(600.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {

            }
            Text(
                text = "Welcome to our app!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 45.dp)
            )
            Text(
                text = "Please pick the following to continue.",
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 90.dp)
            )
            Box(
                modifier = Modifier
                    .padding(top = 130.dp)
                    .size(height = 45.dp, width = 330.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFFFFFF))
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Log in",
                        color = Color(0xFF8b8b8b),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(vertical = 1.dp)
                    .padding(top = 210.dp)
                    .size(320.dp)
            )
            Box(
                modifier = Modifier
                    .padding(top = 195.dp)
                    .background(Color(0xFFFFD2D9))
                    .size(height = 35.dp, width = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "OR",
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 250.dp)
                    .size(height = 45.dp, width = 330.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFFFFFF))
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Sign up",
                        color = Color(0xFF8b8b8b),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    ALP_Clement_Romeo_EvanTheme {
        LoginView()
    }
}