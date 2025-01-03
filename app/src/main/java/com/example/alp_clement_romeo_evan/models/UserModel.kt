package com.example.alp_clement_romeo_evan.models

data class UserResponse(
    val data: UserModel
)

data class UserModel (
    val username: String,
    val token: String?
)