package com.example.alp_clement_romeo_evan.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alp_clement_romeo_evan.WonderOfU
import com.example.alp_clement_romeo_evan.models.ErrorModel
import com.example.alp_clement_romeo_evan.models.GetAllEventResponse
import com.example.alp_clement_romeo_evan.models.GetEventResponse
import com.example.alp_clement_romeo_evan.repositories.EventRepository
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EventDetailViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    var dataStatus: EventDataStatusUIState by mutableStateOf(EventDataStatusUIState.Start)
        private set

    var deleteStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    fun getAllEvents(
        token: String
    ) {
        viewModelScope.launch {
            dataStatus = EventDataStatusUIState.Loading

            try {
                val call = eventRepository.getAllEvents(token)

                call.enqueue(object : Callback<GetAllEventResponse> {
                    override fun onResponse(
                        call: Call<GetAllEventResponse>,
                        res: Response<GetAllEventResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = EventDataStatusUIState.GetAllSuccess(res.body()!!.data)
                        } else {
                            val errorBody = res.errorBody()?.charStream()
                            val errorMessage = if (errorBody != null) {
                                Gson().fromJson(errorBody, ErrorModel::class.java)
                            } else {
                                null
                            }

                            val errorText = errorMessage?.errors ?: "Unknown error occurred"
                            dataStatus = EventDataStatusUIState.Failed(errorText)
                        }
                    }

                    override fun onFailure(call: Call<GetAllEventResponse>, t: Throwable) {
                        dataStatus = EventDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = EventDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun getEventDetails(token: String, eventId: Int) {
        viewModelScope.launch {
            dataStatus = EventDataStatusUIState.Loading

            try {
                val call = eventRepository.getEventById(token, eventId)

                call.enqueue(object : Callback<GetEventResponse> {
                    override fun onResponse(call: Call<GetEventResponse>, res: Response<GetEventResponse>) {
                        if (res.isSuccessful) {
                            dataStatus = EventDataStatusUIState.Success(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = EventDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetEventResponse>, t: Throwable) {
                        dataStatus = EventDataStatusUIState.Failed(t.localizedMessage)
                        Log.d("get-user-error", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (error: IOException) {
                dataStatus = EventDataStatusUIState.Failed(error.localizedMessage)
                Log.d("get-user-error", "GET USER ERROR: ${error.localizedMessage}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WonderOfU)
                val eventRepository = application.container.eventRepository
                EventDetailViewModel(eventRepository)
            }
        }
    }

    fun clearErrorMessage() {
        deleteStatus = StringDataStatusUIState.Start
    }
}