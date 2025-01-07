package com.example.alp_clement_romeo_evan.models

data class UserResponse(
    val data: UserModel
)

data class AllUserResponse (
    val data: List<UserData>
)

data class LogInResponse(
    val data: UserData
)

data class UpdateResponse (
    val data: UserUpdate
)

data class UserModel (
    val id: Int,
    val isAdmin: Boolean,
    val token: String?
)

data class UserUpdate (
    val username: String,
    val email: String,
)

data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val isAdmin: Boolean,
)