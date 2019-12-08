package com.safframework.log.debugview.extension

import android.content.Context

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.extension.`Context+Extension`
 * @author: Tony Shen
 * @date: 2019-11-30 15:34
 * @version: V2.2 <描述当前版本功能>
 */

/**
 * returns dip(dp) dimension value in pixels
 * @param value dp
 */
fun Context.dp2px(value: Int): Int = (value * resources.displayMetrics.density).toInt()