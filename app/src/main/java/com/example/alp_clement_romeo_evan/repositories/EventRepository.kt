package com.example.alp_clement_romeo_evan.repositories
import com.example.alp_clement_romeo_evan.models.EventRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllEventResponse
import com.example.alp_clement_romeo_evan.models.GetEventResponse
import com.example.alp_clement_romeo_evan.services.EventAPIService
import retrofit2.Call

interface EventRepository {
    fun getAllEvents(token: String): Call<GetAllEventResponse>

    fun createEvent(token: String, title: String, description: String, location: String, date: String, poster: String, categoryId: Int): Call<GeneralResponseModel>

    fun getEventById(token: String, eventId: Int): Call<GetEventResponse>

    fun updateEvent(token: String, eventId: Int, title: String, description: String, location: String, date: String, poster: String, categoryId: Int): Call<GeneralResponseModel>

    fun deleteEvent(token: String, eventId: Int): Call<GeneralResponseModel>
}

class NetworkEventRepository(
    private val eventAPIService: EventAPIService
): EventRepository {
    override fun getAllEvents(token: String): Call<GetAllEventResponse> {
        return eventAPIService.getALlEvent(token)
    }

    override fun createEvent(
        token: String,
        title: String,
        description: String,
        location: String,
        date: String,
        poster: String,
        categoryId: Int
    ): Call<GeneralResponseModel> {
        return eventAPIService.createEvent(
            token,
            EventRequest(title, description, location, date, poster, categoryId)
        )
    }

    override fun getEventById(token: String, eventId: Int): Call<GetEventResponse> {
        return eventAPIService.getEventById(token, eventId)
    }

    override fun updateEvent(
        token: String,
        eventId: Int,
        title: String,
        description: String,
        location: String,
        date: String,
        poster: String,
        categoryId: Int
    ): Call<GeneralResponseModel> {
        return eventAPIService.updateEvent(
            token,
            eventId,
            EventRequest(title, description, location, date, poster, categoryId)
        )
    }

    override fun deleteEvent(token: String, eventId: Int): Call<GeneralResponseModel> {
        return eventAPIService.deleteEvent(token, eventId)
    }
}
