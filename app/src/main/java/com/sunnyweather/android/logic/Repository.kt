package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

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
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        //  emit()方法类似于调用LiveData的setValue()方法来通知数据变化
        //  只不过这里我们无法直接取得返回的LiveData对象，所以lifecycle-livedata-ktx库提供了这样一个替代方法
        emit(result)
    }
}