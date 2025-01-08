package com.example.alp_clement_romeo_evan.views.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventDetailViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun EventCard(
    title: String,
    date: String,
    poster: String,
    navController: NavController,
    name: String,
    onClickCard: () -> Unit = {}
) {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val showDate = OffsetDateTime.parse(date, formatter)
    val painter = rememberAsyncImagePainter(poster)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp, horizontal = 15.dp)
            .height(280.dp)
            .clickable(onClick = onClickCard),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD3FFD4)),
    ) {
        Image(
            painter = painter,
            contentDescription = "Event Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 15.dp)
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, // Limits to one line
                    overflow = TextOverflow.Ellipsis, // Adds ellipses if the text is too long
                    modifier = Modifier.widthIn(max = 230.dp)
                )
                Text(
                    text = "By ${name}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = showDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun EventCardWithButtons(
    title: String,
    date: String,
    poster: String,
    event_id: Int,
    token: String,
    eventDetailViewModel: EventDetailViewModel,
    navController: NavHostController, //for update and create announcement
    onClickCard: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val showDate = OffsetDateTime.parse(date, formatter)
    val painter = rememberAsyncImagePainter(poster)

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp, horizontal = 15.dp)
    ) {
        Card(
            modifier = Modifier
                .height(280.dp)
                .clickable(onClick = onClickCard),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD3FFD4)),
        ) {
            Image(
                painter = painter,
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 15.dp)
            ) {
                Column {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1, // Limits to one line
                        overflow = TextOverflow.Ellipsis, // Adds ellipses if the text is too long
                        modifier = Modifier.widthIn(max = 230.dp)
                    )
                    Text(
                        text = "By You",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = showDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }
        Row {
            FilledTonalButton(
                onClick = { navController.navigate("${PagesEnum.CreateAnnouncement.name}/${event_id}") },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFFC1FF69),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(7.dp),
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 5.dp)
            ) {
                Text(
                    text = "create announcement",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            FilledTonalButton(
                onClick = { eventDetailViewModel.markEventAsDone(token, event_id) },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFFFF3270),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(7.dp),
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 5.dp, start = 5.dp)
            ) {
                Text(
                    text = "end event",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(Modifier.weight(1f))

            FilledTonalButton(
                onClick = { navController.navigate("${PagesEnum.UpdateEvent.name}/${event_id}") },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFF00B7FF),
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 5.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = "edit",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(24.dp)
                )
            }
            FilledTonalButton(
                onClick = { onDeleteClick() },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFFFF3270),
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier
                    .height(40.dp)
                    .width(45.dp)
                    .padding(top = 5.dp, start = 5.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "comment",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardPreview() {
    ALP_Clement_Romeo_EvanTheme {
        EventCardWithButtons(
            title = "",
            date = "",
            poster = "",
            event_id = 0,
            navController = rememberNavController(), //for update and create announcement
            onClickCard = {},
            token = "",
            eventDetailViewModel = viewModel()
        )
    }
}