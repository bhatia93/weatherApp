
import com.example.weather.network.QueryParameterInterceptor
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    val client: Retrofit
        get() {
            if (retrofit == null) {
                synchronized(Retrofit::class.java) {
                    if (retrofit == null) {

                        val httpClient = OkHttpClient.Builder()
                                .addInterceptor(QueryParameterInterceptor())


                        val client = httpClient.build()

                        retrofit = Retrofit.Builder()
                                .baseUrl("https://api.openweathermap.org/data/2.5/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .client(client)
                                .build()
                    }
                }

            }
            return retrofit!!
        }
}