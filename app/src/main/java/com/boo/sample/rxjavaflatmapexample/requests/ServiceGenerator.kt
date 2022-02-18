package com.boo.sample.rxjavaflatmapexample.requests

import android.app.DownloadManager
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    val BASE_URL = "https://jsonplaceholder.typicode.com"

    private val retrofitBuilder = Retrofit.Builder().baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    val retrofit:Retrofit = retrofitBuilder.build()
    val requestApi: RequestApi = retrofit.create(RequestApi::class.java)

}