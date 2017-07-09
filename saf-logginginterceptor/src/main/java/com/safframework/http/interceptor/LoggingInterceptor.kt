package com.safframework.http.interceptor

import android.text.TextUtils
import okhttp3.*
import okhttp3.internal.platform.Platform
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Tony Shen on 2017/7/9.
 */
class LoggingInterceptor private constructor(private val builder: LoggingInterceptor.Builder) : Interceptor {

    private val isDebug: Boolean

    init {
        this.isDebug = builder.isDebug
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (builder.headers.size() > 0) {
            val headers = request.headers()
            val names = headers.names()
            val iterator = names.iterator()
            val requestBuilder = request.newBuilder()
            requestBuilder.headers(builder.headers)
            while (iterator.hasNext()) {
                val name = iterator.next()
                requestBuilder.addHeader(name, headers.get(name)!!)
            }
            request = requestBuilder.build()
        }

        if (!isDebug || builder.getLevel() === Level.NONE) {
            return chain.proceed(request)
        }
        val requestBody = request.body()

        var rContentType: MediaType? = null
        if (requestBody != null) {
            rContentType = request.body()!!.contentType()
        }

        var rSubtype: String? = null
        if (rContentType != null) {
            rSubtype = rContentType.subtype()
        }

        if (rSubtype != null && (rSubtype.contains("json")
                || rSubtype.contains("xml")
                || rSubtype.contains("plain")
                || rSubtype.contains("html"))) {
            Logger.printJsonRequest(builder, request)
        } else {
            Logger.printFileRequest(builder, request)
        }

        val st = System.nanoTime()
        val response = chain.proceed(request)

        val segmentList = (request.tag() as Request).url().encodedPathSegments()
        val chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st)
        val header = response.headers().toString()
        val code = response.code()
        val isSuccessful = response.isSuccessful
        val responseBody = response.body()
        val contentType = responseBody!!.contentType()

        var subtype: String? = null
        val body: ResponseBody

        if (contentType != null) {
            subtype = contentType.subtype()
        }

        if (subtype != null && (subtype.contains("json")
                || subtype.contains("xml")
                || subtype.contains("plain")
                || subtype.contains("html"))) {
            val bodyString = Logger.getJsonString(responseBody.string())
            Logger.printJsonResponse(builder, chainMs, isSuccessful, code, header, bodyString, segmentList)
            body = ResponseBody.create(contentType, bodyString)
        } else {
            Logger.printFileResponse(builder, chainMs, isSuccessful, code, header, segmentList)
            return response
        }

        return response.newBuilder().body(body).build()
    }

    class Builder {

        var TAG = "SAF_Logging_Interceptor"

        var isDebug: Boolean = false
        internal var type = Platform.INFO
            private set
        private var requestTag: String? = null
        private var responseTag: String? = null
        var level = Level.BASIC
        private val builder: Headers.Builder

        init {
            builder = Headers.Builder()
        }

        internal fun getLevel(): Level {
            return level
        }

        internal val headers: Headers
            get() = builder.build()

        internal fun getTag(isRequest: Boolean): String {
            if (isRequest) {
                return if (TextUtils.isEmpty(requestTag)) TAG else requestTag!!
            } else {
                return if (TextUtils.isEmpty(responseTag)) TAG else responseTag!!
            }
        }

        /**
         * @param name  Filed
         * *
         * @param value Value
         * *
         * @return Builder
         * * Add a field with the specified value
         */
        fun addHeader(name: String, value: String): Builder {
            builder.set(name, value)
            return this
        }

        /**
         * @param level set log level
         * *
         * @return Builder
         * *
         * @see Level
         */
        fun setLevel(level: Level): Builder {
            this.level = level
            return this
        }

        /**
         * Set request and response each log tag

         * @param tag general log tag
         * *
         * @return Builder
         */
        fun tag(tag: String): Builder {
            TAG = tag
            return this
        }

        /**
         * Set request log tag

         * @param tag request log tag
         * *
         * @return Builder
         */
        fun request(tag: String): Builder {
            this.requestTag = tag
            return this
        }

        /**
         * Set response log tag

         * @param tag response log tag
         * *
         * @return Builder
         */
        fun response(tag: String): Builder {
            this.responseTag = tag
            return this
        }

        /**
         * @param isDebug set can sending log output
         * *
         * @return Builder
         */
        fun loggable(isDebug: Boolean): Builder {
            this.isDebug = isDebug
            return this
        }

        /**
         * @param type set sending log output type
         * *
         * @return Builder
         * *
         * @see Platform
         */
        fun log(type: Int): Builder {
            this.type = type
            return this
        }

        fun build(): LoggingInterceptor {
            return LoggingInterceptor(this)
        }
    }

}
