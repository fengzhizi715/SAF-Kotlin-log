package com.safframework.log.parser

import com.safframework.log.formatter.Formatter


/**
 * 将对象解析成特定的字符串
 * Created by tony on 2017/11/24.
 */
interface Parser<T> {

    /**
     * 将对象解析成字符串
     */
    fun parseString(t: T,formatter: Formatter): String
}