package com.example.alp_clement_romeo_evan.uiStates

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.alp_clement_romeo_evan.R

data class AuthenticationUIState(
    val showPassword: Boolean = false,
    val passwordVisibility: VisualTransformation = PasswordVisualTransformation(),
    //val passwordVisibilityIcon: Int = R.drawable.ic_password_visible,
    val buttonEnabled: Boolean = false
)