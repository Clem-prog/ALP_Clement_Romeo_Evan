package com.example.alp_clement_romeo_evan.models

data class GetAllAnnouncementResponse (
    val data : List<AnnouncementModel>
)

data class GetAnnouncementResponse (
    val data : AnnouncementModel
)

data class AnnouncementModel (
    val id: Int = 0 ,
    val content: String="",
    val date: String = "",
    val eventId: Int = 0 // not sure about this part
)

data class AnnouncementRequest (
    val name: String="",
    val date: String = "",
    val eventId: Int = 0 // not sure about this part
)