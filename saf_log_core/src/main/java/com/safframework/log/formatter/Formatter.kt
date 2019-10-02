package com.safframework.log.formatter

/**
 *
 * @FileName:
 *          com.safframework.log.formatter.Formatter
 * @author: Tony Shen
 * @date: 2019-09-15 14:49
 * @since: V2.0 格式化日志，便于 Printer 进行打印。每一个 Printer 包含一个自身的 Formatter
 */
interface Formatter {

    fun top():String

    fun middle():String

    fun bottom():String

    fun spliter():String
}