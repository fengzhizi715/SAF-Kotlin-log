package com.safframework.log.handler

import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.logTag
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class ObjectHandler:BaseHandler() {

    override fun handle(obj: Any): Boolean {

        val s = L.getMethodNames()

        var msg = obj.toJavaClass() + LoggerPrinter.BR + "║ "
        val objStr = JSON.toJSONString(obj)
        val jsonObject = JSONObject(objStr)
        var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        printer.println(LogLevel.INFO, this.logTag(),String.format(s, msg + message))
        return true
    }
}