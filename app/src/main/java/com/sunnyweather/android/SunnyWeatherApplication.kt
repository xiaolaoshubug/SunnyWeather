package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/24
 * 全局 TOKEN 用于彩云天气 API 调用
 * 全局 context 从 application 中获取
 * 需要在 AndroidManifest.xml -> application 标签中使用 android:name 属性中注册
 * android:name=".SunnyWeatherApplication"
 **/
class SunnyWeatherApplication : Application() {
    //  单例类
    companion object {
        @SuppressLint("StaticFieldLeak")
        //  这里拿的是 Application 的 context
        lateinit var context: Context
        //  彩云天气 API TOKEN
        const val TOKEN = "LvwbbMxqVYyAknY3"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}