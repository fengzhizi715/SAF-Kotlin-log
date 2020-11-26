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
val sdf by lazy {
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
}

fun now() = sdf.format(Date())