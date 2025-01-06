package com.example.alp_clement_romeo_evan.views

import android.annotation.SuppressLint
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventDetailViewModel
import com.example.alp_clement_romeo_evan.views.components.EventCard
import com.example.alp_clement_romeo_evan.views.components.ReviewCard
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun EventDetails(
    eventDetailViewModel: EventDetailViewModel,
    authenticationViewModel: AuthenticationViewModel,
    event_id: Int,
    token: String,
    user_id: Int,
) {
    var isExpanded by remember { mutableStateOf(false) }
    LaunchedEffect(token) {
        eventDetailViewModel.getEventDetails(token, event_id)
    }
    LaunchedEffect(user_id) {
        authenticationViewModel.getEventUser(token, user_id)
    }
    var dataStatus = eventDetailViewModel.dataStatus
    var userDataStatus = authenticationViewModel.dataStatus

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFD3FFD4))
    ) {
        when (dataStatus) {
            is EventDataStatusUIState.Success ->
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    val formatter = DateTimeFormatter.ISO_DATE_TIME
                    val showDate = OffsetDateTime.parse(dataStatus.data.date, formatter)

                    Image(
                        painter = rememberAsyncImagePainter(dataStatus.data.poster),
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(200.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )

                    Row(modifier = Modifier.padding(top = 15.dp)) {
                        Column {
                            Text(
                                text = dataStatus.data.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                            )
                            Row(
                                modifier = Modifier.padding(top = 5.dp)
                            ) {
                                if (userDataStatus is AuthenticationStatusUIState.GotUser) {
                                    Image(
                                        painter = painterResource(R.drawable.character_yi),
                                        contentDescription = "Profile Picture",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(25.dp)
                                            .clip(CircleShape)
                                    )
                                    Text(
                                        text = userDataStatus.userModelData.username,
                                        fontSize = 15.sp,
                                        modifier = Modifier
                                            .padding(start = 5.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = showDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            fontSize = 15.sp,
                        )
                    }
                    Text(
                        text = dataStatus.data.description,
                        fontSize = 17.sp,
                        lineHeight = 20.sp,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 6,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 15.dp, bottom = 7.dp)
                            .clickable {
                                isExpanded = !isExpanded
                            }
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .size(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF9FFC9),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {}
                    ) {
                        Text(
                            text = "Attend Event",
                            fontSize = 15.sp
                        )
                    }
                    /*Text(
                        text = "Review(s):",
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    ReviewCard()
                    ReviewCard()*/ //this is for admins and history dont erase for now
                }

            else ->
                Column(
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsPreview() {
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
                                text = "Event Detail",
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
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        ) // Optional rounded corners
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
                                onClick = { /* Handle home click */ },
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
                EventDetails(
                    eventDetailViewModel = viewModel(),
                    authenticationViewModel = viewModel(),
                    event_id = 0,
                    token = "",
                    user_id = 0,
                )
            }
        }
    }
}