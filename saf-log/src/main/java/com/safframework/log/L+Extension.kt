package com.safframework.log

/**
 * Created by Tony Shen on 2017/6/30.
 */

fun String?.e() = L.e(this)

fun String?.w() = L.w(this)

fun String?.i() = L.i(this)

fun String?.d() = L.d(this)

fun Any?.json()  = L.json(this)
