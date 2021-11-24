package com.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/24
 * model view
 **/
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    //  对界面上显示的城市数据进行缓存
    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    //  调用 searchPlaces 方法被调用时，switchMap()方法所对应的转换函数就会执行
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}