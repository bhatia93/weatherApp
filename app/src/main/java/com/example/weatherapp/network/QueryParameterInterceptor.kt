package com.example.weather.network
import okhttp3.Interceptor
import okhttp3.Response

class QueryParameterInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url.newBuilder()
                .addQueryParameter("appid", "5ef4cd7294bf2d51459055f0d6025196")
                .build()

        val request = chain.request().newBuilder()
                .url(url)
                .build()

        return chain.proceed(request)
    }
}