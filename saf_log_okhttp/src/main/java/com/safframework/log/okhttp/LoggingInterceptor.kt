package com.safframework.log.okhttp

import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

/**
 *
 * @FileName:
 *          com.safframework.log.okhttp.LoggingInterceptor
 * @author: Tony Shen
 * @date: 2019-09-21 12:36
 * @since: V2.0 OkHttp 的日志拦截器
 */
class LoggingInterceptor: Interceptor {

    companion object {

        private const val JSON_INDENT = 3
        private const val MAX_STRING_LENGTH = 4000
    }


    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val header = request.headers.toString()

        val requestLog = " URL: " + request.url  + LoggerPrinter.BR +
                " Method: @" + request.method  + LoggerPrinter.BR +
                if (header.isNullOrEmpty()) "" else " Headers: " + header + LoggerPrinter.BR

        L.i(requestLog)

        val response = chain.proceed(request)

        val responseBody = response.body
        val contentType = responseBody!!.contentType()

        var subtype: String? = null

        if (contentType != null) {
            subtype = contentType.subtype
        }

        if (subtypeIsNotFile(subtype)) {

            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer()

            val bodyString = getJsonString(buffer.clone().readString(Charset.forName("UTF-8")))

            L.i(bodyString)
        }

        return response
    }

    private fun subtypeIsNotFile(subtype: String?): Boolean = subtype != null && (subtype.contains("json")
            || subtype.contains("xml")
            || subtype.contains("plain")
            || subtype.contains("html"))

    private fun getJsonString(msg: String): String {

        var message: String
        try {
            if (msg.startsWith("{")) {
                val jsonObject = JSONObject(msg)
                message = jsonObject.toString(JSON_INDENT)
            } else if (msg.startsWith("[")) {
                val jsonArray = JSONArray(msg)
                message = jsonArray.toString(JSON_INDENT)
            } else {
                message = msg
            }
        } catch (e: JSONException) {
            message = msg
        }

        return message
    }
}