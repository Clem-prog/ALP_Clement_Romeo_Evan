package com.example.alp_clement_romeo_evan.views

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
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.views.components.EventCard
import com.example.alp_clement_romeo_evan.views.components.ReviewCard

@Composable
fun EventDetails() {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFD3FFD4))
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.character_yi),
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
                        text = "Event Name",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                    )
                    Row(
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.character_yi), // Replace with your image resource
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = "Ignatius Romeo",
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = "21-12-2024",
                    fontSize = 15.sp,
                )
            }
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris id nibh nulla. Sed est quam, eleifend non posuere fermentum, dignissim eget nibh. Sed luctus ornare ligula eget consequat. Aenean a arcu eu nulla vestibulum interdum. Nam auctor commodo vestibulum. Etiam a faucibus arcu. Nulla eget neque non est tincidunt consectetur. Cras sed lacus in eros tincidunt hendrerit ac varius dolor. Cras vestibulum massa augue, eu semper eros consectetur ut. Nulla sed porta felis, et commodo purus. Praesent quis gravida justo. Ut consequat tempus rhoncus.\n" +
                        "\n" +
                        "Duis non accumsan nulla. Suspendisse potenti. Sed sit amet consectetur augue. Pellentesque ac ullamcorper diam, a ornare nulla. Nulla convallis, purus vel auctor ullamcorper, augue dui malesuada lectus, quis tincidunt odio dui sit amet lacus. Etiam sed pretium quam. Aenean sollicitudin blandit neque ac suscipit. Nam sed ante non diam volutpat volutpat nec eu erat. Donec ultrices lobortis nisl vel egestas. Donec luctus at arcu nec vestibulum. Maecenas feugiat orci ut tempor tempus. Donec feugiat, justo eu pellentesque viverra, nisl leo pretium eros, accumsan convallis justo ipsum at nulla. Aenean finibus tortor quis ligula placerat rhoncus. ",
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
            ReviewCard()*/ //this is for admins dont erase for now
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
                EventDetails()
            }
        }
    }
}