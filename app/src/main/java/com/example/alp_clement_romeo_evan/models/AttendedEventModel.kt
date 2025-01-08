package com.example.alp_clement_romeo_evan.models

data class GetAllAttendedEventResponse (
    val data : List<AttendedEventModel>
)

data class GetAttendedEventResponse (
    val data : AttendedEventModel
)

data class AttendedEventModel(
    val id: Int = 0,
    val date_signed: String = "",
    val user_id: Int = 0,
    val event_id: Int = 0
)

data class AttendedEventRequest(
    val date_signed: String = "",
    val user_id: Int = 0,
    val event_id: Int = 0
)