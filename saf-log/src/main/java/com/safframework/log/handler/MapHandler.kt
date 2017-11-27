package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.parser.MapParser

/**
 * Created by tony on 2017/11/27.
 */
class MapHandler():BaseHandler(){

    override fun handle(obj: Any): Boolean {

        if (obj is Map<*,*>) {
            val s = L.getMethodNames()
            val parser = MapParser()
            println(String.format(s, parser.parseString(obj)))
            return true
        }

        return false
    }

}