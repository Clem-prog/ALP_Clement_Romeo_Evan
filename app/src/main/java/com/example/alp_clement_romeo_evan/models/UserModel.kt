package com.example.alp_clement_romeo_evan.models

data class UserResponse(
    val data: UserModel
)

data class LogInResponse(
    val data: UserData
)

data class UserModel (
    val id: Int,
    val username: String,
    val token: String?
)

data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val isAdmin: Boolean,
)