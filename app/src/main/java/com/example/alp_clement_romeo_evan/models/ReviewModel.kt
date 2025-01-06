package com.example.alp_clement_romeo_evan.models

data class GetAllReviewResponse(
    val data: List<ReviewModel>
)

data class GetReviewResponse(
    val data: ReviewModel
)

data class ReviewModel(
    val id: Int = 0,
    val user_id: Int = 0,
    val event_id: Int = 0,
    val rating: Int = 0,
    val comment: String = ""
)

data class ReviewRequest(

    val user_id: Int = 0,
    val event_id: Int = 0,
    val rating: Int = 0,
    val comment: String = ""

)