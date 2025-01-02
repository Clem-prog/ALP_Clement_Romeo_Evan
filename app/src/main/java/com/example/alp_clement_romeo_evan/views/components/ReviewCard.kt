package com.example.alp_clement_romeo_evan.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme

@Composable
fun ReviewCard() {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable {
                isExpanded = !isExpanded
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.character_yi), // Replace with your image resource
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Ignatius Romeo",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 7.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "4.5/5",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Image(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "star",
                    )
                }
            }
            Text(
                text = "Title Review",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "Lorem ipsum odor amet, consectetuer adipiscing elit. Fames conubia facilisi platea suspendisse ridiculus lacinia. Vehicula taciti arcu natoque aptent congue nam senectus feugiat natoque. Neque dolor id vulputate lacus platea. Mi ultrices libero posuere fermentum mattis purus convallis. Mi mollis metus magna mi turpis congue vehicula diam.\n" +
                        "\n" +
                        "Per netus efficitur sollicitudin ipsum metus condimentum proin lacinia. Convallis hendrerit urna, posuere neque eleifend aenean. Auctor conubia egestas sed elit, eget quisque nam a. Malesuada maecenas dictumst auctor sapien tortor pellentesque lacinia. Accumsan ac ullamcorper faucibus nisl magnis lobortis mattis natoque. Dignissim sollicitudin etiam habitasse velit mauris arcu, ut ad cursus. Accumsan nulla nam vehicula mi dui nunc. Varius aliquam litora fusce semper ullamcorper vel praesent feugiat. Litora ipsum feugiat fusce nostra inceptos tortor eu.",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewPreview() {
    ALP_Clement_Romeo_EvanTheme {
        ReviewCard()
    }
}