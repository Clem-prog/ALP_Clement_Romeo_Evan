package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.EventModel
import com.example.alp_clement_romeo_evan.models.ReviewModel

sealed interface ReviewDataStatusUIState {
    data class Success(val data: ReviewModel): ReviewDataStatusUIState
    data class GetAllSuccess(val data: List<ReviewModel>): ReviewDataStatusUIState
    object Loading: ReviewDataStatusUIState
    object Start: ReviewDataStatusUIState
    data class Failed(val errorMessage: String): ReviewDataStatusUIState
}