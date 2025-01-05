package com.example.alp_clement_romeo_evan.services

import com.example.alp_clement_romeo_evan.models.ReviewRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllReviewResponse
import com.example.alp_clement_romeo_evan.models.GetReviewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewAPIService {
    @GET("/api/review/all") fun getAllReviews(@Header("X-API-TOKEN") token: String): Call<GetAllReviewResponse>

    @GET("/api/review/{id}") fun getReviewById(@Header("X-API-TOKEN") token: String, @Path("id") reviewId: Int): Call<GetReviewResponse>

    @POST("/api/review") fun createReview(@Header("X-API-TOKEN") token: String, @Body review: ReviewRequest): Call<GeneralResponseModel>

    @PUT("/api/review/{id}") fun updateReview(@Header("X-API-TOKEN") token: String, @Path("id") reviewId: Int, @Body review: ReviewRequest): Call<GeneralResponseModel>

    @DELETE("/api/review/{id}") fun deleteReview(@Header("X-API-TOKEN") token: String, @Path("id") reviewId: Int): Call<GeneralResponseModel>
}