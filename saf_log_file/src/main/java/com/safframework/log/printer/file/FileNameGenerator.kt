package com.safframework.log.printer.file

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.FileNameGenerator
 * @author: Tony Shen
 * @date: 2019-08-31 22:38
 * @version: V1.0 <描述当前版本功能>
 */
interface FileNameGenerator {

    fun isFileNameChangeable(): Boolean

    fun generateFileName(logLevel: Int, timestamp: Long): String
}