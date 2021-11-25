package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/25
 * 地点保存到 SharedPreferences 文件中
 **/
object PlaceDao {

    //  保存 Place 以 Place 对象存储
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    //  读取 Place 转换成 Place 对象
    fun getSavePlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    //  以保存地点数据
    fun isPlaceSaved() = sharedPreferences().contains("place")

    //  获取全局 sharedPreferences 对象
    private fun sharedPreferences() =
        //  MODE_PRIVATE 表示只有当前的应用程序才可以对这个SharedPreferences文件进行读写
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}