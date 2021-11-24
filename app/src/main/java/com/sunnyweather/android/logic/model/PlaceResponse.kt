package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/24
 * 数据类
 **/
data class PlaceResponse(val status: String, val places: List<Place>)

//  使用 @SerializedName注解的方式，来让JSON字段和Kotlin字段之间建立映射关系
data class Place(
    val name: String,
    val localhost: Localhost,
    @SerializedName("formatted_address") val address: String
)

//  经纬度
data class Localhost(val lng: String, val lat: String)