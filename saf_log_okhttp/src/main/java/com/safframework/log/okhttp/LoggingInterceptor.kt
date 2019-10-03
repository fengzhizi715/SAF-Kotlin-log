package com.safframework.log.okhttp

import android.text.TextUtils
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

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

        val st = System.nanoTime()

        val response = chain.proceed(request)

        val chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st)
        val code = response.code
        val segmentList = request.url.encodedPathSegments
        val isSuccessful = response.isSuccessful
        val responseBody = response.body
        val contentType = responseBody?.contentType()

        var subtype: String? = null

        if (contentType != null) {
            subtype = contentType.subtype
        }

        if (subtypeIsNotFile(subtype)) {

            responseBody?.let {

                val source = it.source()
                source.request(Long.MAX_VALUE)
                val buffer = source.buffer()

                val bodyString = getJsonString(buffer.clone().readString(Charset.forName("UTF-8")))

                val segmentString = slashSegments(segmentList)

                val responseString = StringBuilder().apply {

                    append(if (!TextUtils.isEmpty(segmentString)) segmentString + " - " else "")
                            .append("is success : $isSuccessful Received in: $chainMs ms")
                            .append(LoggerPrinter.BR)
                            .append(LoggerPrinter.BR)
                            .append("Status Code: $code")
                            .append(LoggerPrinter.BR)
                            .append(LoggerPrinter.BR)
                            .append(bodyString)

                }.toString()

                L.i(responseString)
            }
        }

        return response
    }

    private fun slashSegments(segments: List<String>): String {
        val segmentString = StringBuilder()
        for (segment in segments) {
            segmentString.append("/").append(segment)
        }
        return segmentString.toString()
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