package com.example.alp_clement_romeo_evan

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.alp_clement_romeo_evan.repositories.AnnouncementRepository
import com.example.alp_clement_romeo_evan.repositories.AuthenticationRepository
import com.example.alp_clement_romeo_evan.repositories.CategoryRepository
import com.example.alp_clement_romeo_evan.repositories.EventRepository
import com.example.alp_clement_romeo_evan.repositories.NetworkAnnouncementRepository
import com.example.alp_clement_romeo_evan.repositories.NetworkAuthenticationRepository
import com.example.alp_clement_romeo_evan.repositories.NetworkCategoryRepository
import com.example.alp_clement_romeo_evan.repositories.NetworkEventRepository
import com.example.alp_clement_romeo_evan.repositories.NetworkUserRepository
import com.example.alp_clement_romeo_evan.repositories.UserRepository
import com.example.alp_clement_romeo_evan.services.AnnouncementAPIService
import com.example.alp_clement_romeo_evan.services.AuthenticationAPIService
import com.example.alp_clement_romeo_evan.services.CategoryAPIService
import com.example.alp_clement_romeo_evan.services.EventAPIService
import com.example.alp_clement_romeo_evan.services.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// A container is an object that contains the dependencies that the app requires.
// These dependencies are used across the whole application, so they need to be in a common place that all activities can use.
// You can create a subclass of the Application class and store a reference to the container.
interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val eventRepository: EventRepository
    val categoryRepository: CategoryRepository
    val announcementRepository: AnnouncementRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>

): AppContainer {
    // change it to your own local ip please
    private val baseUrl = "http://10.0.2.2:3000/"

    // RETROFIT SERVICE
    // delay object creation until needed using lazy
    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userRetrofitService: UserAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserAPIService::class.java)
    }

    private val eventRetrofitService: EventAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(EventAPIService::class.java)
    }

    private val categoryRetrofitService: CategoryAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(CategoryAPIService::class.java)
    }

    private val announcementRetrofitService: AnnouncementAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AnnouncementAPIService::class.java)
    }

    // REPOSITORY INIT
    // Passing in the required objects is called dependency injection (DI). It is also known as inversion of control.
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(eventRetrofitService)
    }

    override val categoryRepository: CategoryRepository by lazy {
        NetworkCategoryRepository(categoryRetrofitService)
    }

    override val announcementRepository: AnnouncementRepository by lazy {
        NetworkAnnouncementRepository(announcementRetrofitService)
    }

    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}
