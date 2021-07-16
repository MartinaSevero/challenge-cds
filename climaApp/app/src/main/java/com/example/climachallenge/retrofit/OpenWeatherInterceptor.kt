package com.example.climachallenge.retrofit

import com.example.climachallenge.common.Constants
import okhttp3.Interceptor
import okhttp3.Response

class OpenWeatherInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlWithParams = chain.request()
                .url
                .newBuilder()
                .addQueryParameter(Constants.URL_PARAM_LAT, Constants.VALUE_LAT)
                .addQueryParameter(Constants.URL_PARAM_LON, Constants.VALUE_LON)
                .addQueryParameter(Constants.URL_PARAM_EXCLUDE, Constants.VALUE_EXCLUDE)
                .addQueryParameter(Constants.URL_PARAM_UNITS, Constants.VALUE_UNITS)
                .addQueryParameter(Constants.URL_PARAM_OPENWEATHER_API_KEY, Constants.OPENWEATHER_API_KEY)
                .build()

        var request = chain.request()

        request = request?.newBuilder()
                .url(urlWithParams)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build()

        return chain.proceed(request)
    }

}