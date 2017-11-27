package com.safframework.log.handler

import android.net.Uri
import com.safframework.log.L
import com.safframework.log.parser.UriParser

/**
 * Created by tony on 2017/11/27.
 */
class UriHandler():BaseHandler() {

    override fun handle(obj: Any): Boolean {

        if (obj is Uri) {

            val s = L.getMethodNames()
            val parser = UriParser()
            println(String.format(s, parser.parseString(obj)))
            return true
        }

        return false
    }

}