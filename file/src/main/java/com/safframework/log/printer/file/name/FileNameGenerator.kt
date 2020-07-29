package com.safframework.log.printer.file.name

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.name.FileNameGenerator
 * @author: Tony Shen
 * @date: 2019-08-31 22:38
 * @since: V2.0 简单的文件命名/生成策略
 */
interface FileNameGenerator {

    fun isFileNameChangeable(): Boolean

    /**
     * 生成文件的名称
     * @param logLevel  日志的等级
     * @param tag       日志的 tag
     * @param timestamp 时间戳
     * @param lastFileName 上一个日志分片的名称
     */
    fun generateFileName(logLevel: Int, tag:String, timestamp: Long, lastFileName: String): String
}