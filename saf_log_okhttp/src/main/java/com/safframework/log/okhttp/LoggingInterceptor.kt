package com.safframework.log.okhttp

import android.text.TextUtils
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
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
        private const val MAX_LONG_SIZE = 120
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val header = request.headers.toString()
        val requestBody = request.body

        val requestString = StringBuilder().apply {

            append("URL: ${request.url}")
                    .append(LoggerPrinter.BR)
                    .append(LoggerPrinter.BR)
                    .append("Method: @${request.method}")

            if (header.isNullOrEmpty()) {
                append(LoggerPrinter.BR)
            } else {
                append(LoggerPrinter.BR)
                    .append(LoggerPrinter.BR)
                    .append("Headers: " + LoggerPrinter.BR + dotHeaders(header))
            }

            requestBody?.let {

                val bodyString = bodyToString(request).split(LoggerPrinter.BR).dropLastWhile { it.isEmpty() }.toTypedArray()

                append(LoggerPrinter.BR)
                        .append("Body: ")
                        .append(LoggerPrinter.BR)
                        .append(logLines(bodyString))
            }

        }.toString()

        L.i(requestString)

        val st = System.nanoTime()

        val response = chain.proceed(request)

        val chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st)
        val code = response.code
        val segmentList = request.url.encodedPathSegments
        val isSuccessful = response.isSuccessful
        val responseHeader = response.headers.toString()
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
                val buffer = source.buffer

                val bodyString = getJsonString(buffer.clone().readString(Charset.forName("UTF-8")))

                val segmentString = slashSegments(segmentList)

                val responseString = StringBuilder().apply {

                    append(if (!TextUtils.isEmpty(segmentString)) "$segmentString - " else "")
                            .append("is success : $isSuccessful Received in: $chainMs ms")
                            .append(LoggerPrinter.BR)
                            .append(LoggerPrinter.BR)
                            .append("Status Code: $code")
                            .append(LoggerPrinter.BR)
                            .append(LoggerPrinter.BR)
                            .append("Headers:")
                            .append(LoggerPrinter.BR)
                            .append(dotHeaders(responseHeader))
                            .append(LoggerPrinter.BR)
                            .append("Body:")
                            .append(LoggerPrinter.BR)
                            .append(bodyString)

                }.toString()

                L.json(responseString)
            }
        }

        return response
    }

    private fun bodyToString(request: Request): String {
        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            if (copy.body == null) return ""

            copy.body?.writeTo(buffer)
            return getJsonString(buffer.readUtf8())
        } catch (e: IOException) {
            return "{\"err\": \"" + e.message + "\"}"
        }
    }

    private fun logLines(lines: Array<String>) = StringBuilder().apply {
        for (line in lines) {
            val lineLength = line.length
            for (i in 0..lineLength / MAX_LONG_SIZE) {
                val start = i * MAX_LONG_SIZE
                var end = (i + 1) * MAX_LONG_SIZE
                end = if (end > line.length) line.length else end

                append(" " + line.substring(start, end)).append(LoggerPrinter.BR)
            }
        }
    }.toString()

    private fun subtypeIsNotFile(subtype: String?)= subtype != null && (subtype.contains("json")
            || subtype.contains("xml")
            || subtype.contains("plain")
            || subtype.contains("html"))

    private fun getJsonString(msg: String): String {

        var message: String
        try {
            if (msg.startsWith("{")) {
                val jsonObject = JSONObject(msg)
                message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
            } else if (msg.startsWith("[")) {
                val jsonArray = JSONArray(msg)
                message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
            } else {
                message = msg
            }
        } catch (e: JSONException) {
            message = msg
        }

        return message
    }

    private fun slashSegments(segments: List<String>) = StringBuilder().apply {
        for (segment in segments) {
            append("/").append(segment)
        }
    }.toString()

    private fun dotHeaders(header: String): String {
        val headers = header.split(LoggerPrinter.BR).dropLastWhile { it.isEmpty() }.toTypedArray()

        return StringBuilder().apply {
            if (headers != null && headers.isNotEmpty()) {
                for (item in headers) {
                    append(" - ").append(item).append("\n")
                }
            } else {
                append(LoggerPrinter.BR)
            }
        }.toString()
    }
}