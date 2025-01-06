package com.example.alp_clement_romeo_evan.models

data class GetAllReviewResponse(
    val data: List<ReviewModel>
)

data class GetReviewResponse(
    val data: ReviewModel
)

data class ReviewModel(
    val id: Int,
    val user_id: Int,
    val event_id: Int,
    val rating: Int,
    val comment: String
)

data class ReviewRequest(
    val user_id: Int,
    val event_id: Int,
    val rating: Int,
    val comment: String,
)