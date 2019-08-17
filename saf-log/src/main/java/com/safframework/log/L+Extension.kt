package com.safframework.log

/**
 * Created by Tony Shen on 2017/6/30.
 */
typealias msgFunction = () -> String

fun String?.e() = L.e(this)

fun String?.w() = L.w(this)

fun String?.i() = L.i(this)

fun String?.d() = L.d(this)

fun Any?.json()  = L.json(this)

inline fun <reified T> T.logTag() = T::class.java.simpleName

inline fun <reified T> Class<T>.logTag() = simpleName

fun L.e(msg: msgFunction) {

    L.e(msg.invoke())
}


fun L.e(tag: String?, msg: msgFunction) {

    L.e(tag, msg.invoke())
}


fun L.w(msg: msgFunction) {

    L.w(msg.invoke())
}


fun L.w(tag: String?, msg: msgFunction) {

    L.w(tag, msg.invoke())
}


fun L.i(msg: msgFunction) {

    L.i(msg.invoke())
}

fun L.i(tag: String?, msg: msgFunction) {

    L.i(tag, msg.invoke())
}


fun L.d(msg: msgFunction) {

    L.d(msg.invoke())
}


fun L.d(tag: String?, msg: msgFunction) {

    L.d(tag, msg.invoke())
}