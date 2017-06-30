package com.safframework.log

/**
 * Created by Tony Shen on 2017/6/30.
 */

fun Any.e(text: String)  = L.e(text)

fun Any.w(text: String)  = L.w(text)

fun Any.i(text: String)  = L.d(text)

fun Any.d(text: String)  = L.i(text)

fun Any.json()  = L.json(this)
