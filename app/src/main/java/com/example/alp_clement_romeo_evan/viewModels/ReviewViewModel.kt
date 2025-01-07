package com.example.alp_clement_romeo_evan.viewModels

import android.content.Context
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
import com.example.alp_clement_romeo_evan.models.GetAllReviewResponse
import com.example.alp_clement_romeo_evan.models.GetReviewResponse
import com.example.alp_clement_romeo_evan.repositories.EventRepository
import com.example.alp_clement_romeo_evan.repositories.ReviewRepository
import com.example.alp_clement_romeo_evan.repositories.UserRepository
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.ReviewDataStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.ReviewUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ReviewViewModel(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,

) : ViewModel() {

    private val _ReviewListState = MutableStateFlow(ReviewUIState())
    val ReviewListState: StateFlow<ReviewUIState> = _ReviewListState.asStateFlow()

    var reviewId by mutableStateOf(0)
        private set

    var userId by mutableStateOf(0)
        private set

    var eventId by mutableStateOf(0)
        private set

    var userIdInput by mutableStateOf(0)
        private set

    var eventIdInput by mutableStateOf(0)
        private set

    var dataStatus: ReviewDataStatusUIState by mutableStateOf(ReviewDataStatusUIState.Start)
        private set

    var deleteStatus: ReviewDataStatusUIState by mutableStateOf(ReviewDataStatusUIState.Start)
        private set

    var submissionStatus: ReviewDataStatusUIState by mutableStateOf(ReviewDataStatusUIState.Start)
        private set

    var titleInput by mutableStateOf("")
        private set

    var commentInput by mutableStateOf("")
        private set
    
    var ratingInput by mutableStateOf(0)
        private set
    
    fun changeTitleInput(title: String) {
        titleInput = title
    }
    
    fun changeRatingInput(rating: Int) {
        ratingInput = rating
    }

    fun changeCommentInput(comment: String) {
        commentInput = comment
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WonderOfU)
                val eventRepository = application.container.eventRepository
                val userRepository = application.container.userRepository
                val reviewRepository = application.container.reviewRepository
                ReviewViewModel(eventRepository, userRepository, reviewRepository)
            }
        }
    }

    fun createReview(navController: NavHostController, token: String, context: Context) {
        viewModelScope.launch {
            submissionStatus = ReviewDataStatusUIState.Loading

            Log.d("token-event-list-form", "TOKEN: ${token}")

            try {
                val call = reviewRepository.createReview(
                    token,
                    userIdInput,
                    eventIdInput,
                    titleInput,
                    ratingInput,
                    commentInput
                )

                call.enqueue(object : Callback<GetReviewResponse> {
                    override fun onResponse(
                        call: Call<GetReviewResponse>,
                        res: Response<GetReviewResponse>
                    ) {
                        if (res.isSuccessful) {
                            Log.d("json", "JSON RESPONSE: ${res.body()!!.data}")
                            submissionStatus = ReviewDataStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.CreateReview.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("event-create", "Error Response: $errorMessage")
                            submissionStatus = ReviewDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetReviewResponse>, t: Throwable) {
                        submissionStatus = ReviewDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                submissionStatus = ReviewDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun updateReview(token: String, getReview: () -> Unit) {
        viewModelScope.launch {
            submissionStatus = ReviewDataStatusUIState.Loading

            try {
                val call = reviewRepository.updateReview(token, reviewId,userId, eventId, titleInput, ratingInput, commentInput)

                call.enqueue(object: Callback<GetReviewResponse>  {
                    override fun onResponse(
                        call: Call<GetReviewResponse>,
                        res: Response<GetReviewResponse>
                    )  {
                        if (res.isSuccessful) {
                            submissionStatus = ReviewDataStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            getReview()
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            submissionStatus = ReviewDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetReviewResponse>, t: Throwable) {
                        submissionStatus = ReviewDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                submissionStatus = ReviewDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun deleteEvent(token: String, reviewId: Int, navController: NavHostController) {
        viewModelScope.launch {
            submissionStatus = ReviewDataStatusUIState.Loading

            try {
                val call = reviewRepository.deleteReview(token, reviewId)

                call.enqueue(object: Callback<GetReviewResponse> {
                    override fun onResponse(
                        call: Call<GetReviewResponse>,
                        res: Response<GetReviewResponse>
                    ) {
                        if (res.isSuccessful) {
                            submissionStatus = ReviewDataStatusUIState.Success(res.body()!!.data)

                            Log.d("delete-status", "Delete status: ${res.body()!!.data}")

                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            submissionStatus = ReviewDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetReviewResponse>, t: Throwable) {
                        submissionStatus = ReviewDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                submissionStatus = ReviewDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun resetViewModel() {
        submissionStatus = ReviewDataStatusUIState.Start
        userIdInput = 0
        eventIdInput = 0
        titleInput = ""
        ratingInput = 0
        commentInput = ""
    }
}
