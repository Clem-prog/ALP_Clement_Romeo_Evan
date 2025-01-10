package com.example.alp_clement_romeo_evan.viewModels

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
import com.example.alp_clement_romeo_evan.models.GetAllReviewResponse
import com.example.alp_clement_romeo_evan.repositories.EventRepository
import com.example.alp_clement_romeo_evan.repositories.ReviewRepository
import com.example.alp_clement_romeo_evan.uiStates.EventDataStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.ReviewDataStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ReviewDetailViewModel(
    private val reviewRepository: ReviewRepository
): ViewModel() {
    var dataStatus: ReviewDataStatusUIState by mutableStateOf(ReviewDataStatusUIState.Start)
        private set

    var deleteStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    fun getReviewsByEventId(
        token: String,
        eventId: Int
    ) {
        viewModelScope.launch {
            dataStatus = ReviewDataStatusUIState.Loading

            try {
                val call = reviewRepository.getReviewById(token, eventId)

                call.enqueue(object : Callback<GetAllReviewResponse> {
                    override fun onResponse(
                        call: Call<GetAllReviewResponse>,
                        res: Response<GetAllReviewResponse>
                    ) {
                        if (res.isSuccessful) {
                            // Update the UI state with the reviews data
                            dataStatus = ReviewDataStatusUIState.GetAllSuccess(res.body()!!.data)
                        } else {
                            // Handle error response
                            val errorBody = res.errorBody()?.charStream()
                            val errorMessage = if (errorBody != null) {
                                Gson().fromJson(errorBody, ErrorModel::class.java)
                            } else {
                                null
                            }

                            val errorText = errorMessage?.errors ?: "Unknown error occurred"
                            dataStatus = ReviewDataStatusUIState.Failed(errorText)
                        }
                    }

                    override fun onFailure(call: Call<GetAllReviewResponse>, t: Throwable) {
                        // Handle failure
                        dataStatus = ReviewDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                // Handle network error
                dataStatus = ReviewDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WonderOfU)
                val reviewRepository = application.container.reviewRepository
                ReviewDetailViewModel(reviewRepository)
            }
        }
    }
}