package com.safframework.log.handler

import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.formatter.Formatter
import com.safframework.log.logTag
import com.safframework.log.printer.Printer
import com.safframework.log.utils.formatJSON
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class ObjectHandler(printer: Printer, formatter: Formatter):BaseHandler(printer,formatter) {

    override fun handle(obj: Any): Boolean {

        val s = L.getMethodNames()

        var msg = obj.toJavaClass() + LoggerPrinter.BR + spliter()

        val message = JSON.toJSONString(obj).run {
            JSONObject(this)
        }
        .formatJSON()
        .let {
            it.replace("\n".toRegex(), "\n${spliter()}")
         }

        println(LogLevel.INFO, this.logTag(),String.format(s, msg + message))
        return true
    }
}