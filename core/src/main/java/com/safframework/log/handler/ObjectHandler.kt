package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import com.safframework.log.bean.JSONConfig
import com.safframework.log.extension.formatJSON
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class ObjectHandler:BaseHandler() {

    override fun handle(obj: Any, jsonConfig: JSONConfig): Boolean {

        if (L.getConverter()!=null) {

            jsonConfig.printers.map {

                val formatter = it.formatter

                var msg = obj.toJavaClass() + LoggerPrinter.BR + formatter.spliter()

                val message = L.getConverter()!!.toJson(obj).run {
                    JSONObject(this)
                }
                .formatJSON()
                .run {
                    replace("\n", "\n${formatter.spliter()}")
                }

                val s = L.getMethodNames(formatter)
                it.printLog(jsonConfig.logLevel,jsonConfig.tag,String.format(s, msg + message))
            }
            return true
        }

        return false
    }
}