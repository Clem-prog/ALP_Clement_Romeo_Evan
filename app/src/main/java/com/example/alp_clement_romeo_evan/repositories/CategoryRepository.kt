package com.example.alp_clement_romeo_evan.repositories

import com.example.alp_clement_romeo_evan.models.CategoryRequest
import com.example.alp_clement_romeo_evan.models.EventRequest
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllCategoryResponse
import com.example.alp_clement_romeo_evan.models.GetAllEventResponse
import com.example.alp_clement_romeo_evan.models.GetCategoryResponse
import com.example.alp_clement_romeo_evan.models.GetEventResponse
import com.example.alp_clement_romeo_evan.services.CategoryAPIService
import retrofit2.Call

interface CategoryRepository {
    fun getAllCategories(token: String): Call<GetAllCategoryResponse>
    fun getCategoryById(token: String, categoryId: Int): Call<GetCategoryResponse>
    fun createCategory(token: String, name: String): Call<GeneralResponseModel>
    fun updateCategory(token: String, categoryId: Int, name: String): Call<GeneralResponseModel>
    fun deleteCategory(token: String, categoryId: Int): Call<GeneralResponseModel>
}


class NetworkCategoryRepository (
    private val categoryAPIService: CategoryAPIService
): CategoryRepository {
    override fun getAllCategories(token: String): Call<GetAllCategoryResponse> {
        return categoryAPIService.getAllCategory(token)
    }

    override fun createCategory(
        token: String,
        name: String
    ): Call<GeneralResponseModel> {
        return categoryAPIService.createCategory(
            token,
            CategoryRequest(name)
        )
    }

    override fun getCategoryById(token: String, categoryId: Int): Call<GetCategoryResponse> {
        return categoryAPIService.getCategoryById(token, categoryId)
    }

    override fun updateCategory(
        token: String,
        categoryId: Int,
        name: String
    ): Call<GeneralResponseModel> {
        return categoryAPIService.updateCategory(
            token,
            categoryId,
            EventRequest(name)
        )
    }

    override fun deleteCategory(token: String, categoryId: Int): Call<GeneralResponseModel> {
        return categoryAPIService.deleteCategory(token, categoryId)
    }
}
