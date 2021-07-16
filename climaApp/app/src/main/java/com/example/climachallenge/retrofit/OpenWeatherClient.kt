package com.example.climachallenge.retrofit

import com.example.climachallenge.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherClient {
    private val openWeatherService: OpenWeatherService
    private val retrofit: Retrofit

    companion object {
        var instance: OpenWeatherClient? = null
        get() {
            if (field == null) {
                instance = OpenWeatherClient()
            }
            return instance
        }
    }
    //Builder
    init {
        //Include defined interceptor
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(OpenWeatherInterceptor())

        val client = okHttpClientBuilder.build()

        //Build retrofit client
        retrofit = Retrofit.Builder()
                .baseUrl(Constants.OPENWEATHER_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        //Instantiate retrofit service from retrofit object
        openWeatherService = retrofit.create(OpenWeatherService::class.java)

        fun getOpenWeatherService() = openWeatherService
    }
}