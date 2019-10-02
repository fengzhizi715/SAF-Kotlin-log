package com.safframework.log.handler

import android.content.Intent
import android.os.Bundle
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.formatter.Formatter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
import com.safframework.log.utils.formatJSON
import com.safframework.log.utils.parseBundle
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class IntentHandler:BaseHandler(), Parser<Intent> {

    override fun handle(obj: Any): Boolean {

        if (obj is Intent) {

            L.printers().map {
                val s = L.getMethodNames(it.formatter)
                it.printLog(LogLevel.INFO, this.logTag(),String.format(s, parseString(obj,it.formatter)))
            }
            return true
        }

        return false
    }

    override fun parseString(intent: Intent,formatter: Formatter): String {

        var msg = intent.toJavaClass()+ LoggerPrinter.BR + formatter.spliter()

        return msg + JSONObject().apply {

            put("Scheme", intent.scheme)
            put("Action", intent.action)
            put("DataString", intent.dataString)
            put("Type", intent.type)
            put("Package", intent.`package`)
            put("ComponentInfo", intent.component)
            put("Categories", intent.categories)
            if (intent.extras!=null) {
                put("Extras", JSONObject(parseBundleString(intent.extras)))
            }
        }
        .formatJSON()
        .let {
            it.replace("\n", "\n${formatter.spliter()}")
        }
    }

    private fun parseBundleString(extras: Bundle) = JSONObject().parseBundle(extras).formatJSON()
}