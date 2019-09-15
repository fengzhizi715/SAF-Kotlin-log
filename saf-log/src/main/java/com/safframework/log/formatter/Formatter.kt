package com.safframework.log.formatter

import com.safframework.log.LogLevel

/**
 *
 * @FileName:
 *          com.safframework.log.formatter.Formatter
 * @author: Tony Shen
 * @date: 2019-09-15 14:49
 * @version: V1.0 <描述当前版本功能>
 */
interface Formatter {

    fun top():String

    fun middle():String

    fun bottom():String
}