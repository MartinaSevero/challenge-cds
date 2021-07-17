package com.example.climachallenge.retrofit

import android.util.Log
import com.example.climachallenge.common.Constants
import okhttp3.Interceptor
import okhttp3.Response

class OpenWeatherInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlWithParams = chain.request()
                .url
                .newBuilder()
                .addQueryParameter(Constants.URL_PARAM_OPENWEATHER_API_KEY, Constants.OPENWEATHER_API_KEY)
                .build()

        var request = chain.request()

        request = request.newBuilder()
                .url(urlWithParams)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .build()
        Log.i("DENTRO DE INTERCEPTOR", "" + request) //FIXME
        return chain.proceed(request)
    }

}