package com.safframework.log.handler

import android.os.Bundle
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.utils.formatJSON
import com.safframework.log.utils.parseBundle
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class BundleHandler:BaseHandler(), Parser<Bundle> {

    override fun handle(obj: Any): Boolean {

        if (obj is Bundle) {

            val s = L.getMethodNames()
            L.printers().map {
                it.println(LogLevel.INFO, this.logTag(), String.format(s, parseString(obj)))
            }

            return true
        }

        return false
    }

    override fun parseString(bundle: Bundle): String {

        var msg = bundle.toJavaClass() + LoggerPrinter.BR + L.formatter().spliter()

        return msg + JSONObject().parseBundle(bundle)
                .formatJSON()
                .let {
                    it.replace("\n".toRegex(), "\n${L.formatter().spliter()}")
                }
    }
}