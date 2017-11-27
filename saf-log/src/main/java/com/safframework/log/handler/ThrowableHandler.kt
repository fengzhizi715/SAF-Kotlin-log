package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.parser.ThrowableParser

/**
 * Created by tony on 2017/11/27.
 */
class ThrowableHandler():BaseHandler() {

    override fun handle(obj: Any): Boolean {

        if (obj is Throwable) {

            val s = L.getMethodNames()
            val parser = ThrowableParser()
            System.err.println(String.format(s, parser.parseString(obj)))
            return true
        }

        return false
    }

}