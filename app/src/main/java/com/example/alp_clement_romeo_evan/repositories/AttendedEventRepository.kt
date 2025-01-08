package com.example.alp_clement_romeo_evan.repositories

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
        userId: Int,
        eventId: Int
    ): Call<GetAttendedEventResponse>
    fun getAttendedEventById(token: String, attendedEventId: Int): Call<GetAttendedEventResponse>
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
        userId: Int,
        eventId: Int
    ): Call<GetAttendedEventResponse> {
        return attendedEventAPIService.createAttendedEvent(
            token,
            AttendedEventRequest(date_signed = dateSigned, user_id = userId, event_id = eventId)
        )
    }

    override fun getAttendedEventById(token: String, attendedEventId: Int): Call<GetAttendedEventResponse> {
        return attendedEventAPIService.getAttendedEventById(token, attendedEventId)
    }

}
