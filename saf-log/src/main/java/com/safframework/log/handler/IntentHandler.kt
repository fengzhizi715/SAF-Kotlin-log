package com.safframework.log.handler

import android.content.Intent
import com.safframework.log.L
import com.safframework.log.parser.IntentParser

/**
 * Created by tony on 2017/11/27.
 */
class IntentHandler():BaseHandler() {

    override fun handle(obj: Any): Boolean {

        if (obj is Intent) {

            val s = L.getMethodNames()
            val parser = IntentParser()
            println(String.format(s, parser.parseString(obj)))
            return true
        }

        return false
    }
}