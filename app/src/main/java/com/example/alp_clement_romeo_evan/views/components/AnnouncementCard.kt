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
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme

@Composable
fun AnnouncementCard() {
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
                    text = "Event 1",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "3h ago",
                    fontSize = 15.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Text(
            text = "Lorem ipsum odor amet, consectetuer adipiscing elit. Fames conubia facilisi platea suspendisse ridiculus lacinia. Vehicula taciti arcu natoque aptent congue nam senectus feugiat natoque. Neque dolor id vulputate lacus platea. Mi ultrices libero posuere fermentum mattis purus convallis. Mi mollis metus magna mi turpis congue vehicula diam.\n" +
                    "\n" +
                    "Per netus efficitur sollicitudin ipsum metus condimentum proin lacinia. Convallis hendrerit urna, posuere neque eleifend aenean. Auctor conubia egestas sed elit, eget quisque nam a. Malesuada maecenas dictumst auctor sapien tortor pellentesque lacinia. Accumsan ac ullamcorper faucibus nisl magnis lobortis mattis natoque. Dignissim sollicitudin etiam habitasse velit mauris arcu, ut ad cursus. Accumsan nulla nam vehicula mi dui nunc. Varius aliquam litora fusce semper ullamcorper vel praesent feugiat. Litora ipsum feugiat fusce nostra inceptos tortor eu.",
            fontSize = 14.sp,
            lineHeight = 20.sp,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Toggle maxLines
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 15.dp),
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Composable
fun AnnouncementCardWithButton() {
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
                        text = "Event 1",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "3h ago",
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            Text(
                text = "Lorem ipsum odor amet, consectetuer adipiscing elit. Fames conubia facilisi platea suspendisse ridiculus lacinia. Vehicula taciti arcu natoque aptent congue nam senectus feugiat natoque. Neque dolor id vulputate lacus platea. Mi ultrices libero posuere fermentum mattis purus convallis. Mi mollis metus magna mi turpis congue vehicula diam.\n" +
                        "\n" +
                        "Per netus efficitur sollicitudin ipsum metus condimentum proin lacinia. Convallis hendrerit urna, posuere neque eleifend aenean. Auctor conubia egestas sed elit, eget quisque nam a. Malesuada maecenas dictumst auctor sapien tortor pellentesque lacinia. Accumsan ac ullamcorper faucibus nisl magnis lobortis mattis natoque. Dignissim sollicitudin etiam habitasse velit mauris arcu, ut ad cursus. Accumsan nulla nam vehicula mi dui nunc. Varius aliquam litora fusce semper ullamcorper vel praesent feugiat. Litora ipsum feugiat fusce nostra inceptos tortor eu.",
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
                onClick = { },
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
        AnnouncementCardWithButton()
    }
}