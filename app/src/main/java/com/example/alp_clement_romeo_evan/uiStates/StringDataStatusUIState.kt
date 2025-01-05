package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.EventModel

sealed interface StringDataStatusUIState {
    data class Success(val data: String): StringDataStatusUIState
    object Start: StringDataStatusUIState
    object Loading: StringDataStatusUIState
    data class Failed(val errorMessage: String): StringDataStatusUIState
}