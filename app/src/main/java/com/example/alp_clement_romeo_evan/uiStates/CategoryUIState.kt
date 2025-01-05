package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.CategoryModel

sealed interface CategoryUIState {
    data class Success(val data: List<CategoryModel>): CategoryUIState
    object Loading: CategoryUIState
    object Start: CategoryUIState
    data class Failed(val errorMessage: String): CategoryUIState
}