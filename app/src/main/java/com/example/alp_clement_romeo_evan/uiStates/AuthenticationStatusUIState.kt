package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.UserData
import com.example.alp_clement_romeo_evan.models.UserModel
import com.example.alp_clement_romeo_evan.models.UserUpdate

// exhaustive (covers all scenario possibilities) interface
sealed interface AuthenticationStatusUIState {
    data class Success(val userModelData: UserModel): AuthenticationStatusUIState
    data class GotUser(val userModelData: UserData): AuthenticationStatusUIState
    data class GotAllUser(val userModelData: List<UserData>): AuthenticationStatusUIState
    data class Updated(val userModelData: UserUpdate): AuthenticationStatusUIState
    object Loading: AuthenticationStatusUIState
    object Start: AuthenticationStatusUIState
    data class Failed(val errorMessage: String): AuthenticationStatusUIState
}