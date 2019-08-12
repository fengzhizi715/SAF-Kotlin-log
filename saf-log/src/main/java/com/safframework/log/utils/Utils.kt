package com.safframework.log.utils

/**
 * Created by tony on 2017/11/24.
 */

/**
 * 判断是否基本类型
 */
fun isPrimitiveType(value: Any?):Boolean = when(value){

    is Boolean -> true

    is String -> true

    is Int -> true

    is Float -> true

    is Double ->  true

    else -> false
}
