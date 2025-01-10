package com.example.alp_clement_romeo_evan.views


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.alp_clement_romeo_evan.uiStates.CategoryUIState
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.CategoryViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventDetailViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventFormViewModel
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventView(
    eventFormViewModel: EventFormViewModel,
    eventDetailViewModel: EventDetailViewModel,
    categoryViewModel: CategoryViewModel,
    navController: NavHostController,
    context: Context,
    event_id: Int,
    isEditing: Boolean,
    token: String
) {
    val context = LocalContext.current
    val dataStatus = categoryViewModel.dataStatus
    val eventDataStatus = eventDetailViewModel.dataStatus
    val createUIState by eventFormViewModel.eventUIState.collectAsState()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.timeInMillis
    )
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")  // Ensure it's in UTC timezone
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                eventFormViewModel.changePosterInput(it.toString())
                Log.d("Selected Image", "URI: $selectedImageUri")
            }
        }

    LaunchedEffect(token) {
        categoryViewModel.getAllCategories(token)
        eventFormViewModel.resetViewModel()
    }

    if (isEditing) {
        LaunchedEffect(event_id) {
            eventDetailViewModel.getEventDetails(token, event_id)
        }

        if (eventDataStatus is EventDataStatusUIState.Success) {
            LaunchedEffect(eventDataStatus) {
                eventFormViewModel.changeTitleInput(eventDataStatus.data.title)
                eventFormViewModel.changeDescriptionInput(eventDataStatus.data.description)
                eventFormViewModel.changeLocationInput(eventDataStatus.data.location)
                eventFormViewModel.changeDateInput(eventDataStatus.data.date)
                eventFormViewModel.changePosterInput(eventDataStatus.data.poster)
                eventFormViewModel.changeCategoryIdInput(eventDataStatus.data.category_id)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3FFD4))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Title Field
            TextField(
                value = eventFormViewModel.titleInput,
                onValueChange = {
                    eventFormViewModel.changeTitleInput(it)
                    eventFormViewModel.checkCreateForm()
                                },
                label = { Text("Event Title") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Description Field
            TextField(
                value = eventFormViewModel.descriptionInput,
                onValueChange = {
                    eventFormViewModel.changeDescriptionInput(it)
                    eventFormViewModel.checkCreateForm()
                                },
                label = { Text("Description") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                minLines = 3
            )

            // Location Field
            TextField(
                value = eventFormViewModel.locationInput,
                onValueChange = {
                    eventFormViewModel.changeLocationInput(it)
                    eventFormViewModel.checkCreateForm()
                },
                label = { Text("Location") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Date Field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                TextField(
                    value = eventFormViewModel.dateInput,
                    onValueChange = { },
                    label = { Text("Date") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f),
                    readOnly = true
                )
                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF9FFC9),
                        contentColor = Color.Black
                    ),
                ) {
                    Text("Select Date")
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let {
                                selectedDate.timeInMillis = it
                                eventFormViewModel.changeDateInput(dateFormatter.format(selectedDate.time))
                                eventFormViewModel.checkCreateForm()
                            }
                            showDatePicker = false
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }

            // Poster URL Field
            Button(
                onClick = {
                    imagePickerLauncher.launch("image/*")
                    eventFormViewModel.checkCreateForm()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF9FFC9),
                    contentColor = Color.Black
                ),
            ) {
                Text("Select Poster Image")
            }

            // Display selected image path
            selectedImageUri?.let {
                Column {
                    Text("Selected Image:")
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "picked Image",
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    )
                }
            }

            var expanded by remember { mutableStateOf(false) }
            if (dataStatus is CategoryUIState.Success) {
                val categories = dataStatus.data

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    TextField(
                        value = categories.find { it.id == eventFormViewModel.categoryIdInput }?.name
                            ?: "",
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    // Dropdown menu
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    eventFormViewModel.changeCategoryIdInput(category.id)
                                    eventFormViewModel.checkCreateForm()
                                    Log.d("Form State", "Title: ${eventFormViewModel.titleInput}, Description: ${eventFormViewModel.descriptionInput}, Location: ${eventFormViewModel.locationInput}, date: ${eventFormViewModel.dateInput}, poster: ${eventFormViewModel.posterInput}, category id: ${eventFormViewModel.categoryIdInput}")
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (isEditing) {
                        selectedImageUri?.let { uri ->
                            val uploadSuccess = eventFormViewModel.uploadPoster(uri, context)
                            if (uploadSuccess) {
                                Log.d("Image Upload", "Image uploaded successfully.")
                            } else {
                                Log.e("Upload Error", "Image upload failed.")
                            }
                        }

                        eventFormViewModel.updateEvent(navController, token, event_id)
                    } else {
                        selectedImageUri?.let { uri ->
                            val uploadSuccess = eventFormViewModel.uploadPoster(uri, context)
                            if (uploadSuccess) {
                                Log.d("Image Upload", "Image uploaded successfully.")
                                eventFormViewModel.createEvent(navController, token)
                            } else {
                                Log.e("Upload Error", "Image upload failed. Event creation aborted.")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF9FFC9),
                    contentColor = Color.Black
                ),
                enabled = createUIState.createButtonEnabled
            ) {
                Text("Create Event")
            }

            // Reset Form Button
            TextButton(
                onClick = { eventFormViewModel.resetViewModel() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Reset Form")
            }
        }
    }
}
