package com.example.alp_clement_romeo_evan.views

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.R
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.models.EventModel
import com.example.alp_clement_romeo_evan.repositories.CategoryRepository
import com.example.alp_clement_romeo_evan.ui.theme.ALP_Clement_Romeo_EvanTheme
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.CategoryUIState
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.AuthenticationViewModel
import com.example.alp_clement_romeo_evan.viewModels.CategoryViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventDetailViewModel
import com.example.alp_clement_romeo_evan.views.components.EventCard
import com.example.alp_clement_romeo_evan.views.components.categoriesButton

@Composable
fun HomeView(
    categoryViewModel: CategoryViewModel,
    eventDetailViewModel: EventDetailViewModel,
    authenticationViewModel: AuthenticationViewModel,
    navController: NavController,
    token: String,
    isAdmin: Boolean
) {
    LaunchedEffect(token) {
        categoryViewModel.getAllCategories(token)
        eventDetailViewModel.getAllEvents(token)
        authenticationViewModel.getAllUser()
    }

    val (selectedCategory, setSelectedCategory) = rememberSaveable { mutableStateOf("") }
    val dataStatus = categoryViewModel.dataStatus
    val eventDataStatus = eventDetailViewModel.dataStatus
    val userDataStatus = authenticationViewModel.dataStatus

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE7C9))

    ) {
        Column {
            LazyRow(
                modifier = Modifier.padding(vertical = 10.dp),
            ) {
                when (dataStatus) {
                    is CategoryUIState.Success -> {
                        item {
                            categoriesButton(
                                categoryName = "All",
                                isSelected = selectedCategory == "",
                                onCategorySelected = { selectedCategoryName ->
                                    setSelectedCategory("")
                                }
                            )
                        }
                        items(dataStatus.data.size) { index ->
                            val category = dataStatus.data[index]
                            categoriesButton(
                                categoryName = category.name,
                                isSelected = selectedCategory == category.name,
                                onCategorySelected = { selectedCategoryName ->
                                    setSelectedCategory(selectedCategoryName)
                                }
                            )
                        }
                    }
                    else -> item {
                        Text(
                            text = "No categories here!",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
            LazyColumn {
                when (eventDataStatus) {
                    is EventDataStatusUIState.GetAllSuccess -> {
                        val filteredEvents = if (dataStatus is CategoryUIState.Success) {
                            val categoryId = dataStatus.data.find { it.name == selectedCategory }?.id
                            eventDataStatus.data.filter { event ->
                                categoryId == null || categoryId == event.category_id
                            }
                        } else {
                            eventDataStatus.data
                        }

                        if (filteredEvents.isEmpty()) {
                            item {
                                Text(
                                    text = "No events available for this category!",
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        } else {
                            items(filteredEvents.size) { index ->
                                val event = filteredEvents[index]
                                var username = ""
                                if (userDataStatus is AuthenticationStatusUIState.GotAllUser) {
                                    username = userDataStatus.userModelData
                                        .find { it.id == event.user_id }?.username ?: "Unknown User"
                                }

                                if (event.isOngoing) {
                                    EventCard(
                                        title = event.title,
                                        date = event.date,
                                        poster = event.poster,
                                        name = username,
                                        onClickCard = {
                                            navController.navigate(
                                                "${PagesEnum.EventDetail.name}/${event.id}/${event.user_id}"
                                            )
                                        },
                                        navController = navController
                                    )
                                }
                            }
                        }
                    }
                    else -> item {
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
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
                                text = "Home",
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
                HomeView(
                    categoryViewModel = viewModel(),
                    eventDetailViewModel = viewModel(),
                    authenticationViewModel = viewModel(),
                    navController = rememberNavController(),
                    token = "",
                    isAdmin = false
                )
            }
        }
    }
}