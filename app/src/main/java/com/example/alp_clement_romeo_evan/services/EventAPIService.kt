package com.example.alp_clement_romeo_evan.services

import com.example.alp_clement_romeo_evan.models.EventModel
import com.example.alp_clement_romeo_evan.models.EventRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllEventResponse
import com.example.alp_clement_romeo_evan.models.GetEventResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventAPIService {
    @GET("api/event/all")
    fun getAllEvent (@Header("X-API-TOKEN") token: String): Call<GetAllEventResponse>

    @GET("api/event/{id}")
    fun getEventById (@Header("X-API-TOKEN") token: String, @Path("id") eventId: Int): Call<GetEventResponse>

    @POST("api/event")
    fun createEvent(@Header("X-API-TOKEN") token: String, @Body eventModel: EventRequest): Call<GeneralResponseModel>

    @PUT("api/event/{id}")
    fun updateEvent(@Header("X-API-TOKEN") token: String, @Path("id") eventId: Int, @Body eventModel: EventRequest): Call<GeneralResponseModel>

    @DELETE("api/event/{id}")
    fun deleteEvent(@Header("X-API-TOKEN") token: String, @Path("id") eventId: Int): Call<GeneralResponseModel>
}

