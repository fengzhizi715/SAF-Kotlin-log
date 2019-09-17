package com.safframework.log.handler

import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.utils.formatJSON
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
            L.printers().map {
                it.println(LogLevel.INFO, this.logTag(),String.format(s, parseString(obj)))
            }
            return true
        }

        return false
    }

    override fun parseString(reference: Reference<*>): String {
        val actual = reference.get()

        var msg = reference.javaClass.canonicalName + "<" + actual?.toJavaClass() + ">"+ LoggerPrinter.BR + L.formatter().spliter()

        val isPrimitiveType = isPrimitiveType(actual)

        if (isPrimitiveType) {

            msg += "{$actual}"
        } else {

            msg += JSONObject(JSON.toJSONString(actual))
                    .formatJSON()
                    .let {
                        it.replace("\n".toRegex(), "\n${L.formatter().spliter()}")
                    }
        }

        return msg
    }
}