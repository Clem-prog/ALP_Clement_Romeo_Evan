package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.EventModel

sealed interface EventDataStatusUIState {
    data class Success(val data: EventModel): EventDataStatusUIState
    data class GetAllSuccess(val data: List<EventModel>): EventDataStatusUIState
    object Start: EventDataStatusUIState
    object Loading: EventDataStatusUIState
    data class Failed(val errorMessage: String): EventDataStatusUIState
}