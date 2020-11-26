package com.safframework.log.extension

import com.safframework.bytekit.bytes.ByteBufferBytes
import com.safframework.log.L

/**
 *
 * @FileName:
 *          com.safframework.log.extension.`L+Extension`
 * @author: Tony Shen
 * @date: 2020-11-26 21:25
 * @version: V1.0 <描述当前版本功能>
 */
fun L.bytes(bytes: ByteArray) {

    val byteString = ByteBufferBytes.create(bytes).toHexString()
    L.i(byteString)
}