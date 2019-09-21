package com.safframework.log.okhttp

import com.safframework.log.L
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @FileName:
 *          com.safframework.log.okhttp.LoggingInterceptor
 * @author: Tony Shen
 * @date: 2019-09-21 12:36
 * @version: V1.0 <描述当前版本功能>
 */
class LoggingInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        L.json(request)

        val response = chain.proceed(request)

        L.json(response)

        return response
    }

}