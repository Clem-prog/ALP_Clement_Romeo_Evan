package com.example.alp_clement_romeo_evan.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.alp_clement_romeo_evan.WonderOfU
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.models.ErrorModel
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.GetAllCategoryResponse
import com.example.alp_clement_romeo_evan.models.GetCategoryResponse
import com.example.alp_clement_romeo_evan.repositories.CategoryRepository
import com.example.alp_clement_romeo_evan.uiStates.CategoryUIState
import com.example.alp_clement_romeo_evan.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    var dataStatus: CategoryUIState by mutableStateOf(CategoryUIState.Start)
        private set

    var deleteStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    fun getAllCategories(
        token: String
    ) {
        viewModelScope.launch {
            dataStatus = CategoryUIState.Loading

            try {
                val call = categoryRepository.getAllCategories(token)

                call.enqueue(object : Callback<GetAllCategoryResponse> {
                    override fun onResponse(
                        call: Call<GetAllCategoryResponse>,
                        res: Response<GetAllCategoryResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = CategoryUIState.Success(res.body()!!.data)
                        } else {
                            val errorBody = res.errorBody()?.charStream()
                            val errorMessage = if (errorBody != null) {
                                Gson().fromJson(errorBody, ErrorModel::class.java)
                            } else {
                                null
                            }

                            val errorText = errorMessage?.errors ?: "Unknown error occurred"
                            dataStatus = CategoryUIState.Failed(errorText)
                        }
                    }

                    override fun onFailure(call: Call<GetAllCategoryResponse>, t: Throwable) {
                        dataStatus = CategoryUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = CategoryUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun deleteTodo(token: String, categoryId: Int, navController: NavHostController) {
        viewModelScope.launch {
            deleteStatus = StringDataStatusUIState.Loading

            try {
                val call = categoryRepository.deleteCategory(token, categoryId)

                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            deleteStatus = StringDataStatusUIState.Success(res.body()!!.data)

                            Log.d("delete-status", "Delete status: ${res.body()!!.data}")

                            navController.popBackStack()
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            deleteStatus = StringDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        deleteStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                deleteStatus = StringDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WonderOfU)
                val categoryRepository = application.container.categoryRepository
                CategoryViewModel(categoryRepository)
            }
        }
    }

    fun clearErrorMessage() {
        deleteStatus = StringDataStatusUIState.Start
    }
}