package com.safframework.log.formatter

import com.safframework.log.LoggerPrinter

/**
 *
 * @FileName:
 *          com.safframework.log.formatter.BorderFormatter
 * @author: Tony Shen
 * @date: 2019-09-15 15:14
 * @since: V2.0 <描述当前版本功能>
 */
object BorderFormatter:Formatter {

    override fun top()    = LoggerPrinter.BR + LoggerPrinter.TOP_BORDER + LoggerPrinter.BR + LoggerPrinter.HORIZONTAL_DOUBLE_LINE

    override fun middle() = LoggerPrinter.BR + LoggerPrinter.MIDDLE_BORDER + LoggerPrinter.BR

    override fun bottom() = LoggerPrinter.BR + LoggerPrinter.BOTTOM_BORDER + LoggerPrinter.BR

    override fun spliter()= LoggerPrinter.HORIZONTAL_DOUBLE_LINE
}