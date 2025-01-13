package com.example.alp_clement_romeo_evan.services

import com.example.alp_clement_romeo_evan.models.AttendedEventRequest
import com.example.alp_clement_romeo_evan.models.EventRequest
import com.example.alp_clement_romeo_evan.models.GetAllAttendedEventResponse
import com.example.alp_clement_romeo_evan.models.GetAttendedEventResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AttendedEventAPIService {

    @GET("/api/attendance") // route for all this isnt final, will be finish tomorrow
    fun getAllAttendedEvents (@Header("X-API-TOKEN") token: String): Call<GetAllAttendedEventResponse>

    @GET("/api/attendance/event/{id}")
    fun getAllEventMembers (@Header("X-API-TOKEN") token: String, @Path("id") eventId: Int): Call<GetAllAttendedEventResponse>

    @POST("/api/attendance")
    fun createAttendedEvent(@Header("X-API-TOKEN") token: String, @Body attendedEventModel: AttendedEventRequest): Call<GetAttendedEventResponse>

}