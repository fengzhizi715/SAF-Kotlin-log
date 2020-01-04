package com.safframework.log.printer.file.name

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.name.DateWithHourFileNameGenerator
 * @author: Tony Shen
 * @date: 2020-01-04 00:17
 * @version: V2.4 <描述当前版本功能>
 */
class DateWithHourFileNameGenerator(val divider:Int) : FileNameGenerator {

    private val mLocalDateFormat: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {

        override fun initialValue() = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    }

    override fun isFileNameChangeable() = true

    override fun generateFileName(logLevel: Int, tag:String, timestamp: Long): String {

        var sdf = mLocalDateFormat.get()?: SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

        return sdf.let {

            it.timeZone = TimeZone.getDefault()
            val date = Date(timestamp)
            val dateString=  it.format(date)

            "$dateString-${date.hours/divider}"
        }


    }
}