package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/24
 * 获取未来几天天气信息
 * 数据类：用来解析json数据
 **/
data class DailyResponse(val status: String, val result: Result) {

    //  定义在内部防止冲突
    data class Result(val daily: Daily)

    data class Daily(
        val temperature: List<Temperature>,
        val skycon: List<SkyCon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    data class Temperature(val max: Float, val min: Float)

    data class SkyCon(val value: String, val date: Date)

    data class LifeIndex(
        val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>
    )

    data class LifeDescription(val desc: String)
}