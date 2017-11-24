package com.safframework.log

import android.util.Log
import com.alibaba.fastjson.JSON
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Tony Shen on 2017/1/2.
 */
object L {

    enum class LogLevel {
        ERROR {
            override val value: Int
                get() = 0
        },
        WARN {
            override val value: Int
                get() = 1
        },
        INFO {
            override val value: Int
                get() = 2
        },
        DEBUG {
            override val value: Int
                get() = 3
        };

        abstract val value: Int
    }

    private var TAG = "SAF_L"
    private var header:String? = ""

    @JvmStatic
    var logLevel = LogLevel.DEBUG // 日志的等级，可以进行配置，最好在Application中进行全局的配置

    @JvmStatic
    fun init(clazz: Class<*>):L {
        TAG = clazz.simpleName
        return this
    }

    /**
     * 支持用户自己传tag，可扩展性更好
     * @param tag
     */
    @JvmStatic
    fun init(tag: String):L {
        TAG = tag
        return this
    }

    /**
     * header是自定义的内容，可以放App的信息版本号等，方便查找和调试
     * @param tag
     */
    @JvmStatic
    fun header(header: String?):L {
        this.header = header
        return this
    }

    @JvmStatic
    fun e(msg: String?) {
        if (LogLevel.ERROR.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()

                if (msg.contains("\n")) {
                    Log.e(TAG, String.format(s,msg.replace("\n".toRegex(), "\n║ ")))
                } else {
                    Log.e(TAG, String.format(s, msg))
                }
            }
        }
    }

    /**
     * @param tag 使用自定义tag
     *
     * @param msg
     */
    @JvmStatic
    fun e(tag: String?, msg: String?) {
        if (LogLevel.ERROR.value <= logLevel.value) {

            if (tag!=null && tag.isNotBlank() && msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()

                if (msg.contains("\n")) {
                    Log.e(tag, String.format(s,msg.replace("\n".toRegex(), "\n║ ")))
                } else {
                    Log.e(tag, String.format(s, msg))
                }
            }
        }
    }

    @JvmStatic
    fun e(msg: String?, tr: Throwable) {
        if (LogLevel.ERROR.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {
                Log.e(TAG, msg, tr)
            }
        }
    }

    @JvmStatic
    fun w(msg: String?) {
        if (LogLevel.WARN.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()

                if (msg.contains("\n")) {
                    Log.w(TAG, String.format(s,msg.replace("\n".toRegex(), "\n║ ")))
                } else {
                    Log.w(TAG, String.format(s, msg))
                }
            }
        }
    }

    /**
     * @param tag 使用自定义tag
     *
     * @param msg
     */
    @JvmStatic
    fun w(tag: String?, msg: String?) {
        if (LogLevel.WARN.value <= logLevel.value) {

            if (tag!=null && tag.isNotBlank() && msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()

                if (msg.contains("\n")) {
                    Log.w(tag, String.format(s,msg.replace("\n".toRegex(), "\n║ ")))
                } else {
                    Log.w(tag, String.format(s, msg))
                }
            }
        }
    }

    @JvmStatic
    fun w(msg: String?, tr: Throwable) {
        if (LogLevel.WARN.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {
                Log.w(TAG, msg, tr)
            }
        }
    }

    @JvmStatic
    fun i(msg: String?) {
        if (LogLevel.INFO.value <= logLevel.value) {

           if (msg!=null && msg.isNotBlank()) {

               val s = getMethodNames()

               if (msg.contains("\n")) {
                   Log.i(TAG, String.format(s,msg.replace("\n".toRegex(), "\n║ ")))
               } else {
                   Log.i(TAG, String.format(s, msg))
               }
           }

        }
    }

    /**
     * @param tag 使用自定义tag
     *
     * @param msg
     */
    @JvmStatic
    fun i(tag: String?, msg: String?) {
        if (LogLevel.INFO.value <= logLevel.value) {

            if (tag!=null && tag.isNotBlank() && msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()

                if (msg.contains("\n")) {
                    Log.i(tag, String.format(s,msg.replace("\n".toRegex(), "\n║ ")))
                } else {
                    Log.i(tag, String.format(s, msg))
                }
            }
        }
    }

    @JvmStatic
    fun i(msg: String?, tr: Throwable) {
        if (LogLevel.INFO.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {
                Log.i(TAG, msg, tr)
            }
        }
    }

    @JvmStatic
    fun d(msg: String?) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()

                if (msg.contains("\n")) {
                    Log.d(TAG, String.format(s,msg.replace("\n".toRegex(), "\n║ ")))
                } else {
                    Log.d(TAG, String.format(s, msg))
                }
            }
        }
    }

    /**
     * @param tag 使用自定义tag
     *
     * @param msg
     */
    @JvmStatic
    fun d(tag: String?, msg: String?) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            if (tag!=null && tag.isNotBlank() && msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()

                if (msg.contains("\n")) {
                    Log.d(tag, String.format(s,msg.replace("\n".toRegex(), "\n║ ")))
                } else {
                    Log.d(tag, String.format(s, msg))
                }
            }
        }
    }

    @JvmStatic
    fun d(msg: String?, tr: Throwable) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {
                Log.d(TAG, msg, tr)
            }
        }
    }

    /**
     * 将任何对象转换成json字符串进行打印
     */
    @JvmStatic
    fun json(obj: Any?) {

        if (obj == null) {
            d("object is null")
            return
        }

        when(obj) {

            is String -> string2JSONString(obj)

            is Map<*, *> -> map2JSONString(obj)

            is List<*> -> list2JSONString(obj)

            is Set<*> -> set2JSONString(obj)

            else -> {

                try {
                    val objStr = JSON.toJSONString(obj)
                    val jsonObject = JSONObject(objStr)
                    var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                    message = message.replace("\n".toRegex(), "\n║ ")
                    val s = getMethodNames()
                    println(String.format(s, message))
                } catch (e: JSONException) {
                    e("Invalid Json")
                }
            }
        }
    }

    /**
     * 打印json字符串
     */
    private fun string2JSONString(json: String?) {
        var json = json

        if (json==null || json.isBlank()) {
            d("Empty/Null json content")
            return
        }

        try {
            json = json.trim { it <= ' ' }
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
                return
            }
            if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
                return
            }
            e("Invalid Json: "+ json)
        } catch (e: JSONException) {
            e("Invalid Json: "+ json)
        }
    }

    /**
     * 将map打印成json字符串
     */
    private fun map2JSONString(map: Map<*, *>?) {
        if (map != null) {

            val keys = map.keys
            val values = map.values
            val value = values.firstOrNull()
            val isPrimitiveType = isPrimitiveType(value)

            val jsonObject = JSONObject()
            keys.map {

                it ->

                try {

                    if (isPrimitiveType) {
                        jsonObject.put(it.toString(), map.get(it))
                    } else {
                        jsonObject.put(it.toString(), JSONObject(JSON.toJSONString(map.get(it))))
                    }
                } catch (e: JSONException) {
                    e("Invalid Json")
                }
            }

            var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
            message = message.replace("\n".toRegex(), "\n║ ")
            val s = getMethodNames()
            println(String.format(s, message))
        }
    }

    /**
     * 将list打印成json字符串
     */
    private fun list2JSONString(list: List<*>?) {
        if (list != null) {

            try {
                val jsonArray = JSONArray()
                val value = list.firstOrNull()
                val isPrimitiveType = isPrimitiveType(value)

                if (isPrimitiveType) {
                    val s = getMethodNames()
                    println(String.format(s, list.toString()))
                    return
                }

                list.map {

                    it ->

                    try {
                        val objStr = JSON.toJSONString(it)
                        val jsonObject = JSONObject(objStr)
                        jsonArray.put(jsonObject)
                    } catch (e: JSONException) {
                        e("Invalid Json")
                    }
                }

                var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
            } catch (e: JSONException) {
                e("Invalid Json")
            }
        }
    }

    /**
     * 将set打印成json字符串
     */
    private fun set2JSONString(set: Set<*>?) {
        if (set != null) {
            try {
                val jsonArray = JSONArray()
                val value = set.firstOrNull()
                val isPrimitiveType = isPrimitiveType(value)

                if (isPrimitiveType) {
                    val s = getMethodNames()
                    println(String.format(s, set.toString()))
                    return
                }

                set.map {

                    it ->

                    try {
                        val objStr = JSON.toJSONString(it)
                        val jsonObject = JSONObject(objStr)
                        jsonArray.put(jsonObject)
                    } catch (e: JSONException) {
                        e("Invalid Json")
                    }
                }

                var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
            } catch (e: JSONException) {
                e("Invalid Json")
            }
        }
    }

    fun getMethodNames(): String {
        val sElements = Thread.currentThread().stackTrace

        var stackOffset = LoggerPrinter.getStackOffset(sElements)

        stackOffset++
        val builder = StringBuilder()

        if (header!=null && header!!.isNotBlank()) {
            builder.append(LoggerPrinter.TOP_BORDER).append(LoggerPrinter.BR)
                    // 添加当前线程名
                    .append("║ " + "header: " + header).append(LoggerPrinter.BR)
                    .append(LoggerPrinter.MIDDLE_BORDER).append(LoggerPrinter.BR)
                    // 添加当前线程名
                    .append("║ " + "Thread: " + Thread.currentThread().name).append(LoggerPrinter.BR)
                    .append(LoggerPrinter.MIDDLE_BORDER).append(LoggerPrinter.BR)
                    // 添加类名、方法名、行数
                    .append("║ ")
                    .append(sElements[stackOffset].className)
                    .append(".")
                    .append(sElements[stackOffset].methodName)
                    .append(" ")
                    .append(" (")
                    .append(sElements[stackOffset].fileName)
                    .append(":")
                    .append(sElements[stackOffset].lineNumber)
                    .append(")")
                    .append(LoggerPrinter.BR)
                    .append(LoggerPrinter.MIDDLE_BORDER).append(LoggerPrinter.BR)
                    // 添加打印的日志信息
                    .append("║ ").append("%s").append(LoggerPrinter.BR)
                    .append(LoggerPrinter.BOTTOM_BORDER).append(LoggerPrinter.BR)
        } else {
            builder.append(LoggerPrinter.TOP_BORDER).append(LoggerPrinter.BR)
                    // 添加当前线程名
                    .append("║ " + "Thread: " + Thread.currentThread().name).append(LoggerPrinter.BR)
                    .append(LoggerPrinter.MIDDLE_BORDER).append(LoggerPrinter.BR)
                    // 添加类名、方法名、行数
                    .append("║ ")
                    .append(sElements[stackOffset].className)
                    .append(".")
                    .append(sElements[stackOffset].methodName)
                    .append(" ")
                    .append(" (")
                    .append(sElements[stackOffset].fileName)
                    .append(":")
                    .append(sElements[stackOffset].lineNumber)
                    .append(")")
                    .append(LoggerPrinter.BR)
                    .append(LoggerPrinter.MIDDLE_BORDER).append(LoggerPrinter.BR)
                    // 添加打印的日志信息
                    .append("║ ").append("%s").append(LoggerPrinter.BR)
                    .append(LoggerPrinter.BOTTOM_BORDER).append(LoggerPrinter.BR)
        }

        return builder.toString()
    }

    /**
     * 判断是否基本类型
     */
    private fun isPrimitiveType(value: Any?):Boolean = when(value){

        is Boolean -> true

        is String -> true

        is Int -> true

        is Float -> true

        is Double ->  true

        else -> false
    }

    fun String.isBlank(msg:String):Boolean {

        return msg.length==0;
    }

    fun String.isNotBlank(msg:String):Boolean {

        return !msg.isBlank();
    }
}