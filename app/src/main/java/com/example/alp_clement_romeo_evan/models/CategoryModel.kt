package com.example.alp_clement_romeo_evan.models

data class GetAllCategoryResponse (
    val data : List<EventModel>
)

data class GetCategoryResponse (
    val data : CategoryModel
)

data class CategoryModel (
    val id: Int = 0 ,
    val name: String="",
)

data class CategoryRequest (
    val name: String="",
)