package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/24
 * 获取实时天气信息
 * 数据类：用来解析json数据
 **/
data class RealtimeResponse(val status: String, val result: Result) {

    //  定义在内部防止冲突
    data class Result(val realtime: Realtime)

    data class Realtime(
        val skycon: String,
        val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}
