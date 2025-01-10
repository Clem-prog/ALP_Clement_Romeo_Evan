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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel

@Composable
fun ReviewCard(
    authenticationViewModel: AuthenticationViewModel,
    title: String,
    rating: Int,
    review: String,
    user_id: Int,
    token: String,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var dataStatus = authenticationViewModel.dataStatus

    LaunchedEffect(token) {
        authenticationViewModel.getAllUser()
    }

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
                    painter = painterResource(R.drawable.profile), // Replace with your image resource
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    if (dataStatus is AuthenticationStatusUIState.GotAllUser) {
                        dataStatus.userModelData.forEach { user ->
                            if (user.id == user_id) {
                                Text(
                                    text = user.username,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(start = 7.dp)
                                )
                            }
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "${rating}/5",
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
                text = title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = review,
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
        ReviewCard(
            authenticationViewModel = viewModel(),
            title = "",
            rating = 0,
            review = "",
            user_id = 0,
            token = ""
        )
    }
}