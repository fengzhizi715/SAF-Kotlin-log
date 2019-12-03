package com.safframework.log.debugview.utils

import android.os.Build

/**
 *
 * @FileName:
 *          com.safframework.log.debugview.utils.Platform
 * @author: Tony Shen
 * @date: 2019-12-03 15:11
 * @version: V1.0 <描述当前版本功能>
 */
fun support(apiVersion:Int, block : () -> Unit) {

    if (versionOrHigher(apiVersion)) {

        block()
    }
}

fun <T> support(apiVersion: Int, function: () -> T, default: () -> T): T = if (versionOrHigher(apiVersion)) function() else default()

private fun versionOrHigher(version: Int) = Build.VERSION.SDK_INT >= version