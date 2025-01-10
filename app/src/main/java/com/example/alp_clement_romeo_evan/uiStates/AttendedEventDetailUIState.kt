package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.AttendedEventModel

interface AttendedEventDetailUIState {
    data class Success(val data: AttendedEventModel): AttendedEventDetailUIState
    data class GotAll(val data: List<AttendedEventModel>): AttendedEventDetailUIState
    object Loading: AttendedEventDetailUIState
    object Start: AttendedEventDetailUIState
    data class Failed(val errorMessage: String): AttendedEventDetailUIState
}