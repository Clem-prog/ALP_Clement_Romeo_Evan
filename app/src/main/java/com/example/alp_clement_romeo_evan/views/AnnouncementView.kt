package com.example.alp_clement_romeo_evan.views

import AttendedEventViewModel
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.models.AttendedEventModel
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.uiStates.AnnouncementStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.AttendedEventDetailUIState
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.AnnouncementViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventDetailViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventFormViewModel
import com.example.alp_clement_romeo_evan.views.components.AnnouncementCard
import com.example.alp_clement_romeo_evan.views.components.AnnouncementCardWithButton
import com.example.alp_clement_romeo_evan.views.components.EventCard
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnnouncementView(
    announcementViewModel: AnnouncementViewModel,
    eventDetailViewModel: EventDetailViewModel,
    attendedEventViewModel: AttendedEventViewModel,
    navController: NavController,
    token: String,
    user_id: Int,
    isAdmin: Boolean,
) {
    LaunchedEffect(token) {
        announcementViewModel.getAllAnnouncement(token)
        eventDetailViewModel.getAllEvents(token)
        attendedEventViewModel.getAllAttendedEvents(token)
    }

    val eventDataStatus = eventDetailViewModel.dataStatus
    val dataStatus = announcementViewModel.dataStatus
    val attendanceDataStatus = attendedEventViewModel.dataStatus

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE7C9))

    ) {
        LazyColumn {
            when (dataStatus) {
                is AnnouncementStatusUIState.GotAll -> {
                    if (isAdmin) {
                        val userEvents = when (eventDataStatus) {
                            is EventDataStatusUIState.GetAllSuccess -> {
                                eventDataStatus.data.filter { it.user_id == user_id }
                            }

                            else -> emptyList()
                        }

                        val userEventIds = userEvents.map { it.id }

                        val filteredAnnouncements = dataStatus.data.filter {
                            it.event_id in userEventIds
                        }

                        if (filteredAnnouncements.isNotEmpty()) {
                            items(filteredAnnouncements.size) { index ->
                                val announcement = filteredAnnouncements[index]

                                val event = userEvents.find { it.id == announcement.event_id }

                                if (event != null) {
                                    AnnouncementCardWithButton(
                                        name = event.title,
                                        date = formatTimeAgo(announcement.date),
                                        announcement_id = announcement.id,
                                        content = announcement.content,
                                        token = token,
                                        navController = navController,
                                        announcementViewModel = announcementViewModel
                                    )
                                }
                            }
                        } else {
                            item {
                                Text(
                                    text = "No announcements found.",
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    } else {
                        val attendedEvents = when (attendanceDataStatus) {
                            is AttendedEventDetailUIState.GotAll -> {
                                attendanceDataStatus.data
                            }
                            else -> emptyList()
                        }

                        val attendedEventIds = attendedEvents.map { it.event_id }

                        val filteredAnnouncements = dataStatus.data.filter {
                            it.event_id in attendedEventIds
                        }

                        if (filteredAnnouncements.isNotEmpty()) {
                            items(filteredAnnouncements.size) { index ->
                                val announcement = filteredAnnouncements[index]

                                if (eventDataStatus is EventDataStatusUIState.GetAllSuccess) {
                                    val event = eventDataStatus.data.find { it.id == announcement.event_id }

                                    if (event != null) {
                                        AnnouncementCard(
                                            name = event.title,
                                            date = formatTimeAgo(announcement.date),
                                            content = announcement.content
                                        )
                                    }
                                }
                            }
                        } else {
                            item {
                                Text(
                                    text = "No announcements found.",
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }

                else -> {
                    item {
                        Text(
                            text = "Nothing here!",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun formatTimeAgo(uploadTime: String): String {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val uploadedAt = OffsetDateTime.parse(uploadTime, formatter).toLocalDateTime()

    val now = LocalDateTime.now()
    val nowInUTC =
        now.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()

    val duration = Duration.between(uploadedAt, nowInUTC)

    return when {
        duration.seconds <= 0 -> "just now"
        duration.seconds < 60 -> "${duration.seconds}s ago"
        duration.toMinutes() < 60 -> "${duration.toMinutes()}m ago"
        duration.toHours() < 24 -> "${duration.toHours()}h ago"
        duration.toDays() < 7 -> "${duration.toDays()}d ago"
        else -> "More than a week ago"
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AnnouncementPreview() {
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
                                text = "Announcements",
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
                                onClick = { /* Handle Announcement click */ },
                                modifier = Modifier.size(50.dp),
                                contentPadding = PaddingValues(1.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.home),
                                    contentDescription = "Announcement",
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
                AnnouncementView(
                    announcementViewModel = viewModel(),
                    eventDetailViewModel = viewModel(),
                    attendedEventViewModel = viewModel(),
                    token = "",
                    user_id = 0,
                    isAdmin = false,
                    navController = rememberNavController(),
                )
            }
        }
    }
}