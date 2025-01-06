package com.example.alp_clement_romeo_evan.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.example.alp_clement_romeo_evan.models.GetEventResponse
import com.example.alp_clement_romeo_evan.repositories.EventRepository
import com.example.alp_clement_romeo_evan.repositories.UserRepository
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.EventFormUIState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class EventFormViewModel(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _eventListState = MutableStateFlow(EventFormUIState())
    val eventListState: StateFlow<EventFormUIState> = _eventListState.asStateFlow()

    var eventId by mutableStateOf(0)
        private set

    var submissionStatus: EventDataStatusUIState by mutableStateOf(EventDataStatusUIState.Start)
        private set

    var titleInput by mutableStateOf("")
        private set

    var isOngoingInput by mutableStateOf(false)
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

    fun changeIsOngoingInput(isOngoing: Boolean) {
        isOngoingInput = isOngoing
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

    fun createEvent(navController: NavHostController, token: String, context: Context) {
        viewModelScope.launch {
            submissionStatus = EventDataStatusUIState.Loading

            Log.d("token-event-list-form", "TOKEN: ${token}")

            try {
                val call = eventRepository.createEvent(
                    token,
                    titleInput,
                    isOngoingInput,
                    descriptionInput,
                    locationInput,
                    dateInput,
                    posterInput,
                    categoryIdInput
                )

                call.enqueue(object : Callback<GetEventResponse> {
                    override fun onResponse(
                        call: Call<GetEventResponse>,
                        res: Response<GetEventResponse>
                    ) {
                        if (res.isSuccessful) {
                            Log.d("json", "JSON RESPONSE: ${res.body()!!.data}")
                            submissionStatus = EventDataStatusUIState.Success(res.body()!!.data)

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

                            Log.d("event-create", "Error Response: $errorMessage")
                            submissionStatus = EventDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetEventResponse>, t: Throwable) {
                        submissionStatus = EventDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                submissionStatus = EventDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun clearErrorMessage() {
        submissionStatus = EventDataStatusUIState.Start
    }

    fun resetViewModel() {
        submissionStatus = EventDataStatusUIState.Start
        titleInput = ""
        isOngoingInput = false
        descriptionInput = ""
        locationInput = ""
        dateInput = ""
        posterInput = ""
        categoryIdInput = 0
    }

    fun uploadPoster(uri: Uri, context: Context): Boolean {
        var uploadSuccess = false
        runBlocking { // Block the current thread until the upload completes
            try {
                val url = uploadPosterToCloudinary(uri, context)
                if (url.isNotEmpty()) {
                    posterInput = url // Store the URL after uploading
                    Log.d("EventFormViewModel", "Poster uploaded successfully: $posterInput")
                    uploadSuccess = true
                } else {
                    Log.d("EventFormViewModel", "Poster upload failed, URL is empty.")
                }
            } catch (e: Exception) {
                Log.e("EventFormViewModel", "Upload failed: ${e.localizedMessage}")
            }
        }
        return uploadSuccess
    }


    private suspend fun uploadPosterToCloudinary(posterUri: Uri, context: Context): String {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()

                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(posterUri)
                    ?: throw IOException("Failed to open InputStream for URI: $posterUri")

                val mediaType = "image/*".toMediaTypeOrNull()

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        "image.jpg",
                        inputStream.readBytes().toRequestBody(mediaType)
                    )
                    .addFormDataPart("upload_preset", "event_posters")
                    .build()

                val request = Request.Builder()
                    .url("https://api.cloudinary.com/v1_1/dggn8axkq/image/upload")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (!responseBody.isNullOrEmpty()) {
                        val jsonResponse = JSONObject(responseBody)
                        val secureUrl = jsonResponse.getString("secure_url")
                        Log.d("Cloudinary Upload", "Image uploaded to: $secureUrl")
                        return@withContext secureUrl
                    }
                } else {
                    Log.e("Cloudinary Upload", "Upload failed: ${response.message}")
                    throw IOException("Failed to upload image to Cloudinary: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("Cloudinary Upload", "Exception: ${e.localizedMessage}")
                throw IOException("Error uploading image: ${e.localizedMessage}")
            }
            return@withContext ""
        }
    }

}


