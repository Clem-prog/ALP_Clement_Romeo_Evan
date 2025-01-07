package com.example.alp_clement_romeo_evan.services


import com.example.alp_clement_romeo_evan.models.AnnouncementRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllAnnouncementResponse
import com.example.alp_clement_romeo_evan.models.GetAnnouncementResponse
import retrofit2.Call
import retrofit2.http.*

interface AnnouncementAPIService {
    @GET("api/announcements") fun getAllAnnouncements(@Header("X-API-TOKEN") token: String): Call<GetAllAnnouncementResponse>

    @GET("api/announcements/{id}") fun getAnnouncementById(@Header("X-API-TOKEN") token: String, @Path("id") id: Int): Call<GetAnnouncementResponse>

    @POST("api/announcements") fun createAnnouncement(@Header("X-API-TOKEN") token: String, @Body request: AnnouncementRequest): Call<GetAnnouncementResponse>

    @PUT("api/announcements/{id}") fun updateAnnouncement(@Header("X-API-TOKEN") token: String, @Path("id") id: Int, @Body request: AnnouncementRequest): Call<GetAnnouncementResponse>

    @DELETE("api/announcements/{id}") fun deleteAnnouncement(@Header("X-API-TOKEN") token: String, @Path("id") id: Int): Call<GeneralResponseModel>
}