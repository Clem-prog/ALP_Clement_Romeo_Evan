package com.example.alp_clement_romeo_evan.models

data class GetAllReviewResponse(
    val data: List<ReviewModel>
)

data class GetReviewResponse(
    val data: ReviewModel
)

data class ReviewModel(
    val id: Int = 0,
    val userId: Int = 0,
    val eventId: Int = 0,
    val rating: Int = 0,
    val comment: String = ""
)

data class ReviewRequest(
    val userId: Int = 0,
    val eventId: Int = 0,
    val rating: Int = 0,
    val comment: String = ""
)