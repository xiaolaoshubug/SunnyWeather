package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/24
 * 仓库层的统一封装入口
 **/
object Repository {
    //  liveData 使用的是 lifecycle-livedata-ktx 库
    //  自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文，
    //  这样我们就可以在liveData()函数的代码块中调用任意的挂起函数了
    //  挂起函数指定为 Dispatchers.IO 这样代码块中的代码执行在子线程中
    //  Android是不允许在主线程中进行网络请求的，诸如读写数据库之类的本地数据操作也是不建议在主线程中进行的
    //  emit()方法类似于调用LiveData的setValue()方法来通知数据变化
    //  只不过这里我们无法直接取得返回的LiveData对象，所以lifecycle-livedata-ktx库提供了这样一个替代方法
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response is ${placeResponse.status}"))
        }
    }

    //  刷新天气
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            //  异步执行加载天气，使用协程 async 函数
            //  必须在协程作用域内才能调用
            //  最后调用await()获取结果
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            //  接口数据
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                Result.success(
                    Weather(
                        realtimeResponse.result.realtime,
                        dailyResponse.result.daily
                    )
                )
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }

        }
    }

    //  网络请求接口都可能会抛出异常
    //  统一的入口函数，简化重复的 try catch代码
    //  liveData()函数的代码块中，我们是拥有挂起函数上下文的，
    //  可是当回调到Lambda表达式中，代码就没有挂起函数上下文了，
    //  但实际上Lambda表达式中的代码一定也是在挂起函数中运行的。
    //  为了解决这个问题，我们需要在函数类型前声明一个suspend关键字，
    //  以表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavePlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}