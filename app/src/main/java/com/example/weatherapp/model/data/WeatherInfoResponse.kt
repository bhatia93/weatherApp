
import com.example.weather.model.data.*
import com.google.gson.annotations.SerializedName
import com.hellohasan.weatherappmvvm.features.weather_info_show.model.data_class.Wind

data class WeatherInfoResponse(
    @SerializedName("coord")
        val coord: Coord = Coord(),
    @SerializedName("weather")
        val weather: List<Weather> = listOf(),
    @SerializedName("base")
        val base: String = "",
    @SerializedName("main")
        val main: Main = Main(),
    @SerializedName("visibility")
        val visibility: Int = 0,
    @SerializedName("wind")
        val wind: Wind = Wind(),
    @SerializedName("clouds")
        val clouds: Clouds = Clouds(),
    @SerializedName("dt")
        val dt: Int = 0,
    @SerializedName("sys")
        val sys: Sys = Sys(),
    @SerializedName("id")
        val id: Int = 0,
    @SerializedName("name")
        val name: String = "",
    @SerializedName("cod")
        val cod: Int = 0
)