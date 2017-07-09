package com.safframework.http.interceptor

import com.safframework.log.L
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.io.IOException

/**
 * Created by Tony Shen on 2017/7/9.
 */
class HttpLoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val requestBody = request.body()

        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1

        val requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol

        L.i(requestStartMessage)

        //        if (requestBody!=null) {
        //            L.i(requestBody.toString());
        //        }

        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            throw e
        }

        return response
    }
}