package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.ReviewModel

sealed interface ReviewDetailStatusUIState {
    data class Success(val data: ReviewModel): ReviewDetailStatusUIState
    object Loading: ReviewDetailStatusUIState
    object Start: ReviewDetailStatusUIState
    data class Failed(val errorMessage: String): ReviewDetailStatusUIState
}