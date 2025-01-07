package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.AnnouncementModel

interface AnnouncementStatusUIState {
    data class Success(val data: AnnouncementModel): AnnouncementStatusUIState
    data class Deleted(val data: String): AnnouncementStatusUIState
    data class GotAll(val data: List<AnnouncementModel>): AnnouncementStatusUIState
    object Loading: AnnouncementStatusUIState
    object Start: AnnouncementStatusUIState
    data class Failed(val errorMessage: String): AnnouncementStatusUIState
}