package cn.magicwindow.toutiao.http.interceptor

import android.text.TextUtils
import android.util.Log
import okhttp3.Request
import okio.Buffer
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * Created by Tony Shen on 2017/7/9.
 */
class Logger {

    companion object {

        private val JSON_INDENT = 3
        private val MAX_LONG_SIZE = 110
        private val N = "\n"
        private val T = "\t"

        private val TOP_LEFT_CORNER = '╔'
        private val BOTTOM_LEFT_CORNER = '╚'
        private val DOUBLE_DIVIDER = "═════════════════════════════════════════════════"
        private val TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER
        private val BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER
        private val LINE_SEPARATOR = System.getProperty("line.separator")
        private val DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR

        private fun isEmpty(line: String) = line.isEmpty() || N == line || T == line || line.trim { it <= ' ' }.isEmpty()

        @JvmStatic
        fun printJsonRequest(builder: LoggingInterceptor.Builder, request: Request) {
            val requestBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + bodyToString(request)
            val tag = builder.getTag(true)
            Log.i(tag, TOP_BORDER)

            logLines(tag, getRequest(request))
            logLines(tag, requestBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

            Log.i(tag, BOTTOM_BORDER)
        }

        @JvmStatic
        fun printFileRequest(builder: LoggingInterceptor.Builder, request: Request) {

            val requestBody = LINE_SEPARATOR + binaryBodyToString(request)

            val tag = builder.getTag(true)
            Log.i(tag, TOP_BORDER)

            logLines(tag, getRequest(request))
            logLines(tag, requestBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

            Log.i(tag, BOTTOM_BORDER)
        }

        @JvmStatic
        fun printJsonResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean,
                                       code: Int, headers: String, bodyString: String, segments: List<String>) {
            val responseBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + getJsonString(bodyString)
            val tag = builder.getTag(false)
            Log.i(tag, TOP_BORDER)

            logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments))
            logLines(tag, responseBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            Log.i(tag, BOTTOM_BORDER)
        }

        @JvmStatic
        fun printFileResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean,
                                       code: Int, headers: String, segments: List<String>) {
            val tag = builder.getTag(false)
            Log.i(tag, TOP_BORDER)
            logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments))
            Log.i(tag, BOTTOM_BORDER)
        }

        private fun getRequest(request: Request): Array<String> {

            val header = request.headers().toString()
            val message: String = "URL: " + request.url() + DOUBLE_SEPARATOR + "Method: @" + request.method() + DOUBLE_SEPARATOR +
                    if (isEmpty(header)) "" else "Headers:" + LINE_SEPARATOR + dotHeaders(header)
            return message.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun getResponse(header: String, tookMs: Long, code: Int, isSuccessful: Boolean,
                                segments: List<String>): Array<String> {

            val segmentString = slashSegments(segments)
            val message: String = (if (!TextUtils.isEmpty(segmentString)) segmentString + " - " else "") + "is success : " + isSuccessful + " - " + "Received in: " + tookMs + "ms" + DOUBLE_SEPARATOR + "Status Code: " +
                    code + DOUBLE_SEPARATOR +
                    if (isEmpty(header)) "" else "Headers:" + LINE_SEPARATOR + dotHeaders(header)

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

        private fun logLines(tag: String, lines: Array<String>) {
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
                if (copy.body() == null) return ""

                copy.body()!!.writeTo(buffer)
                return getJsonString(buffer.readUtf8())
            } catch (e: IOException) {
                return "{\"err\": \"" + e.message + "\"}"
            }
        }

        private fun binaryBodyToString(request: Request): String {

            val copy = request.newBuilder().build()
            val requestBody = copy.body()
            if (requestBody == null) return ""

            var buffer:String?
            if (requestBody.contentType()!=null) {
                buffer = "Content-Type: "+requestBody.contentType().toString()
            } else {
                buffer  = ""
            }

            if (requestBody.contentLength()>0) {
                buffer += LINE_SEPARATOR + "Content-Length: "+requestBody.contentLength()
            }

            return buffer
        }

        @JvmStatic
        fun getJsonString(msg: String): String {

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
