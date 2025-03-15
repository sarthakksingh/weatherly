package com.example.weatherly



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.api.Constant
import com.example.weatherly.api.NetworkResponse
import com.example.weatherly.api.RetrofitInstance
import com.example.weatherly.api.weatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData <NetworkResponse<weatherModel>>()
    val weatherResult : LiveData<NetworkResponse<weatherModel>> = _weatherResult



    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {



            try {
                val response = weatherApi.getWeather(Constant.apikey, city)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else {
                    _weatherResult.value = NetworkResponse.Error("Failed To Load ")
                }


            }
            catch (e:Exception){
                _weatherResult.value = NetworkResponse.Error("Failed To Load ")
            }


        }
    }
}


