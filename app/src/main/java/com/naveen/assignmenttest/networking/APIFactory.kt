package com.naveen.assignmenttest.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIFactory {

    var loggingInterceptor = HttpLoggingInterceptor()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://hn.algolia.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()

    private fun getClient(): OkHttpClient {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        var client = OkHttpClient.Builder()
        client.addInterceptor(loggingInterceptor)
        return client.build()
    }

    fun getApi() = retrofit.create(APIs::class.java)

}