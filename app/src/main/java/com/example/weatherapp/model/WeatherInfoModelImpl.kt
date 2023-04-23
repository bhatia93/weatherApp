package com.example.weather.model
import WeatherInfoResponse
import android.content.Context
import com.example.weather.common.RequestListener
import com.example.weather.network.ApiInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherInfoModelImpl(private val context: Context): WeatherInfoModel {

    override fun getSearchCity(
        cityId: String,
        callback: RequestListener<WeatherInfoResponse>
    ) {
        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call: Call<WeatherInfoResponse> = apiInterface.callCityWeather(cityId)
        call.enqueue(object : Callback<WeatherInfoResponse> {

            // if network call success, this method will be triggered
            override fun onResponse(call: Call<WeatherInfoResponse>, response: Response<WeatherInfoResponse>) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // if network call fail, this method will be triggered
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!) //let presenter know about failure
            }
        })
    }


}