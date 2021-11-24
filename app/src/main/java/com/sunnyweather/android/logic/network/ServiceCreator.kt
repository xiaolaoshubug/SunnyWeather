package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/24
 * Retrofit 网络请求构造器
 **/
object ServiceCreator {

    //  彩云 API 地址
    private const val BASE_URL = "https://api.caiyunapp.com"

    // retrofit 构造器
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //  使用泛型实化
    inline fun <reified T> create(): T = create(T::class.java)

}