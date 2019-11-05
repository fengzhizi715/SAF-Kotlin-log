package com.safframework.log.printer.file.name

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.name.TagFileNameGenerator
 * @author: Tony Shen
 * @date: 2019-11-06 01:56
 * @version: V2.2 <描述当前版本功能>
 */
class TagFileNameGenerator : FileNameGenerator {

    override fun isFileNameChangeable() = true

    override fun generateFileName(logLevel: Int, tag: String, timestamp: Long): String {

        return tag
    }

}