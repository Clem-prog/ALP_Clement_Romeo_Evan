import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.alp_clement_romeo_evan.WonderOfU
import com.example.alp_clement_romeo_evan.enums.PagesEnum
import com.example.alp_clement_romeo_evan.models.ErrorModel
import com.example.alp_clement_romeo_evan.models.GetAllAttendedEventResponse
import com.example.alp_clement_romeo_evan.models.GetAttendedEventResponse
import com.example.alp_clement_romeo_evan.repositories.AttendedEventRepository
import com.example.alp_clement_romeo_evan.uiStates.AttendedEventDetailUIState
import com.example.alp_clement_romeo_evan.viewModels.AnnouncementViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AttendedEventViewModel(
    private val attendedEventRepository: AttendedEventRepository
): ViewModel() {
    var dataStatus: AttendedEventDetailUIState by mutableStateOf(AttendedEventDetailUIState.Start)
        private set

    fun getAllAttendedEvents(token: String) {
        viewModelScope.launch {
            dataStatus = AttendedEventDetailUIState.Loading
            try {
                val call = attendedEventRepository.getAllAttendedEvents(token)
                call.enqueue(object : Callback<GetAllAttendedEventResponse> {
                    override fun onResponse(
                        call: Call<GetAllAttendedEventResponse>,
                        res: Response<GetAllAttendedEventResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = AttendedEventDetailUIState.GotAll(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AttendedEventDetailUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllAttendedEventResponse>, t: Throwable) {
                        dataStatus = AttendedEventDetailUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = AttendedEventDetailUIState.Failed(error.localizedMessage)
                Log.d("attended-event-error", "ERROR: ${error.toString()}")
            }
        }
    }

    fun createAttendedEvent(token: String, dateSigned: String, eventId: Int, navController: NavHostController) {
        viewModelScope.launch {
            dataStatus = AttendedEventDetailUIState.Loading
            try {
                Log.d("event-id thing submitted lol", "SUBMITTED ID: ${eventId}")
                val call = attendedEventRepository.createAttendedEvent(token, dateSigned, eventId)
                call.enqueue(object : Callback<GetAttendedEventResponse> {
                    override fun onResponse(
                        call: Call<GetAttendedEventResponse>,
                        res: Response<GetAttendedEventResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = AttendedEventDetailUIState.Success(res.body()!!.data)

                            navController.navigate(PagesEnum.YourEvents.name) {
                                popUpTo(PagesEnum.EventDetail.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AttendedEventDetailUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAttendedEventResponse>, t: Throwable) {
                        dataStatus = AttendedEventDetailUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = AttendedEventDetailUIState.Failed(error.localizedMessage)
                Log.d("attended-event-error", "ERROR: ${error.toString()}")
            }
        }
    }

    fun getAllEventMembers(token: String, eventId: Int) {
        viewModelScope.launch {
            dataStatus = AttendedEventDetailUIState.Loading
            try {
                val call = attendedEventRepository.getAllEventMembers(token, eventId)
                call.enqueue(object : Callback<GetAllAttendedEventResponse> {
                    override fun onResponse(
                        call: Call<GetAllAttendedEventResponse>,
                        res: Response<GetAllAttendedEventResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = AttendedEventDetailUIState.GotAll(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AttendedEventDetailUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllAttendedEventResponse>, t: Throwable) {
                        dataStatus = AttendedEventDetailUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = AttendedEventDetailUIState.Failed(error.localizedMessage)
                Log.d("attended-event-error", "ERROR: ${error.toString()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WonderOfU)
                val attendedEventRepository = application.container.attendedEventRepository
                AttendedEventViewModel(attendedEventRepository)
            }
        }
    }
}

