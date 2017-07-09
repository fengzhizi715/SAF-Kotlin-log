package com.safframework.http.interceptor

/**
 * Created by Tony Shen on 2017/7/9.
 */
enum class Level {
    /**
     * No logs.
     */
    NONE,
    /**
     *
     * Example:
     * <pre>`- URL
     * - Method
     * - Headers
     * - Body
    `</pre> *
     */
    BASIC,
    /**
     *
     * Example:
     * <pre>`- URL
     * - Method
     * - Headers
    `</pre> *
     */
    HEADERS,
    /**
     *
     * Example:
     * <pre>`- URL
     * - Method
     * - Body
    `</pre> *
     */
    BODY
}