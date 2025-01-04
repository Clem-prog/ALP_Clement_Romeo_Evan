package com.example.alp_clement_romeo_evan.services

import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.LogInResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPIService {
    @POST("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

    @GET("api/user/{id}")
    fun getUser(@Header("X-API-TOKEN") token: String, @Path("id") userId: Int): Call<LogInResponse>
}