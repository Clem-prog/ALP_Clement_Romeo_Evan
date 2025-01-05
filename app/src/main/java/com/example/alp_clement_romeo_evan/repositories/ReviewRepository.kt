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
    fun createReview(token: String, userId: Int, eventId: Int, rating: Int, comment: String): Call<GeneralResponseModel>
    fun updateReview(token: String, reviewId: Int, userId: Int, eventId: Int, rating: Int, comment: String): Call<GeneralResponseModel>
    fun deleteReview(token: String, reviewId: Int): Call<GeneralResponseModel>
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
        userId: Int,
        eventId: Int,
        rating: Int,
        comment: String
    ): Call<GeneralResponseModel> {
        return reviewAPIService.createReview(
            token,
            ReviewRequest(userId, eventId, rating, comment)
        )
    }

    override fun updateReview(
        token: String,
        reviewId: Int,
        userId: Int,
        eventId: Int,
        rating: Int,
        comment: String
    ): Call<GeneralResponseModel> {
        return reviewAPIService.updateReview(
            token,
            reviewId,
            ReviewRequest(userId, eventId, rating, comment)
        )
    }

    override fun deleteReview(token: String, reviewId: Int): Call<GeneralResponseModel> {
        return reviewAPIService.deleteReview(token, reviewId)
    }
}

