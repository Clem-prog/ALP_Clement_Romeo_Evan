package com.example.alp_clement_romeo_evan.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.alp_clement_romeo_evan.WonderOfU
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.models.GetAllAnnouncementResponse
import com.example.alp_clement_romeo_evan.models.ErrorModel
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAnnouncementResponse
import com.example.alp_clement_romeo_evan.models.GetEventResponse
import com.example.alp_clement_romeo_evan.repositories.AnnouncementRepository
import com.example.alp_clement_romeo_evan.repositories.EventRepository
import com.example.alp_clement_romeo_evan.uiStates.AnnouncementStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AnnouncementViewModel (
    private val announcementRepository: AnnouncementRepository
): ViewModel() {
    private val _authenticationUIState = MutableStateFlow(AuthenticationUIState())

    val authenticationUIState: StateFlow<AuthenticationUIState>
        get() {
            return _authenticationUIState.asStateFlow()
        }
    
    var dataStatus: AnnouncementStatusUIState by mutableStateOf(AnnouncementStatusUIState.Start)
        private set

    var contentInput by mutableStateOf("")
        private set

    var dateInput by mutableStateOf("")
        private set

    fun changeContentInput(contentInput: String) {
        this.contentInput = contentInput
    }

    fun changeDateInput(dateInput: String) {
        this.dateInput = dateInput
    }

    fun checkAnnouncementForm() {
        if (contentInput.isNotEmpty()) {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = true
                )
            }
        } else {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    fun createAnnouncement(token: String, content: String, event_id: Int, date: String, navController: NavHostController) {
        viewModelScope.launch {
            dataStatus = AnnouncementStatusUIState.Loading

            Log.d("ANNOUNCEMENT-CREATION", "TOKEN: ${token}")

            try {
                val call = announcementRepository.createAnnouncement(token, content, date, event_id)

                call.enqueue(object : Callback<GetAnnouncementResponse> {
                    override fun onResponse(
                        call: Call<GetAnnouncementResponse>,
                        res: Response<GetAnnouncementResponse>
                    ) {
                        if (res.isSuccessful) {
                            Log.d("json", "JSON RESPONSE: ${res.body()!!.data}")
                            dataStatus = AnnouncementStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Announcements.name) {
                                popUpTo(PagesEnum.CreateAnnouncement.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("event-create", "Error Response: $errorMessage")
                            dataStatus = AnnouncementStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAnnouncementResponse>, t: Throwable) {
                        dataStatus = AnnouncementStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = AnnouncementStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun getAllAnnouncement(token: String) {
        viewModelScope.launch {
            dataStatus = AnnouncementStatusUIState.Loading

            try {
                val call = announcementRepository.getAllAnnouncements(token)

                call.enqueue(object : Callback<GetAllAnnouncementResponse> {
                    override fun onResponse(
                        call: Call<GetAllAnnouncementResponse>,
                        res: Response<GetAllAnnouncementResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = AnnouncementStatusUIState.GotAll(res.body()!!.data)
                        } else {
                            val errorBody = res.errorBody()?.charStream()
                            val errorMessage = if (errorBody != null) {
                                Gson().fromJson(errorBody, ErrorModel::class.java)
                            } else {
                                null
                            }

                            val errorText = errorMessage?.errors ?: "Unknown error occurred"
                            dataStatus = AnnouncementStatusUIState.Failed(errorText)
                        }
                    }

                    override fun onFailure(call: Call<GetAllAnnouncementResponse>, t: Throwable) {
                        dataStatus = AnnouncementStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = AnnouncementStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun getAnnouncementById(token: String, announcementId: Int) {
        viewModelScope.launch {
            dataStatus = AnnouncementStatusUIState.Loading

            try {
                val call = announcementRepository.getAnnouncementById(token, announcementId)

                call.enqueue(object : Callback<GetAnnouncementResponse> {
                    override fun onResponse(
                        call: Call<GetAnnouncementResponse>,
                        res: Response<GetAnnouncementResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = AnnouncementStatusUIState.Success(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            dataStatus = AnnouncementStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAnnouncementResponse>, t: Throwable) {
                        dataStatus = AnnouncementStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = AnnouncementStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun updateAnnouncement(token: String, announcementId: Int, content: String, navController: NavHostController) {
        viewModelScope.launch {
            dataStatus = AnnouncementStatusUIState.Loading

            try {
                val call = announcementRepository.updateAnnouncement(token, announcementId, content)

                call.enqueue(object : Callback<GetAnnouncementResponse> {
                    override fun onResponse(
                        call: Call<GetAnnouncementResponse>,
                        res: Response<GetAnnouncementResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = AnnouncementStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Announcements.name) {
                                popUpTo(PagesEnum.CreateAnnouncement.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            dataStatus = AnnouncementStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAnnouncementResponse>, t: Throwable) {
                        dataStatus = AnnouncementStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = AnnouncementStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun deleteAnnouncement(token: String, announcementId: Int) {
        viewModelScope.launch {
            dataStatus = AnnouncementStatusUIState.Loading

            try {
                val call = announcementRepository.deleteAnnouncement(token, announcementId)

                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = AnnouncementStatusUIState.Deleted(res.body()!!.data)

                            getAllAnnouncement(token)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            dataStatus = AnnouncementStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        dataStatus = AnnouncementStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = AnnouncementStatusUIState.Failed(error.localizedMessage)
            }
        }
    }



    fun resetViewModel() {
        changeContentInput("")
        _authenticationUIState.update { currentState ->
            currentState.copy(
                buttonEnabled = false
            )
        }
        dataStatus = AnnouncementStatusUIState.Start
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WonderOfU)
                val announcementRepository = application.container.announcementRepository
                AnnouncementViewModel(announcementRepository)

            }
        }
    }
}