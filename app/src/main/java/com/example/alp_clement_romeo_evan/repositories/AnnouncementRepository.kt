package com.example.alp_clement_romeo_evan.repositories

import com.example.alp_clement_romeo_evan.models.AnnouncementRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllAnnouncementResponse
import com.example.alp_clement_romeo_evan.models.GetAnnouncementResponse
import com.example.alp_clement_romeo_evan.services.AnnouncementAPIService
import retrofit2.Call

interface AnnouncementRepository {
    fun getAllAnnouncements(token: String): Call<GetAllAnnouncementResponse>
    fun getAnnouncementById(token: String, announcementId: Int): Call<GetAnnouncementResponse>
    fun createAnnouncement(token: String, content: String, date: String, eventId: Int): Call<GeneralResponseModel>
    fun updateAnnouncement(token: String, announcementId: Int, content: String, date: String, eventId: Int): Call<GeneralResponseModel>
    fun deleteAnnouncement(token: String, announcementId: Int): Call<GeneralResponseModel>
}

class NetworkAnnouncementRepository(
    private val announcementAPIService: AnnouncementAPIService
) : AnnouncementRepository {
    override fun getAllAnnouncements(token: String): Call<GetAllAnnouncementResponse> {
        return announcementAPIService.getAllAnnouncements(token)
    }

    override fun getAnnouncementById(
        token: String,
        announcementId: Int
    ): Call<GetAnnouncementResponse> {
        return announcementAPIService.getAnnouncementById(token, announcementId)
    }

    override fun createAnnouncement(
        token: String,
        content: String,
        date: String,
        eventId: Int
    ): Call<GeneralResponseModel> {
        return announcementAPIService.createAnnouncement(
            token,
            AnnouncementRequest(content, date, eventId)
        )
    }

    override fun updateAnnouncement(
        token: String,
        announcementId: Int,
        content: String,
        date: String,
        eventId: Int
    ): Call<GeneralResponseModel> {
        return announcementAPIService.updateAnnouncement(
            token,
            announcementId,
            AnnouncementRequest(content, date, eventId)
        )
    }

    override fun deleteAnnouncement(
        token: String,
        announcementId: Int
    ): Call<GeneralResponseModel> {
        return announcementAPIService.deleteAnnouncement(token, announcementId)
    }
}