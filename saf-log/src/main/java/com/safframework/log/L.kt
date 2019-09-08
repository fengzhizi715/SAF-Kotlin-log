package com.safframework.log

import android.util.Log
import com.safframework.log.handler.*
import com.safframework.log.printer.ConsolePrinter
import com.safframework.log.printer.Printer

/**
 * Created by Tony Shen on 2017/1/2.
 */
object L {

    private var TAG = "SAF_L"
    private var header: String? = ""
    private val handlers = ArrayList<BaseHandler>()
    private var firstHandler: BaseHandler

    @JvmStatic
    var printer: Printer

    init {
        printer = ConsolePrinter

        handlers.add(StringHandler())
        handlers.add(CollectionHandler())
        handlers.add(MapHandler())
        handlers.add(BundleHandler())
        handlers.add(IntentHandler())
        handlers.add(UriHandler())
        handlers.add(ThrowableHandler())
        handlers.add(ReferenceHandler())
        handlers.add(ObjectHandler())

        val len = handlers.size

        for (i in 0 until len) {
            if (i > 0) {
                handlers[i - 1].setNextHandler(handlers[i])
            }
        }

        firstHandler = handlers[0]
    }

    @JvmStatic
    var logLevel = LogLevel.DEBUG // 日志的等级，可以进行配置，最好在Application中进行全局的配置

    /******************* L 的配置方法 Start *******************/

    @JvmStatic
    fun init(clazz: Class<*>): L {
        TAG = clazz.simpleName
        return this
    }

    /**
     * 支持用户自己传tag，可扩展性更好
     * @param tag
     */
    @JvmStatic
    fun init(tag: String): L {
        TAG = tag
        return this
    }

    /**
     * header是自定义的内容，可以放App的信息版本号等，方便查找和调试
     * @param tag
     */
    @JvmStatic
    fun header(header: String?): L {
        this.header = header
        return this
    }

    /**
     * 自定义Handler来解析Object
     */
    @JvmStatic
    fun addCustomerHandler(handler: BaseHandler): L {

        val size = handlers.size

        return addCustomerHandler(handler, size - 1) // 插入在ObjectHandler之前
    }

    /**
     * 自定义Handler来解析Object，并指定Handler的位置
     */
    @JvmStatic
    fun addCustomerHandler(handler: BaseHandler, index: Int): L {

        handlers.add(index, handler)

        val len = handlers.size

        for (i in 0 until len) {
            if (i > 0) {
                handlers[i - 1].setNextHandler(handlers[i])
            }
        }

        return this
    }

    /**
     * 自定义的 Printer
     */
    @JvmStatic
    fun printer(printer: Printer): L {

        this.printer = printer
        return this
    }

    /******************* L 的配置方法 End *******************/

    /******************* L 打印的方法 Start *******************/

    @JvmStatic
    fun e(msg: String?) = e(TAG,msg)

    /**
     * @param tag 使用自定义tag
     *
     * @param msg
     */
    @JvmStatic
    fun e(tag: String?, msg: String?) {
        if (LogLevel.ERROR.value <= logLevel.value) {

            print(LogLevel.ERROR,tag,msg)
        }
    }

    @JvmStatic
    fun e(msg: String?, tr: Throwable) {
        if (LogLevel.ERROR.value <= logLevel.value) {

            throwable(LogLevel.ERROR,msg,tr)
        }
    }

    @JvmStatic
    fun w(msg: String?) = w(TAG,msg)

    /**
     * @param tag 使用自定义tag
     *
     * @param msg
     */
    @JvmStatic
    fun w(tag: String?, msg: String?) {
        if (LogLevel.WARN.value <= logLevel.value) {

            print(LogLevel.WARN,tag,msg)
        }
    }

    @JvmStatic
    fun w(msg: String?, tr: Throwable) {
        if (LogLevel.WARN.value <= logLevel.value) {

            throwable(LogLevel.WARN,msg,tr)
        }
    }

    @JvmStatic
    fun i(msg: String?) = i(TAG,msg)

    /**
     * @param tag 使用自定义tag
     *
     * @param msg
     */
    @JvmStatic
    fun i(tag: String?, msg: String?) {
        if (LogLevel.INFO.value <= logLevel.value) {

            print(LogLevel.INFO,tag,msg)
        }
    }

    @JvmStatic
    fun i(msg: String?, tr: Throwable) {
        if (LogLevel.INFO.value <= logLevel.value) {

            throwable(LogLevel.INFO,msg,tr)
        }
    }

    @JvmStatic
    fun d(msg: String?) = d(TAG,msg)

    /**
     * @param tag 使用自定义tag
     *
     * @param msg
     */
    @JvmStatic
    fun d(tag: String?, msg: String?) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            print(LogLevel.DEBUG,tag,msg)
        }
    }

    @JvmStatic
    fun d(msg: String?, tr: Throwable) {
        if (LogLevel.DEBUG.value <= logLevel.value) {

            throwable(LogLevel.DEBUG,msg,tr)
        }
    }

    private fun print(logLevel: LogLevel, tag: String?, msg: String?) {

        if (tag != null && tag.isNotEmpty() && msg != null && msg.isNotEmpty()) {

            val s = getMethodNames()

            if (msg.contains("\n")) {
                printer.println(logLevel, tag, String.format(s, msg.replace("\n".toRegex(), "\n║ ")))
            } else {
                printer.println(logLevel, tag, String.format(s, msg))
            }
        }
    }

    private fun throwable(logLevel: LogLevel, msg: String?, tr: Throwable) {

        if (msg != null && msg.isNotEmpty()) {

            when(logLevel) {

                LogLevel.ERROR -> Log.e(TAG, msg, tr)

                LogLevel.WARN  -> Log.w(TAG, msg, tr)

                LogLevel.INFO  -> Log.i(TAG, msg, tr)

                LogLevel.DEBUG -> Log.d(TAG, msg, tr)
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

        firstHandler.handleObject(obj)
    }

    /******************* L 打印的方法 End *******************/

    @JvmStatic
    fun getMethodNames(): String {

        val sElements = Thread.currentThread().stackTrace

        var stackOffset = LoggerPrinter.getStackOffset(sElements)

        stackOffset++

        return StringBuilder().apply {

            this.append("  ").append(LoggerPrinter.BR).append(LoggerPrinter.TOP_BORDER).append(LoggerPrinter.BR)
        }.apply {

            header?.let {

                if (it.isNotEmpty()) {
                    // 添加Header
                    this.append("║ Header: $header").append(LoggerPrinter.BR)
                            .append(LoggerPrinter.MIDDLE_BORDER).append(LoggerPrinter.BR)
                }
            }
        }.apply {

            // 添加当前线程名
            this.append("║ Thread: ${Thread.currentThread().name}").append(LoggerPrinter.BR)
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
        }.toString()
    }
}