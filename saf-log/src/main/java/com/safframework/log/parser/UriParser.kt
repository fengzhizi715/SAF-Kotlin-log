package com.safframework.log.parser

import android.net.Uri
import com.safframework.log.LoggerPrinter

/**
 * Created by tony on 2017/11/25.
 */
class UriParser : Parser<Uri> {

    override fun parseString(uri: Uri): String {

        var msg = uri.javaClass.toString() + LoggerPrinter.BR + "║ "

        return msg
    }

}