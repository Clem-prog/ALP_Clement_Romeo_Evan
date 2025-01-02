package com.example.alp_clement_romeo_evan.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAnnouncementView() {
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
                                text = "Announcement+",
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
                            Icon(
                                painter = painterResource(R.drawable.profile),
                                contentDescription = "Profile Icon",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* Navigate to Home */ }) {
                            Icon(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "Home Icon",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        IconButton(onClick = { /* Navigate to Add */ }) {
                            Icon(
                                painter = painterResource(R.drawable.event),
                                contentDescription = "Add Icon",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        IconButton(onClick = { /* Navigate to Notifications */ }) {
                            Icon(
                                painter = painterResource(R.drawable.notifications),
                                contentDescription = "Notifications Icon",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color(0xFFE0FFE0))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Announcement:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = "",
                    onValueChange = { /* Handle text change */ },
                    placeholder = { Text("Insert Announcement Here") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {  },
                        modifier = Modifier.width(120.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF9FFC9),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Publish")
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateAnnouncementPreview() {
    ALP_Clement_Romeo_EvanTheme {
        CreateAnnouncementView()
    }
}

