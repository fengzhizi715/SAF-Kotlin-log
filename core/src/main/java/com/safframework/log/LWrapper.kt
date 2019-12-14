package com.safframework.log

import com.safframework.log.converter.Converter

/**
 *
 * @FileName:
 *          com.safframework.log.LWrapper
 * @author: Tony Shen
 * @date: 2019-12-11 17:10
 * @version: V2.4 <描述当前版本功能>
 */
class LWrapper {

    var logLevel:LogLevel          = LogLevel.DEBUG

    var header:String?             = null

    var tag:String                 = "SAF_L"

    var converter: Converter?      = null

    var displayThreadInfo:Boolean  = true

    var displayClassInfo:Boolean   = true
}

fun configL(init: LWrapper.() -> Unit):L {

    val wrap = LWrapper()

    wrap.init()

    return configWrap(wrap)
}

internal fun configWrap(wrap:LWrapper):L {

    L.logLevel = wrap.logLevel

    wrap.header?.let {
        L.header(it)
    }

    wrap.tag?.let {
        L.init(it)
    }

    wrap.converter?.let {
        L.converter(it)
    }

    L.displayClassInfo(wrap.displayClassInfo)

    L.displayThreadInfo(wrap.displayThreadInfo)

    return L
}