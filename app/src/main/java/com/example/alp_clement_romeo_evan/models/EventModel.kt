package com.example.alp_clement_romeo_evan.models

data class GetAllEventResponse (
   val data : List<EventModel>
)

data class GetEventResponse (
    val data : EventModel
)

data class EventModel(

    val id: Int = 0,
    val title: String = "",
    val isOngoing: Boolean = false,
    val description: String ="",
    val location: String = "",
    val date: String = "",
    val poster: String = "",
    val category_id: Int = 0,
    val user_id: Int = 0

)

data class EventRequest(
    val title: String = "",
    val isOngoing: Boolean = false,
    val description: String = "",
    val location: String = "",
    val date: String = "",
    val poster: String = "",
    val category_id: Int = 0,
    val user_id: Int = 0
)