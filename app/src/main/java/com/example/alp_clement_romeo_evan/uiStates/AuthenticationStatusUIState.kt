package com.example.alp_clement_romeo_evan.uiStates

import com.example.alp_clement_romeo_evan.models.UserData
import com.example.alp_clement_romeo_evan.models.UserModel

// exhaustive (covers all scenario possibilities) interface
sealed interface AuthenticationStatusUIState {
    data class Success(val userModelData: UserModel): AuthenticationStatusUIState
    data class GotUser(val userModelData: UserData): AuthenticationStatusUIState
    object Loading: AuthenticationStatusUIState
    object Start: AuthenticationStatusUIState
    data class Failed(val errorMessage: String): AuthenticationStatusUIState
}