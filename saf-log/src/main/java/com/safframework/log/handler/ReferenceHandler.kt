package com.safframework.log.handler

import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.utils.isPrimitiveType
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject
import java.lang.ref.Reference

/**
 * Created by tony on 2017/11/27.
 */
class ReferenceHandler:BaseHandler(), Parser<Reference<*>> {

    override fun handle(obj: Any): Boolean {

        if (obj is Reference<*>) {
            val s = L.getMethodNames()
//            println(String.format(s, parseString(obj)))
            printer.println(LogLevel.INFO, this.logTag(),String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(reference: Reference<*>): String {
        val actual = reference.get()

        var msg = reference.javaClass.canonicalName + "<" + actual?.toJavaClass() + ">"+ LoggerPrinter.BR + "║ "

        val isPrimitiveType = isPrimitiveType(actual)

        if (isPrimitiveType) {

            msg += "{" + actual + "}"
        } else {

            val jsonObject = JSONObject(JSON.toJSONString(actual))
            var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
            message = message.replace("\n".toRegex(), "\n║ ")
            msg += message
        }

        return msg
    }
}