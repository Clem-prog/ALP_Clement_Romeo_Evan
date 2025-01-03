package com.example.alp_clement_romeo_evan.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme

@Composable
fun EventCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp, horizontal = 15.dp)
            .height(280.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD3FFD4)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.character_yi),
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
                    text = "Event 1",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "By Ignatius Romeo",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "21-12-2024",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun EventCardWithButtons() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp, horizontal = 15.dp),
    ) {
        Card(
            modifier = Modifier
                .height(280.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD3FFD4)),
        ) {
            Image(
                painter = painterResource(id = R.drawable.character_yi),
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
                        text = "Event 1",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "By Ignatius Romeo",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = "21-12-2024",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }
        Row {
            FilledTonalButton(
                onClick = { },
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
                onClick = { },
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
                onClick = { },
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
                    contentDescription = "comment",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(24.dp)
                )
            }
            FilledTonalButton(
                onClick = { },
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
        EventCardWithButtons()
    }
}