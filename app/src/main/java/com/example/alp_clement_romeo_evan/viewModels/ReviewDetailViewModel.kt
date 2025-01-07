package com.example.alp_clement_romeo_evan.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun getAllReview(
        token: String
    ) {
        viewModelScope.launch {
            dataStatus = ReviewDataStatusUIState.Loading

            try {
                val call = reviewRepository.getAllReviews(token)

                call.enqueue(object : Callback<GetAllReviewResponse> {
                    override fun onResponse(
                        call: Call<GetAllReviewResponse>,
                        res: Response<GetAllReviewResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = ReviewDataStatusUIState.GetAllSuccess(res.body()!!.data)
                        } else {
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
                        dataStatus = ReviewDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = ReviewDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }
}