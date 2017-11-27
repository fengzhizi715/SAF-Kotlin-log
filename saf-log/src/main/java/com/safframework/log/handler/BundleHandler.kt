package com.safframework.log.handler

import android.os.Bundle
import com.safframework.log.L
import com.safframework.log.parser.BundleParser

/**
 * Created by tony on 2017/11/27.
 */
class BundleHandler():BaseHandler() {

    override fun handle(obj: Any): Boolean {

        if (obj is Bundle) {

            val s = L.getMethodNames()
            val parser = BundleParser()
            println(String.format(s, parser.parseString(obj)))
            return true
        }

        return false
    }
}