package com.safframework.log.printer.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @FileName:
 *          com.safframework.log.printer.utils.Utils
 * @author: Tony Shen
 * @date: 2019-12-13 16:28
 * @version: V1.0 <描述当前版本功能>
 */

fun now(): String = if (android.os.Build.VERSION.SDK_INT >= 24) {
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date())
} else {
    var tms = Calendar.getInstance()
    tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH).toString() + "-" + tms.get(Calendar.DAY_OF_MONTH).toString() + " " + tms.get(Calendar.HOUR_OF_DAY).toString() + ":" + tms.get(Calendar.MINUTE).toString() + ":" + tms.get(Calendar.SECOND).toString() + "." + tms.get(Calendar.MILLISECOND).toString()
}