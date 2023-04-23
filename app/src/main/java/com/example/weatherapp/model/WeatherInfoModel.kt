package com.example.weather.model

import WeatherInfoResponse
import com.example.weather.common.RequestListener

interface WeatherInfoModel {
    fun getSearchCity(cityId: String, callback: RequestListener<WeatherInfoResponse>)
}