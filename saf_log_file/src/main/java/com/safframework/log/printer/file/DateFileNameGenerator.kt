package com.safframework.log.printer.file

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.DateFileNameGenerator
 * @author: Tony Shen
 * @date: 2019-08-31 23:06
 * @version: V1.0 <描述当前版本功能>
 */
class DateFileNameGenerator : FileNameGenerator {

    var mLocalDateFormat: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {

        override fun initialValue() = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    }

    override fun isFileNameChangeable() = true

    /**
     * Generate a file name which represent a specific date.
     */
    override fun generateFileName(logLevel: Int, timestamp: Long): String {

        val sdf = mLocalDateFormat.get()

        return sdf?.let {

            it.timeZone = TimeZone.getDefault()
            it.format(Date(timestamp))
        }?:""
    }
}