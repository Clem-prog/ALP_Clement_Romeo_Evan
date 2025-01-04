package com.example.alp_clement_romeo_evan.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.alp_clement_romeo_evan.models.GeneralResponseModel
import com.example.alp_clement_romeo_evan.models.LogInResponse
import com.example.alp_clement_romeo_evan.services.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call

interface UserRepository {
    val currentUserToken: Flow<String>
    val currentUsername: Flow<String>
    val currentUserId: Flow<Int>

    fun logout(token: String): Call<GeneralResponseModel>
    fun getUser(token: String, userId: Int): Call<LogInResponse>

    suspend fun saveUserToken(token: String)

    suspend fun saveUsername(username: String)

    suspend fun saveUserId(userId: Int)
}

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userAPIService: UserAPIService
): UserRepository {
    private companion object {
        val USER_TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
        val USER_ID = intPreferencesKey("userId")
    }

    override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USER_TOKEN] ?: "Unknown"
    }

    override val currentUsername: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USERNAME] ?: "Unknown"
    }

    override val currentUserId: Flow<Int> = userDataStore.data.map { preferences ->
        preferences[USER_ID] ?: 0
    }

    override suspend fun saveUserToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    override suspend fun saveUsername(username: String) {
        userDataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    override suspend fun saveUserId(userId: Int) {
        userDataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    override fun logout(token: String): Call<GeneralResponseModel> {
        return userAPIService.logout(token)
    }

    override fun getUser(token: String, userId: Int): Call<LogInResponse> {
        return userAPIService.getUser(token, userId)
    }
}