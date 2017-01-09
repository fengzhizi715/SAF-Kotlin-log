package cn.salesuite.saf.log

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

    var logLevel = LogLevel.DEBUG // 日志的等级，可以进行配置，最好在Application中进行全局的配置

    @JvmStatic fun init(clazz: Class<*>) {
        TAG = clazz.simpleName
    }

    /**
     * 支持用户自己传tag，可扩展性更好
     * @param tag
     */
    @JvmStatic fun init(tag: String) {
        TAG = tag
    }

    @JvmStatic fun e(msg: String?) {
        if (LogLevel.ERROR.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()
                Log.e(TAG, String.format(s, msg))
            }
        }
    }

    @JvmStatic fun e(msg: String?, tr: Throwable) {
        if (LogLevel.ERROR.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {
                Log.e(TAG, msg, tr)
            }
        }
    }

    @JvmStatic fun w(msg: String?) {
        if (LogLevel.WARN.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()
                Log.e(TAG, String.format(s, msg))
            }
        }
    }

    @JvmStatic fun w(msg: String?, tr: Throwable) {
        if (LogLevel.WARN.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {
                Log.w(TAG, msg, tr)
            }
        }
    }

    @JvmStatic fun i(msg: String?) {
        if (LogLevel.INFO.value <= logLevel.value) {

           if (msg!=null && msg.isNotBlank()) {
               val s = getMethodNames()
               Log.i(TAG, String.format(s,msg))
           }

        }
    }

    /**
     * @param tag 自定义tag
     * *
     * @param msg
     */
    @JvmStatic fun i(tag: String?, msg: String?) {
        if (LogLevel.INFO.value <= logLevel.value) {

            if (tag!=null && tag.isNotBlank() && msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()
                Log.i(tag, String.format(s, msg))
            }
        }
    }

    @JvmStatic fun i(msg: String?, tr: Throwable) {
        if (LogLevel.INFO.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {
                Log.i(TAG, msg, tr)
            }
        }
    }

    @JvmStatic fun d(msg: String?) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()
                Log.d(TAG, String.format(s, msg))
            }
        }
    }

    /**
     * @param tag 自定义tag
     * *
     * @param msg
     */
    fun d(tag: String?, msg: String?) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            if (tag!=null && tag.isNotBlank() && msg!=null && msg.isNotBlank()) {

                val s = getMethodNames()
                Log.d(tag, String.format(s, msg))
            }
        }
    }

    fun d(msg: String?, tr: Throwable) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            if (msg!=null && msg.isNotBlank()) {
                Log.d(TAG, msg, tr)
            }
        }
    }

    @JvmStatic fun json(map: Map<*, *>?) {
        if (map != null) {

            try {
                val jsonObject = JSONObject(map)
                var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
                val s = getMethodNames()
                println(String.format(s, message))
            } catch (e: JSONException) {
                e("Invalid Json")
            }

        }
    }

    @JvmStatic fun json(obj: Any?) {

        if (obj == null) {
            d("object is null")
            return
        }

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

    @JvmStatic fun json(json: String) {
        var json = json

        if (json.isBlank()) {
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
            e("Invalid Json")
        } catch (e: JSONException) {
            e("Invalid Json")
        }

    }

    fun getMethodNames(): String {
        val sElements = Thread.currentThread().stackTrace

        var stackOffset = LoggerPrinter.getStackOffset(sElements)

        stackOffset++
        val builder = StringBuilder()
        builder.append(LoggerPrinter.TOP_BORDER).append("\r\n")
                // 添加当前线程名
                .append("║ " + "Thread: " + Thread.currentThread().name).append("\r\n")
                .append(LoggerPrinter.MIDDLE_BORDER).append("\r\n")
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
                .append("\r\n")
                .append(LoggerPrinter.MIDDLE_BORDER).append("\r\n")
                // 添加打印的日志信息
                .append("║ ").append("%s").append("\r\n")
                .append(LoggerPrinter.BOTTOM_BORDER).append("\r\n")
        return builder.toString()
    }

    fun String.isBlank(msg:String):Boolean {

        return msg.length==0;
    }

    fun String.isNotBlank(msg:String):Boolean {

        return !msg.isBlank();
    }
}