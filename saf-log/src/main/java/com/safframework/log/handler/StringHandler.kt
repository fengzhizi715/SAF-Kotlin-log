package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.parser.StringParser

/**
 * Created by tony on 2017/11/27.
 */
class StringHandler():BaseHandler() {

    override fun handle(obj: Any): Boolean {

        if (obj is String) {

            var json = obj.trim { it <= ' ' }
            val s = L.getMethodNames()
            val parser = StringParser()
            println(String.format(s, parser.parseString(json)))
            return true
        }

        return false
    }

}