package com.example.alp_clement_romeo_evan.views



import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.uiStates.CategoryUIState
import com.example.alp_clement_romeo_evan.uiStates.StringDataStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.CategoryViewModel
import com.example.alp_clement_romeo_evan.viewModels.EventFormViewModel
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestView(
    eventFormViewModel: EventFormViewModel,
    categoryViewModel: CategoryViewModel,
    navController: NavHostController,
    token: String
) {
    val submissionStatus = eventFormViewModel.submissionStatus
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    LaunchedEffect(token) {
        categoryViewModel.getAllCategories(token)
    }
    val dataStatus = categoryViewModel.dataStatus

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title Field
        TextField(
            value = eventFormViewModel.titleInput,
            onValueChange = { eventFormViewModel.changeTitleInput(it) },
            label = { Text("Event Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Description Field
        TextField(
            value = eventFormViewModel.descriptionInput,
            onValueChange = { eventFormViewModel.changeDescriptionInput(it) },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            minLines = 3
        )

        // Location Field
        TextField(
            value = eventFormViewModel.locationInput,
            onValueChange = { eventFormViewModel.changeLocationInput(it) },
            label = { Text("Location") },
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
                modifier = Modifier.weight(1f),
                readOnly = true
            )
            Button(
                onClick = { showDatePicker = true },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Select Date")
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        eventFormViewModel.changeDateInput(dateFormatter.format(selectedDate.time))
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
                    state = rememberDatePickerState()
                )
            }
        }

        // Poster URL Field
        TextField(
            value = eventFormViewModel.posterInput,
            onValueChange = { eventFormViewModel.changePosterInput(it) },
            label = { Text("Poster URL") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Category Dropdown
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
                // Find the selected category name or show empty string
                TextField(
                    value = categories.find { it.id == eventFormViewModel.categoryIdInput }?.name ?: "",
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
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        // Submit Button
        Button(
            onClick = {
                eventFormViewModel.createEvent(navController, token)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
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