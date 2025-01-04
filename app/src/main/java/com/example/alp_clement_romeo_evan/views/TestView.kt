package com.example.alp_clement_romeo_evan.views



import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_clement_romeo_evan.uiStates.StringDataStatusUIState
import com.example.alp_clement_romeo_evan.viewModels.EventFormViewModel
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestView(
    viewModel: EventFormViewModel,
    navController: NavHostController = rememberNavController()
) {
    val submissionStatus = viewModel.submissionStatus
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title Field
        TextField(
            value = viewModel.titleInput,
            onValueChange = { viewModel.changeTitleInput(it) },
            label = { Text("Event Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Description Field
        TextField(
            value = viewModel.descriptionInput,
            onValueChange = { viewModel.changeDescriptionInput(it) },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            minLines = 3
        )

        // Location Field
        TextField(
            value = viewModel.locationInput,
            onValueChange = { viewModel.changeLocationInput(it) },
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
                value = viewModel.dateInput,
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
                        viewModel.changeDateInput(dateFormatter.format(selectedDate.time))
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
            value = viewModel.posterInput,
            onValueChange = { viewModel.changePosterInput(it) },
            label = { Text("Poster URL") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Category Dropdown
        var expanded by remember { mutableStateOf(false) }
        val categories = listOf(
            1 to "Category 1",
            2 to "Category 2",
            3 to "Category 3"
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TextField(
                value = categories.find { it.first == viewModel.categoryIdInput }?.second ?: "",
                onValueChange = { },
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { (id, name) ->
                    DropdownMenuItem(
                        text = { Text(name) },
                        onClick = {
                            viewModel.changeCategoryIdInput(id)
                            expanded = false
                        }
                    )
                }
            }
        }

        // Submit Button
        Button(
            onClick = {
                viewModel.createEvent(navController, "your-test-token-here")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Create Event")
        }

        // Status Messages
        when (submissionStatus) {
            is StringDataStatusUIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
            is StringDataStatusUIState.Failed -> {
                Text(
                    text = "error kak", // Changed from message to error
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            is StringDataStatusUIState.Success -> {
                Text(
                    text = "Event created successfully!",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            else -> { /* Start state, nothing to show */ }
        }

        // Reset Form Button
        TextButton(
            onClick = { viewModel.resetViewModel() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Reset Form")
        }
    }
}