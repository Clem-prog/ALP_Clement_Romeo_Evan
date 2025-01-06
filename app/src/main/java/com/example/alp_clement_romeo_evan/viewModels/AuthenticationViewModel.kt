package com.example.alp_clement_romeo_evan.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.alp_clement_romeo_evan.WonderOfU
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.models.AllUserResponse
import com.example.alp_clement_romeo_evan.models.ErrorModel
import com.example.alp_clement_romeo_evan.models.LogInResponse
import com.example.alp_clement_romeo_evan.models.UpdateResponse
import com.example.alp_clement_romeo_evan.models.UserResponse
import com.example.alp_clement_romeo_evan.repositories.AuthenticationRepository
import com.example.alp_clement_romeo_evan.repositories.UserRepository
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationStatusUIState
import com.example.alp_clement_romeo_evan.uiStates.AuthenticationUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _authenticationUIState = MutableStateFlow(AuthenticationUIState())

    val authenticationUIState: StateFlow<AuthenticationUIState>
        get() {
            return _authenticationUIState.asStateFlow()
        }

    var dataStatus: AuthenticationStatusUIState by mutableStateOf(AuthenticationStatusUIState.Start)
        private set

    var usernameInput by mutableStateOf("")
        private set

    var passwordInput by mutableStateOf("")
        private set

    var emailInput by mutableStateOf("")
        private set

    var isAdminInput by mutableStateOf(false)
        private set

    fun changeEmailInput(emailInput: String) {
        this.emailInput = emailInput
    }

    fun changeUsernameInput(usernameInput: String) {
        this.usernameInput = usernameInput
    }

    fun changePasswordInput(passwordInput: String) {
        this.passwordInput = passwordInput
    }

    fun changeIsAdminInput(isAdminInput: Boolean) {
        this.isAdminInput = isAdminInput
    }

    /*fun changePasswordVisibility() {
        _authenticationUIState.update { currentState ->
            if (currentState.showPassword) {
                currentState.copy(
                    showPassword = false,
                    passwordVisibility = PasswordVisualTransformation(),
                    passwordVisibilityIcon = R.drawable.ic_password_visible
                )
            } else {
                currentState.copy(
                    showPassword = true,
                    passwordVisibility = VisualTransformation.None,
                    passwordVisibilityIcon = R.drawable.ic_password_invisible
                )
            }
        }
    }*/

    fun checkLoginForm() {
        if (emailInput.isNotEmpty() && passwordInput.isNotEmpty()) {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = true
                )
            }
        } else {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    fun checkRegisterForm() {
        if (emailInput.isNotEmpty() && passwordInput.isNotEmpty() && usernameInput.isNotEmpty()) {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = true
                )
            }
        } else {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    fun checkUpdateForm() {
        if (emailInput.isNotEmpty() && usernameInput.isNotEmpty()) {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = true
                )
            }
        } else {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    fun checkButtonEnabled(isEnabled: Boolean): Color {
        if (isEnabled) {
            return Color.Blue
        }

        return Color.LightGray
    }

    fun registerUser(navController: NavHostController) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading

            try {
                val call = authenticationRepository.register(usernameInput, emailInput, passwordInput, isAdminInput)
//                dataStatus = UserDataStatusUIState.Success(registerResult)

                call.enqueue(object: Callback<UserResponse>{
                    override fun onResponse(call: Call<UserResponse>, res: Response<UserResponse>) {
                        if (res.isSuccessful) {
                            Log.d("response-data", "RESPONSE DATA: ${res.body()}")

                            saveUsernameToken(res.body()!!.data.token!!, res.body()!!.data.isAdmin, res.body()!!.data.id)

                            dataStatus = AuthenticationStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Login.name) {
                                popUpTo(PagesEnum.Register.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            // get error message
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun loginUser(
        navController: NavHostController
    ) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading
            try {
                val call = authenticationRepository.login(emailInput, passwordInput)
                call.enqueue(object: Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, res: Response<UserResponse>) {
                        if (res.isSuccessful) {
                            saveUsernameToken(res.body()!!.data.token!!, res.body()!!.data.isAdmin, res.body()!!.data.id)

                            dataStatus = AuthenticationStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.Login.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "LOGIN ERROR: ${error.toString()}")
            }
        }
    }

    fun saveUsernameToken(token: String, isAdmin: Boolean, userId: Int) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveIsAdmin(isAdmin)
            userRepository.saveUserId(userId)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WonderOfU)
                val authenticationRepository = application.container.authenticationRepository
                val userRepository = application.container.userRepository
                AuthenticationViewModel(authenticationRepository, userRepository)
            }
        }
    }

    fun updateUser(token: String, userId: Int, navController: NavHostController) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading

            try {
                val call = userRepository.update(token, usernameInput, emailInput, userId)
//                dataStatus = UserDataStatusUIState.Success(registerResult)

                call.enqueue(object: Callback<UpdateResponse>{
                    override fun onResponse(call: Call<UpdateResponse>, res: Response<UpdateResponse>) {
                        if (res.isSuccessful) {

                            dataStatus = AuthenticationStatusUIState.Updated(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Profile.name) {
                                popUpTo(PagesEnum.ProfileEdit.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            // get error message
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun getUserInfo(token: String, userId: Int) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading

            try {
                val call = userRepository.getUser(token, userId)

                call.enqueue(object : Callback<LogInResponse> {
                    override fun onResponse(call: Call<LogInResponse>, res: Response<LogInResponse>) {
                        if (res.isSuccessful) {
                            dataStatus = AuthenticationStatusUIState.GotUser(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<LogInResponse>, t: Throwable) {
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                        Log.d("get-user-error", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("get-user-error", "GET USER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun getEventUser(token: String, userId: Int) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading

            try {
                val call = userRepository.getEventUser(token, userId)

                call.enqueue(object : Callback<LogInResponse> {
                    override fun onResponse(call: Call<LogInResponse>, res: Response<LogInResponse>) {
                        if (res.isSuccessful) {
                            dataStatus = AuthenticationStatusUIState.GotUser(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<LogInResponse>, t: Throwable) {
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                        Log.d("get-user-error", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("get-user-error", "GET USER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun getAllUser() {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading

            try {
                val call = userRepository.getAllUser()

                call.enqueue(object : Callback<AllUserResponse> {
                    override fun onResponse(
                        call: Call<AllUserResponse>,
                        res: Response<AllUserResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = AuthenticationStatusUIState.GotAllUser(res.body()!!.data)
                        } else {
                            val errorBody = res.errorBody()?.charStream()
                            val errorMessage = if (errorBody != null) {
                                Gson().fromJson(errorBody, ErrorModel::class.java)
                            } else {
                                null
                            }

                            val errorText = errorMessage?.errors ?: "Unknown error occurred"
                            dataStatus = AuthenticationStatusUIState.Failed(errorText)
                        }
                    }

                    override fun onFailure(call: Call<AllUserResponse>, t: Throwable) {
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
            }
        }
    }


    fun resetViewModel() {
        changeEmailInput("")
        changePasswordInput("")
        changeUsernameInput("")
        _authenticationUIState.update { currentState ->
            currentState.copy(
                showPassword = false,
                passwordVisibility = PasswordVisualTransformation(),
                //passwordVisibilityIcon = R.drawable.ic_password_visible,
                //confirmPasswordVisibilityIcon = R.drawable.ic_password_visible,
                buttonEnabled = false
            )
        }
        dataStatus = AuthenticationStatusUIState.Start
    }

    fun clearErrorMessage() {
        dataStatus = AuthenticationStatusUIState.Start
    }
}