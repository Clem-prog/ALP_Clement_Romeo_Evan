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
import androidx.navigation.NavHostController
import com.example.alp_clement_romeo_evan.WonderOfU
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.models.ErrorModel
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.repositories.EventRepository
import com.example.alp_clement_romeo_evan.repositories.UserRepository
import com.example.alp_clement_romeo_evan.uiStates.EventFormUIState
import com.example.alp_clement_romeo_evan.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EventFormViewModel(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _eventListState = MutableStateFlow(EventFormUIState())
    val eventListState: StateFlow<EventFormUIState> = _eventListState.asStateFlow()

    var eventId by mutableStateOf(-1)
        private set

    var submissionStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    var titleInput by mutableStateOf("")
        private set

    var descriptionInput by mutableStateOf("")
        private set

    var locationInput by mutableStateOf("")
        private set

    var dateInput by mutableStateOf("")
        private set

    var posterInput by mutableStateOf("")
        private set

    var categoryIdInput by mutableStateOf(1)
        private set

    fun changeTitleInput(title: String) {
        titleInput = title
    }

    fun changeDescriptionInput(description: String) {
        descriptionInput = description
    }

    fun changeLocationInput(location: String) {
        locationInput = location
    }

    fun changeDateInput(date: String) {
        dateInput = date
    }

    fun changePosterInput(poster: String) {
        posterInput = poster
    }

    fun changeCategoryIdInput(categoryId: Int) {
        categoryIdInput = categoryId
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WonderOfU)
                val eventRepository = application.container.eventRepository
                val userRepository = application.container.userRepository
                EventFormViewModel(eventRepository, userRepository)
            }
        }
    }

    fun createEvent(navController: NavHostController, token: String) {
        viewModelScope.launch {
            submissionStatus = StringDataStatusUIState.Loading

            Log.d("token-event-list-form", "TOKEN: ${token}")

            try {
                val call = eventRepository.createEvent(token, titleInput, descriptionInput, locationInput, dateInput, posterInput, categoryIdInput)


                call.enqueue(object: Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            Log.d("json", "JSON RESPONSE: ${res.body()!!.data}")
                            submissionStatus = StringDataStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.CreateEvent.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            submissionStatus = StringDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                submissionStatus = StringDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun clearErrorMessage() {
        submissionStatus = StringDataStatusUIState.Start
    }

    fun resetViewModel() {
        submissionStatus = StringDataStatusUIState.Start
        titleInput = ""
        descriptionInput = ""
        locationInput = ""
        dateInput = ""
        posterInput = ""
        categoryIdInput = 0

    }
}


