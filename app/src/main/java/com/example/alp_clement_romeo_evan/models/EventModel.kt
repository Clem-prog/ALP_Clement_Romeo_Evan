package com.example.alp_clement_romeo_evan.models

data class GetAllEventResponse (
   val data : List<EventModel>
)

data class GetEventResponse (
    val data : EventModel
)

data class EventModel(
    val id: Int,
    val title: String,
    val isOngoing: Boolean,
    val description: String,
    val location: String,
    val date: String,
    val poster: String,
    val category_id: Int
)


data class EventRequest(
    val title: String,
    val isOngoing: Boolean,
    val description: String,
    val location: String,
    val date: String,
    val poster: String,
    val category_id: Int
)