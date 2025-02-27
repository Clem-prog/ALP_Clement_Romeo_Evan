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
    val event_id: Int = 0 // not sure about this part //trust in it
)

data class AnnouncementRequest (
    val content: String="",
    val date: String = "",
    val event_id: Int = 0 // not sure about this part //trust in it
)