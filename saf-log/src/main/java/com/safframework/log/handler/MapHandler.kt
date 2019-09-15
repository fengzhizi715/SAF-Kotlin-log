package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.utils.formatJSON
import com.safframework.log.utils.parseMap
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class MapHandler:BaseHandler(),Parser<Map<*,*>>{

    override fun handle(obj: Any): Boolean {

        if (obj is Map<*,*>) {
            val s = L.getMethodNames()
            printer.println(LogLevel.INFO, this.logTag(),String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(map: Map<*, *>): String {

        var msg = map.toJavaClass() + LoggerPrinter.BR + LoggerPrinter.HORIZONTAL_DOUBLE_LINE

        return msg + JSONObject().parseMap(map)
                .formatJSON()
                .let {
                    it.replace("\n".toRegex(), "\n${LoggerPrinter.HORIZONTAL_DOUBLE_LINE}")
                }
    }

}