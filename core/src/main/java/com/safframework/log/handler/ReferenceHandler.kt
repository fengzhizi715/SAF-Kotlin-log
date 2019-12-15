package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.bean.JSONConfig
import com.safframework.log.extension.formatJSON
import com.safframework.log.formatter.Formatter
import com.safframework.log.parser.Parser
import com.safframework.log.utils.isPrimitiveType
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject
import java.lang.ref.Reference

/**
 * Created by tony on 2017/11/27.
 */
class ReferenceHandler:BaseHandler(), Parser<Reference<*>> {

    override fun handle(obj: Any, jsonConfig: JSONConfig): Boolean {

        if (obj is Reference<*>) {

            jsonConfig.printers.map {
                val s = L.getMethodNames(it.formatter)
                it.printLog(jsonConfig.logLevel,jsonConfig.tag,String.format(s, parseString(obj,it.formatter)))
            }
            return true
        }

        return false
    }

    override fun parseString(reference: Reference<*>,formatter: Formatter): String {
        val actual = reference.get()

        var msg = reference.javaClass.canonicalName + "<" + actual?.toJavaClass() + ">"+ LoggerPrinter.BR + formatter.spliter()

        val isPrimitiveType = isPrimitiveType(actual)

        msg += if (isPrimitiveType) {

            "{$actual}"
        } else {

            L.getConverter()?.takeIf { actual != null }?.let {

                JSONObject(it.toJson(actual!!))
                        .formatJSON()
                        .let {
                            it.replace("\n", "\n${formatter.spliter()}")
                        }

            }?:""
        }

        return msg
    }
}