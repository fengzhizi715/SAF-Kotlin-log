package com.safframework.log.printer.file.name

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.name.TagFileNameGenerator
 * @author: Tony Shen
 * @date: 2019-11-06 01:56
 * @since: V2.2 通过 tag 生成对应的日志文件
 */
class TagFileNameGenerator : FileNameGenerator {

    override fun isFileNameChangeable() = true

    override fun generateFileName(logLevel: Int, tag: String, timestamp: Long, lastFileName: String) = tag
}