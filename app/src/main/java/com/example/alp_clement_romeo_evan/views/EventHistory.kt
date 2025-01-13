package com.example.alp_clement_romeo_evan.views

import AttendedEventViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.uiStates.AttendedEventDetailUIState
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventDetailViewModel
import com.example.alp_clement_romeo_evan.views.components.EventCard
import com.example.alp_clement_romeo_evan.views.components.EventCardWithButtons

@Composable
fun EventHistoryView(
    eventDetailViewModel: EventDetailViewModel,
    authenticationViewModel: AuthenticationViewModel,
    attendedEventViewModel: AttendedEventViewModel,
    navController: NavHostController,
    token: String,
    isAdmin: Boolean,
    user_id: Int
) {
    LaunchedEffect(token) {
        eventDetailViewModel.getAllEvents(token)
        authenticationViewModel.getAllUser()
        attendedEventViewModel.getAllAttendedEvents(token)
    }

    val dataStatus = eventDetailViewModel.dataStatus
    val userDataStatus = authenticationViewModel.dataStatus
    val attendanceDataStatus = attendedEventViewModel.dataStatus

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFFE7C9))
    ) {
        Column {
            if (dataStatus is EventDataStatusUIState.GetAllSuccess) {
                val attendedEventIds = if (attendanceDataStatus is AttendedEventDetailUIState.GotAll) {
                    attendanceDataStatus.data.map { it.event_id }.toSet()
                } else {
                    emptySet()
                }
                val ongoingEvents = dataStatus.data.filter { it.isOngoing }
                ongoingEvents.forEach { event ->
                    if (isAdmin && user_id == event.user_id) {
                        EventCardWithButtons(
                            title = event.title,
                            date = event.date,
                            poster = event.poster,
                            navController = navController,
                            event_id = event.id,
                            eventDetailViewModel = eventDetailViewModel,
                            token = token,
                            onClickCard = {
                                navController.navigate("${PagesEnum.EventDetail.name}/${event.id}/${event.user_id}")
                            },
                            onDeleteClick = {
                                eventDetailViewModel.deleteEvent(token, event.id, navController)
                            }
                        )
                    } else if (!isAdmin && attendedEventIds.contains(event.id)) {
                        var username = "Unknown User"
                        if (userDataStatus is AuthenticationStatusUIState.GotAllUser) {
                            username = userDataStatus.userModelData
                                .find { it.id == event.user_id }?.username ?: "Unknown User"
                        }
                        EventCard(
                            title = event.title,
                            date = event.date,
                            poster = event.poster,
                            navController = navController,
                            onClickCard = {
                                navController.navigate("${PagesEnum.EventDetail.name}/${event.id}/${event.user_id}")
                            },
                            name = username,
                        )
                    }
                }

                Text(
                    text = "Event History: ",
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 2.dp)
                        .padding(horizontal = 17.dp)
                )

                val pastEvents = dataStatus.data.filterNot { it.isOngoing }
                pastEvents.forEach { event ->
                    var username = "Unknown User"
                    if (userDataStatus is AuthenticationStatusUIState.GotAllUser) {
                        username = userDataStatus.userModelData
                            .find { it.id == event.user_id }?.username ?: "Unknown User"
                    }
                    val attendedEventIds = if (attendanceDataStatus is AttendedEventDetailUIState.GotAll) {
                        attendanceDataStatus.data.map { it.event_id }.toSet()
                    } else {
                        emptySet()
                    }
                    if (isAdmin && user_id == event.user_id) {
                        EventCard(
                            title = event.title,
                            date = event.date,
                            poster = event.poster,
                            navController = navController,
                            name = "Managed by You",
                            onClickCard = {
                                navController.navigate("${PagesEnum.EventDetail.name}/${event.id}/${event.user_id}")
                            }
                        )
                    } else if (!isAdmin && attendedEventIds.contains(event.id)) {
                        EventCard(
                            title = event.title,
                            date = event.date,
                            poster = event.poster,
                            navController = navController,
                            name = username,
                            onClickCard = {
                                navController.navigate("${PagesEnum.EventDetail.name}/${event.id}/${event.user_id}")
                            }
                        )
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventHistoryPreview() {
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
                                text = "Your Events",
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
                        )
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
                                onClick = { /* Handle EventHistory click */ },
                                modifier = Modifier.size(50.dp),
                                contentPadding = PaddingValues(1.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.home),
                                    contentDescription = "EventHistory",
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
                EventHistoryView(
                    eventDetailViewModel = viewModel(),
                    authenticationViewModel = viewModel(),
                    navController = rememberNavController(),
                    attendedEventViewModel = viewModel(),
                    token = "",
                    isAdmin = false,
                    user_id = 0,
                )
            }
        }
    }
}