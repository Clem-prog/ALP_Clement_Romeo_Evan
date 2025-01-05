package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.EventModel

sealed interface EventDetailStatusUIState {
    data class Success(val data: EventModel): EventDetailStatusUIState
    object Loading: EventDetailStatusUIState
    object Start: EventDetailStatusUIState
    data class Failed(val errorMessage: String): EventDetailStatusUIState
}

