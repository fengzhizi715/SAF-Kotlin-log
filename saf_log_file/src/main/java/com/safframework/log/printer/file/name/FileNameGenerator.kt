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

    fun generateFileName(logLevel: Int, tag:String, timestamp: Long): String
}