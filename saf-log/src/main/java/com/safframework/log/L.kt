package com.safframework.log

import android.os.Bundle
import android.util.Log
import com.alibaba.fastjson.JSON
import com.safframework.log.parse.*
import com.safframework.log.utils.Utils
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.Reference

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

            if (msg!=null && msg.isNotEmpty()) {

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

            if (tag!=null && tag.isNotEmpty() && msg!=null && msg.isNotEmpty()) {

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

            if (msg!=null && msg.isNotEmpty()) {
                Log.e(TAG, msg, tr)
            }
        }
    }

    @JvmStatic
    fun w(msg: String?) {
        if (LogLevel.WARN.value <= logLevel.value) {

            if (msg!=null && msg.isNotEmpty()) {

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

            if (tag!=null && tag.isNotEmpty() && msg!=null && msg.isNotEmpty()) {

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

            if (msg!=null && msg.isNotEmpty()) {
                Log.w(TAG, msg, tr)
            }
        }
    }

    @JvmStatic
    fun i(msg: String?) {
        if (LogLevel.INFO.value <= logLevel.value) {

           if (msg!=null && msg.isNotEmpty()) {

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

            if (tag!=null && tag.isNotEmpty() && msg!=null && msg.isNotEmpty()) {

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

            if (msg!=null && msg.isNotEmpty()) {
                Log.i(TAG, msg, tr)
            }
        }
    }

    @JvmStatic
    fun d(msg: String?) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            if (msg!=null && msg.isNotEmpty()) {

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

            if (tag!=null && tag.isNotEmpty() && msg!=null && msg.isNotEmpty()) {

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

            if (msg!=null && msg.isNotEmpty()) {
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

            is Collection<*> -> collection2JSONString(obj)

            is Bundle -> bundle2JSONString(obj)

            is Reference<*> -> reference2JSON(obj)

            is Throwable -> throwable2JSONString(obj)

            else -> {

                try {
                    val s = getMethodNames()

                    var msg = obj.javaClass.toString() + LoggerPrinter.BR + "║ "
                    val objStr = JSON.toJSONString(obj)
                    val jsonObject = JSONObject(objStr)
                    var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                    message = message.replace("\n".toRegex(), "\n║ ")

                    println(String.format(s, msg+ message))
                } catch (e: JSONException) {
                    e("Invalid Json")
                }
            }
        }
    }

    /**
     * 打印json字符串
     */
    private fun string2JSONString(json: String) {
        var json = json

        json = json.trim { it <= ' ' }
        val s = getMethodNames()
        val parser = StringParser()
        println(String.format(s, parser.parseString(json)))
    }

    /**
     * 将map打印成json字符串
     */
    private fun map2JSONString(map: Map<*, *>) {
        val s = getMethodNames()
        val parser = MapParser()
        println(String.format(s, parser.parseString(map)))
    }

    /**
     * 将list、set打印成json字符串
     */
    private fun collection2JSONString(collection: Collection<*>) {
        val value = collection.firstOrNull()
        val isPrimitiveType = Utils.isPrimitiveType(value)

        if (isPrimitiveType) {
            val simpleName = collection.javaClass
            var msg = "%s size = %d" + LoggerPrinter.BR
            msg = String.format(msg, simpleName, collection.size) + "║ "
            val s = getMethodNames()
            println(String.format(s, msg + collection.toString()))
            return
        }

        val s = getMethodNames()
        val parser = CollectionParser()
        println(String.format(s, parser.parseString(collection)))
    }

    /**
     * 将bundle打印成json字符串
     */
    private fun bundle2JSONString(bundle: Bundle) {
        val s = getMethodNames()
        val parser = BundleParser()
        println(String.format(s, parser.parseString(bundle)))
    }

    /**
     * 将reference打印成json字符串
     */
    private fun reference2JSON(reference: Reference<*>) {
        val s = getMethodNames()
        val parser = ReferenceParser()
        println(String.format(s, parser.parseString(reference)))
    }

    /**
     * 将throwable打印成json字符串
     */
    private fun throwable2JSONString(throwable: Throwable) {
        val s = getMethodNames()
        val parser = ThrowableParser()
        System.err.println(String.format(s, parser.parseString(throwable)))
    }

    private fun getMethodNames(): String {
        val sElements = Thread.currentThread().stackTrace

        var stackOffset = LoggerPrinter.getStackOffset(sElements)

        stackOffset++
        val builder = StringBuilder()

        builder.append(LoggerPrinter.TOP_BORDER).append(LoggerPrinter.BR)
        if (header!=null && header!!.isNotEmpty()) {
            // 添加Header
            builder.append("║ " + "Header: " + header).append(LoggerPrinter.BR)
                    .append(LoggerPrinter.MIDDLE_BORDER).append(LoggerPrinter.BR)
        }
        // 添加当前线程名
        builder.append("║ " + "Thread: " + Thread.currentThread().name).append(LoggerPrinter.BR)
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

        return builder.toString()
    }
}