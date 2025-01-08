package com.example.alp_clement_romeo_evan.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.viewModels.AnnouncementViewModel

@Composable
fun AnnouncementCard(
    name: String,
    date: String,
    content: String,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp, horizontal = 15.dp)
            .clickable {
                isExpanded = !isExpanded
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FFC9)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD3FFD4))
                .padding(16.dp)

        ) {
            Row {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = date,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Text(
            text = content,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Toggle maxLines
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 15.dp),
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Composable
fun AnnouncementCardWithButton(
    navController: NavController,
    announcementViewModel: AnnouncementViewModel,
    name: String,
    date: String,
    content: String,
    token: String,
    announcement_id: Int
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp, horizontal = 15.dp)
    ){
        Card(
            modifier = Modifier
                .clickable {
                    isExpanded = !isExpanded
                },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FFC9)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD3FFD4))
                    .padding(16.dp)

            ) {
                Row {
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = date,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            Text(
                text = content,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Toggle maxLines
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 15.dp),
                overflow = TextOverflow.Ellipsis
            )
        }
        Row (
            modifier = Modifier.align(Alignment.End)
        ){
            FilledTonalButton(
                onClick = { navController.navigate("${PagesEnum.UpdateAnnouncement.name}/${announcement_id}") },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFF00B7FF),
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier
                    .height(35.dp)
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
                onClick = { announcementViewModel.deleteAnnouncement(token, announcement_id) },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFFFF3270),
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(3.dp),
                modifier = Modifier
                    .height(35.dp)
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
fun AnnouncementPreview() {
    ALP_Clement_Romeo_EvanTheme {
        AnnouncementCardWithButton(
            navController = rememberNavController(),
            name = "",
            date = "",
            announcement_id = 0,
            content = "",
            announcementViewModel = viewModel(),
            token = ""
        )
    }
}