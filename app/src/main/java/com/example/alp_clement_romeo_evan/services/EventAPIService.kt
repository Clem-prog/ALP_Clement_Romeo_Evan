package com.example.alp_clement_romeo_evan.services

import com.example.alp_clement_romeo_evan.models.EventRequest
import com.example.alp_clement_romeo_evan.models.EventUpdateRequest
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
    @GET("api/events")
    fun getAllEvent (@Header("X-API-TOKEN") token: String): Call<GetAllEventResponse>

    @GET("api/events/{id}")
    fun getEventById (@Header("X-API-TOKEN") token: String, @Path("id") eventId: Int): Call<GetEventResponse>

    @POST("api/events")
    fun createEvent(@Header("X-API-TOKEN") token: String, @Body eventModel: EventRequest): Call<GetEventResponse>

    @PUT("api/events/{id}")
    fun updateEvent(@Header("X-API-TOKEN") token: String, @Path("id") eventId: Int, @Body eventModel: EventUpdateRequest): Call<GetEventResponse>

    @PUT("api/events/done/{id}")
    fun markEventAsDone(@Header("X-API-TOKEN") token: String, @Path("id") eventId: Int): Call<GetEventResponse>

    @DELETE("api/events/{id}")
    fun deleteEvent(@Header("X-API-TOKEN") token: String, @Path("id") eventId: Int): Call<GeneralResponseModel>
}

