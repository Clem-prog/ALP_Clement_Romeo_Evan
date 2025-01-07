package com.example.alp_clement_romeo_evan.repositories

import com.example.alp_clement_romeo_evan.models.EventRequest
import com.example.alp_clement_romeo_evan.models.ReviewRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllReviewResponse
import com.example.alp_clement_romeo_evan.models.GetReviewResponse
import com.example.alp_clement_romeo_evan.services.ReviewAPIService
import retrofit2.Call

interface ReviewRepository {
    fun getAllReviews(token: String): Call<GetAllReviewResponse>
    fun getReviewById(token: String, reviewId: Int): Call<GetReviewResponse>
    fun createReview(token: String, userId: Int, eventId: Int, title:String, rating: Int, comment: String): Call<GetReviewResponse>
    fun updateReview(token: String, reviewId: Int, userId: Int, eventId: Int, title:String, rating: Int, comment: String): Call<GetReviewResponse>
    fun deleteReview(token: String, reviewId: Int): Call<GetReviewResponse>
}

class NetworkReviewRepository(
    private val reviewAPIService: ReviewAPIService
) : ReviewRepository {
    override fun getAllReviews(token: String): Call<GetAllReviewResponse> {
        return reviewAPIService.getAllReviews(token)
    }

    override fun getReviewById(token: String, reviewId: Int): Call<GetReviewResponse> {
        return reviewAPIService.getReviewById(token, reviewId)
    }

    override fun createReview(
        token: String,
        user_id: Int,
        event_id: Int,
        title: String,
        rating: Int,
        comment: String
    ): Call<GetReviewResponse> {
        return reviewAPIService.createReview(
            token,
            ReviewRequest(user_id, event_id, title, rating, comment)
        )
    }

    override fun updateReview(
        token: String,
        reviewId: Int,
        userId: Int,
        eventId: Int,
        title: String,
        rating: Int,
        comment: String
    ): Call<GetReviewResponse> {
        return reviewAPIService.updateReview(
            token,
            reviewId,
            ReviewRequest(userId, eventId, title, rating, comment)
        )
    }

    override fun deleteReview(token: String, reviewId: Int): Call<GetReviewResponse> {
        return reviewAPIService.deleteReview(token, reviewId)
    }
}

