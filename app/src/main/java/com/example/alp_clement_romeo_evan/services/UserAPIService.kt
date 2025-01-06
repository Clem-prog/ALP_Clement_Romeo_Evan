package com.example.alp_clement_romeo_evan.services

import android.util.Log
import com.example.alp_clement_romeo_evan.models.AllUserResponse
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.LogInResponse
import com.example.alp_clement_romeo_evan.models.UpdateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPIService {
    @DELETE("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

    @GET("api/user/{id}")
    fun getUser(@Header("X-API-TOKEN") token: String, @Path("id") userId: Int): Call<LogInResponse>

    @PUT("api/user/{id}")
    fun updateUser(@Header("X-API-TOKEN") token: String, @Path("id") userId: Int, @Body updateMap: HashMap<String, String>): Call<UpdateResponse>

    @GET("/api/user/events/{id}")
    fun getEventUser(@Header("X-API-TOKEN") token: String, @Path("id") userId: Int): Call<LogInResponse>

    @GET("/api/users")
    fun getAllUser(): Call<AllUserResponse>
}