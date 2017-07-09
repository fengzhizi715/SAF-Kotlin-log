package com.safframework.http.interceptor

import android.text.TextUtils
import android.util.Log
import cn.magicwindow.toutiao.http.interceptor.LoggingInterceptor
import okhttp3.Request
import okio.Buffer
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * Created by Tony Shen on 2017/7/9.
 */
class Logger protected constructor() {

    init {
        throw UnsupportedOperationException()
    }

    companion object {

        private val JSON_INDENT = 3
        private val LINE_SEPARATOR = System.getProperty("line.separator")
        private val OMITTED_REQUEST = arrayOf(LINE_SEPARATOR, "Omitted request body")
        private val OMITTED_RESPONSE = arrayOf(LINE_SEPARATOR, "Omitted response body")
        private val DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR
        private val MAX_LONG_SIZE = 110
        private val N = "\n"
        private val T = "\t"

        private fun isEmpty(line: String): Boolean {
            return TextUtils.isEmpty(line) || N == line || T == line || TextUtils.isEmpty(line.trim { it <= ' ' })
        }

        @JvmStatic fun printJsonRequest(builder: LoggingInterceptor.Builder, request: Request) {
            val requestBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + bodyToString(request)
            val tag = builder.getTag(true)
            Log.i(tag,
                    "╔══════ Request ════════════════════════════════════════════════════════════════════════")

            logLines(builder.type, tag, getRequest(request, builder.level))
            if (builder.level === Level.BASIC || builder.level === Level.BODY) {
                logLines(builder.type, tag, requestBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            }
            Log.i(tag,

                    "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }

        @JvmStatic fun printFileRequest(builder: LoggingInterceptor.Builder, request: Request) {
            val tag = builder.getTag(true)
            Log.i(tag,
                    "╔══════ Request ════════════════════════════════════════════════════════════════════════")

            logLines(builder.type, tag, getRequest(request, builder.level))
            if (builder.level === Level.BASIC || builder.level === Level.BODY) {
                logLines(builder.type, tag, OMITTED_REQUEST)
            }
            Log.i(tag,

                    "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }

        @JvmStatic fun printJsonResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean,
                                       code: Int, headers: String, bodyString: String, segments: List<String>) {
            val responseBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + getJsonString(bodyString)
            val tag = builder.getTag(false)
            Log.i(tag,
                    "╔══════ Response ═══════════════════════════════════════════════════════════════════════")

            logLines(builder.type, tag, getResponse(headers, chainMs, code, isSuccessful,
                    builder.level, segments))
            if (builder.level === Level.BASIC || builder.level === Level.BODY) {
                logLines(builder.type, tag, responseBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            }
            Log.i(tag,

                    "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }

        @JvmStatic fun printFileResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean,
                                       code: Int, headers: String, segments: List<String>) {
            val tag = builder.getTag(false)
            Log.i(tag,
                    "╔══════ Response ═══════════════════════════════════════════════════════════════════════")

            logLines(builder.type, tag, getResponse(headers, chainMs, code, isSuccessful,
                    builder.level, segments))
            logLines(builder.type, tag, OMITTED_RESPONSE)
            Log.i(tag,

                    "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }

        private fun getRequest(request: Request, level: Level): Array<String> {
            val message: String
            val header = request.headers().toString()
            val loggableHeader = level === Level.HEADERS || level === Level.BASIC
            message = "URL: " + request.url() + DOUBLE_SEPARATOR + "Method: @" + request.method() + DOUBLE_SEPARATOR +
                    if (isEmpty(header)) "" else if (loggableHeader) "Headers:" + LINE_SEPARATOR + dotHeaders(header) else ""
            return message.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun getResponse(header: String, tookMs: Long, code: Int, isSuccessful: Boolean,
                                level: Level, segments: List<String>): Array<String> {
            val message: String
            val loggableHeader = level === Level.HEADERS || level === Level.BASIC
            val segmentString = slashSegments(segments)
            message = (if (!TextUtils.isEmpty(segmentString)) segmentString + " - " else "") + "is success : " + isSuccessful + " - " + "Received in: " + tookMs + "ms" + DOUBLE_SEPARATOR + "Status Code: " +
                    code + DOUBLE_SEPARATOR + if (isEmpty(header))
                ""
            else if (loggableHeader)
                "Headers:" + LINE_SEPARATOR +
                        dotHeaders(header)
            else
                ""
            return message.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun slashSegments(segments: List<String>): String {
            val segmentString = StringBuilder()
            for (segment in segments) {
                segmentString.append("/").append(segment)
            }
            return segmentString.toString()
        }

        private fun dotHeaders(header: String): String {
            val headers = header.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val builder = StringBuilder()
            for (item in headers) {
                builder.append("- ").append(item).append("\n")
            }
            return builder.toString()
        }

        private fun logLines(type: Int, tag: String, lines: Array<String>) {
            for (line in lines) {
                val lineLength = line.length
                for (i in 0..lineLength / MAX_LONG_SIZE) {
                    val start = i * MAX_LONG_SIZE
                    var end = (i + 1) * MAX_LONG_SIZE
                    end = if (end > line.length) line.length else end
                    Log.i(tag, "║ " + line.substring(start, end))
                }
            }
        }

        private fun bodyToString(request: Request): String {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                if (copy.body() == null)
                    return ""
                copy.body()!!.writeTo(buffer)
                return getJsonString(buffer.readUtf8())
            } catch (e: IOException) {
                return "{\"err\": \"" + e.message + "\"}"
            }

        }

        @JvmStatic fun getJsonString(msg: String): String {
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

}
