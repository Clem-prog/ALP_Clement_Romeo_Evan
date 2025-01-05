package com.example.alp_clement_romeo_evan.services

import com.example.alp_clement_romeo_evan.models.CategoryRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllCategoryResponse
import com.example.alp_clement_romeo_evan.models.GetCategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryAPIService {
    @GET("/api/category")
    fun getAllCategory (@Header("X-API-TOKEN") token: String): Call<GetAllCategoryResponse>

    @GET("/api/category/{id}")
    fun getCategoryById (@Header("X-API-TOKEN") token: String, @Path("id") categoryId: Int): Call<GetCategoryResponse>

    @POST("/api/category")
    fun createCategory(@Header("X-API-TOKEN") token: String, @Body categoryId: CategoryRequest): Call<GeneralResponseModel>

    @PUT("/api/category/{id}")
    fun updateCategory(@Header("X-API-TOKEN") token: String, @Path("id") categoryId: Int, @Body eventModel: CategoryRequest): Call<GeneralResponseModel>

    @DELETE("api/category/{id}")
    fun deleteCategory(@Header("X-API-TOKEN") token: String, @Path("id") categoryId: Int): Call<GeneralResponseModel>
}