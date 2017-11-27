package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.parser.ReferenceParser
import java.lang.ref.Reference

/**
 * Created by tony on 2017/11/27.
 */
class ReferenceHandler():BaseHandler() {

    override fun handle(obj: Any): Boolean {

        if (obj is Reference<*>) {
            val s = L.getMethodNames()
            val parser = ReferenceParser()
            println(String.format(s, parser.parseString(obj)))
            return true
        }

        return false
    }
}