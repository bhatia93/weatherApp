package com.example.weather.network
import WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callCityWeather(@Query("q") city: String): Call<WeatherInfoResponse>
}