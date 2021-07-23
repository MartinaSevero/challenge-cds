package com.example.climachallenge.retrofit

import android.os.Build
import com.example.climachallenge.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class OpenWeatherClient {
    private val openWeatherService: OpenWeatherService
    private val retrofit: Retrofit

    companion object {
        var instance: OpenWeatherClient? = null
            get() {
                if (field == null) {
                    instance = OpenWeatherClient()
                }
                return field
            }
    }

    //Builder
    init {
        val okHttpClientBuilder: OkHttpClient.Builder =
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                unSafeOkHttpClient()
            } else {
                OkHttpClient.Builder()
            }
        //Include defined interceptor
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
    }

    fun getOpenWeatherService() = openWeatherService

    fun unSafeOkHttpClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier { _, _ -> true }
            }

            return okHttpClient
        } catch (e: Exception) {
            return okHttpClient
        }
    }
}