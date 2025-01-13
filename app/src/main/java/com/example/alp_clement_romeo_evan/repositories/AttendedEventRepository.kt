package com.example.alp_clement_romeo_evan.repositories

import android.util.Log
import com.example.alp_clement_romeo_evan.models.AttendedEventRequest
import com.example.alp_clement_romeo_evan.models.GetAllAttendedEventResponse
import com.example.alp_clement_romeo_evan.models.GetAttendedEventResponse
import com.example.alp_clement_romeo_evan.services.AttendedEventAPIService
import retrofit2.Call

interface AttendedEventRepository {
    fun getAllAttendedEvents(token: String): Call<GetAllAttendedEventResponse>
    fun createAttendedEvent(
        token: String,
        dateSigned: String,
        eventId: Int
    ): Call<GetAttendedEventResponse>
    fun getAllEventMembers(token: String, eventId: Int): Call<GetAllAttendedEventResponse>
}

class NetworkAttendedEventRepository(
    private val attendedEventAPIService: AttendedEventAPIService
) : AttendedEventRepository {
    override fun getAllAttendedEvents(token: String): Call<GetAllAttendedEventResponse> {
        return attendedEventAPIService.getAllAttendedEvents(token)
    }

    override fun createAttendedEvent(
        token: String,
        dateSigned: String,
        eventId: Int
    ): Call<GetAttendedEventResponse> {
        Log.d("API Request", "Token: $token, Event ID: $eventId, Date Signed: $dateSigned")
        return attendedEventAPIService.createAttendedEvent(
            token,
            AttendedEventRequest(dateSigned, eventId)
        )
    }

    override fun getAllEventMembers(token: String, eventId: Int): Call<GetAllAttendedEventResponse> {
        return attendedEventAPIService.getAllEventMembers(token, eventId)
    }
}
