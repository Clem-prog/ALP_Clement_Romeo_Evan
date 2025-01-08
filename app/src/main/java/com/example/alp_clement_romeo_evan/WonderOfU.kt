package com.example.alp_clement_romeo_evan

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_data"
)

class WonderOfU: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        val config = hashMapOf(
            "cloud_name" to "your-cloud-name",
            "api_key" to "your-api-key",
            "api_secret" to "your-api-secret"
        )

        container = DefaultAppContainer(dataStore)
    }
}