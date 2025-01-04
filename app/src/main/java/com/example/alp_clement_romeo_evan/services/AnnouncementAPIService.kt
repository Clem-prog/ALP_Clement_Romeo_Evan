package com.example.alp_clement_romeo_evan.services


import com.example.alp_clement_romeo_evan.models.AnnouncementRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllAnnouncementResponse
import com.example.alp_clement_romeo_evan.models.GetAnnouncementResponse
import retrofit2.Call
import retrofit2.http.*

interface AnnouncementAPIService { @GET("announcements") fun getAllAnnouncements(@Header("Authorization") token: String): Call<GetAllAnnouncementResponse>

    @GET("announcements/{id}") fun getAnnouncementById(@Header("Authorization") token: String, @Path("id") id: Int): Call<GetAnnouncementResponse>

    @POST("announcements") fun createAnnouncement(@Header("Authorization") token: String, @Body request: AnnouncementRequest): Call<GeneralResponseModel>

    @PUT("announcements/{id}") fun updateAnnouncement(@Header("Authorization") token: String, @Path("id") id: Int, @Body request: AnnouncementRequest): Call<GeneralResponseModel>

    @DELETE("announcements/{id}") fun deleteAnnouncement(@Header("Authorization") token: String, @Path("id") id: Int): Call<GeneralResponseModel>
}