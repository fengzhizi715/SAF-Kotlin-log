package com.safframework.log.handler

import android.os.Bundle
import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.logTag
import com.safframework.log.parser.Parser
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
            printer.println(LogLevel.INFO, this.logTag(), String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(bundle: Bundle): String {

        var msg = bundle.toJavaClass() + LoggerPrinter.BR + "║ "

        val jsonObject = JSONObject().parseBundle(bundle)

        var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }
}