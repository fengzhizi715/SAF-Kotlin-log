package com.safframework.log.printer.file.name

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.name.LengthFileNameGenerator
 * @author: Tony Shen
 * @date: 2020-07-29 10:44
 * @version: V2.5 基于文件大小生成文件(默认情况下，超过1M会对日志进行分片)
 */
class LengthFileNameGenerator(val prefix:String, val length:Long = 1024*1024)  : FileNameGenerator {

    private val mLocalDateFormat: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {

        override fun initialValue() = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    }

    private val counter = AtomicLong(0)

    override fun isFileNameChangeable() = true

    override fun generateFileName(logLevel: Int, tag: String, timestamp: Long, lastFileName: String): String {

        if (lastFileName.isNotEmpty()) {

            val file = File(lastFileName)
            if (file.length()<length) {
                return lastFileName
            } else {
                var sdf = mLocalDateFormat.get()?:SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

                return prefix + sdf.let {

                    it.timeZone = TimeZone.getDefault()
                    it.format(Date(timestamp))
                } + counter.incrementAndGet();
            }
        } else {
            var sdf = mLocalDateFormat.get()?:SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

            return prefix + sdf.let {

                it.timeZone = TimeZone.getDefault()
                it.format(Date(timestamp))
            } + counter.incrementAndGet();
        }
    }
}