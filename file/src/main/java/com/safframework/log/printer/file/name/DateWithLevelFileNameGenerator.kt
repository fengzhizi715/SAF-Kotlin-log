package com.safframework.log.printer.file.name

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.name.DateWithLevelFileNameGenerator
 * @author: Tony Shen
 * @date: 2019-10-02 21:45
 * @since: V2.1 基于 yyyy-MM-dd 的日期格式和日志的 Level 生成文件
 */
class DateWithLevelFileNameGenerator : FileNameGenerator {

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
            val date = it.format(Date(timestamp))

            val level =  when(logLevel) {

                0 -> "error"

                1  -> "warn"

                2  -> "info"

                3 -> "debug"

                else -> "info"
            }

            "$date-$level"
        }
    }
}