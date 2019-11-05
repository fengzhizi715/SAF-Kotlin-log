package com.safframework.log.printer.file.name

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.name.DateFileNameGenerator
 * @author: Tony Shen
 * @date: 2019-08-31 23:06
 * @since: V2.0 基于 yyyy-MM-dd 的日期格式生成文件
 */
class DateFileNameGenerator : FileNameGenerator {

    val mLocalDateFormat: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {

        override fun initialValue() = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    }

    override fun isFileNameChangeable() = true

    override fun generateFileName(logLevel: Int, tag:String, timestamp: Long): String {

        var sdf = mLocalDateFormat.get()

        if (sdf==null) {
            sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        }

        return sdf.let {

            it.timeZone = TimeZone.getDefault()
            it.format(Date(timestamp))
        }
    }
}