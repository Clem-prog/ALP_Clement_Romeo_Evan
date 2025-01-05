package com.example.alp_clement_romeo_evan.models

data class GetAllReviewResponse(
    val data: List<ReviewModel>
)

data class GetReviewResponse(
    val data: ReviewModel
)

data class ReviewModel(
    val id: Int,
    val userId: Int,
    val eventId: Int,
    val rating: Int,
    val comment: String
)

data class ReviewRequest(
    val userId: Int,
    val eventId: Int,
    val rating: Int,
    val comment: String
)