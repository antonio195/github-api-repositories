package com.antoniocostadossantos.githubapirepositories.remote

import com.antoniocostadossantos.githubapirepositories.util.Constants.Companion.URL_BASE
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {

        private val retrofit by lazy {

            val gson = GsonBuilder().setLenient().create()

            Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        fun <T> getService(serviceClass: Class<T>): T {
            return retrofit.create(serviceClass)
        }

    }
}