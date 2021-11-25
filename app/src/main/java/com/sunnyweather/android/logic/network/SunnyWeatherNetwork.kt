package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/24
 * 对所有网络请求的API进行封装
 **/
object SunnyWeatherNetwork {

    //  创建 PlaceService 代理对象
    private val placeService = ServiceCreator.create<PlaceService>()

    //  创建 WeatherService 代理对象
    private val weatherService = ServiceCreator.create<WeatherService>()

    //  通过经纬度查询每日天气
    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng, lat).await()

    //  通过经纬度查询实时天气
    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()

    //  通过地名查询
    //  使用协程来简化网络请求
    //  调用searchPlaces时当前的协程也会被阻塞住
    //  直到服务器响应我们的请求之后，await()函数会将解析出来的数据模型对象取出并返回，
    //  同时恢复当前协程的执行，searchPlaces()函数在得到await()函数的返回值后会将该数据再返回到上一层
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                //  响应成功
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        //  恢复
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                //  响应失败
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}