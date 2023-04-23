package com.example.weather.model.data

import com.google.gson.annotations.SerializedName

data class Clouds(
        @SerializedName("all")
        val all: Int = 0
)