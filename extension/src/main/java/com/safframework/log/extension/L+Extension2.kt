package com.safframework.log.extension

import com.safframework.bytekit.bytes.ByteBufferBytes
import com.safframework.log.L

/**
 *
 * @FileName:
 *          com.safframework.log.extension.`L+Extension2`
 * @author: Tony Shen
 * @date: 2020-11-26 21:25
 * @version: V1.0 <描述当前版本功能>
 */
fun L.bytes(bytes: ByteArray, separator: CharSequence = "-") {
    val hexString = ByteBufferBytes.create(bytes).toHexString()
    if (separator.isNotEmpty()) {
        var m: Int = hexString.length / 2
        if (m * 2 < hexString.length) {
            m++
        }
        val strs = arrayOfNulls<String>(m)
        var j = 0
        for (i in 0 until hexString.length) {
            if (i % 2 == 0) { //每隔两个
                strs[j] = "" + hexString[i]
            } else {
                strs[j] = strs[j] + hexString[i] //将字符加上du两个空格
                j++
            }
        }

        val newHexString = strs.joinToString ("-")
        L.i(newHexString)
    } else {
        L.i(hexString)
    }
}