package com.sunnyweather.android.ui

import android.content.Context
import android.content.res.Configuration

/**
 * Author AY
 * Version 1.0
 * Date 2021/11/25
 * 判断当前是暗黑主题还是浅色主题
 * Kotlin中的or关键字对应了Java中的|运算符
 * xor关键字对应了Java中的^运算符
 * and关键字其实就对应了Java中的&运算符
 * 这是分支测试代码
 **/
fun isDarkTheme(context: Context): Boolean {
    val flag = context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK
    return flag == Configuration.UI_MODE_NIGHT_YES
}