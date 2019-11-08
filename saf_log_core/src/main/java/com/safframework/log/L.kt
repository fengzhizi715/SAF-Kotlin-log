package com.safframework.log

import android.util.Log
import com.safframework.log.formatter.BorderFormatter
import com.safframework.log.formatter.Formatter
import com.safframework.log.handler.*
import com.safframework.log.printer.ConsolePrinter
import com.safframework.log.printer.Printer
import java.util.*

/**
 * Created by Tony Shen on 2017/1/2.
 */
object L {

    private var TAG = "SAF_L"
    private var header: String? = ""
    private val handlers = LinkedList<BaseHandler>()
    private var firstHandler: BaseHandler
    private val printers = mutableSetOf<Printer>()
    private var displayThreadInfo:Boolean  = true
    private var displayClassInfo:Boolean   = true

    init {
        printers.add(ConsolePrinter()) // 默认添加 ConsolePrinter

        handlers.apply {

            add(StringHandler())
            add(CollectionHandler())
            add(MapHandler())
            add(BundleHandler())
            add(IntentHandler())
            add(UriHandler())
            add(ThrowableHandler())
            add(ReferenceHandler())
            add(ObjectHandler())
        }

        val len = handlers.size

        for (i in 0 until len) {
            if (i > 0) {
                handlers[i - 1].setNextHandler(handlers[i])
            }
        }

        firstHandler = handlers[0]
    }

    @JvmStatic
    var logLevel = LogLevel.DEBUG // 日志的等级，可以进行配置，最好在 Application 中进行全局的配置

    /******************* L 的配置方法 Start *******************/

    @JvmStatic
    fun init(clazz: Class<*>): L {
        TAG = clazz.simpleName
        return this
    }

    /**
     * 支持用户自己传 tag，可扩展性更好
     * @param tag
     */
    @JvmStatic
    fun init(tag: String): L {
        TAG = tag
        return this
    }

    /**
     * header 是 L 自定义的内容，可以放 App 的信息版本号等，方便查找和调试
     * @param tag
     */
    @JvmStatic
    fun header(header: String?): L {
        this.header = header
        return this
    }

    /**
     * 自定义 Handler 来解析 Object
     */
    @JvmStatic
    fun addCustomerHandler(handler: BaseHandler): L {

        val size = handlers.size

        return addCustomerHandler(handler, size - 1) // 插入在ObjectHandler之前
    }

    /**
     * 自定义 Handler 来解析 Object，并指定 Handler 的位置
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
     * 添加自定义的 Printer
     */
    @JvmStatic
    fun addPrinter(printer: Printer): L {

        this.printers.add(printer)
        return this
    }

    /**
     * 删除 Printer
     */
    @JvmStatic
    fun removePrinter(printer: Printer): L {

        this.printers.remove(printer)
        return this
    }

    /**
     * 是否打印线程信息
     */
    @JvmStatic
    fun displayThreadInfo(displayThreadInfo:Boolean): L {

        this.displayThreadInfo = displayThreadInfo
        return this
    }

    /**
     * 是否打印类的信息
     */
    @JvmStatic
    fun displayClassInfo(displayClassInfo:Boolean): L {

        this.displayClassInfo = displayClassInfo
        return this
    }

    /******************* L 的配置方法 End *******************/

    /******************* L 提供打印的方法 Start *******************/

    @JvmStatic
    fun e(msg: String?) = e(TAG,msg)

    @JvmStatic
    fun e(tag: String?, msg: String?) = printLog(LogLevel.ERROR,tag,msg)

    @JvmStatic
    fun e(msg: String?, tr: Throwable) = e(TAG,msg,tr)

    @JvmStatic
    fun e(tag: String?, msg: String?, tr: Throwable) = printThrowable(LogLevel.ERROR,tag,msg,tr)

    @JvmStatic
    fun w(msg: String?) = w(TAG,msg)

    @JvmStatic
    fun w(tag: String?, msg: String?) = printLog(LogLevel.WARN,tag,msg)

    @JvmStatic
    fun w(msg: String?, tr: Throwable) = w(TAG,msg,tr)

    @JvmStatic
    fun w(tag: String?, msg: String?, tr: Throwable) = printThrowable(LogLevel.WARN,tag,msg,tr)

    @JvmStatic
    fun i(msg: String?) = i(TAG,msg)

    @JvmStatic
    fun i(tag: String?, msg: String?) = printLog(LogLevel.INFO,tag,msg)

    @JvmStatic
    fun i(msg: String?, tr: Throwable) = i(TAG,msg,tr)

    @JvmStatic
    fun i(tag: String?, msg: String?, tr: Throwable) = printThrowable(LogLevel.INFO,tag,msg,tr)

    @JvmStatic
    fun d(msg: String?) = d(TAG,msg)

    @JvmStatic
    fun d(tag: String?, msg: String?) = printLog(LogLevel.DEBUG,tag,msg)

    @JvmStatic
    fun d(msg: String?, tr: Throwable) = d(TAG,msg,tr)

    @JvmStatic
    fun d(tag: String?, msg: String?, tr: Throwable) = printThrowable(LogLevel.DEBUG,tag,msg,tr)

    /**
     * 使用特定的 printer 进行打印日志
     */
    @JvmStatic
    fun print(logLevel: LogLevel, tag: String?, msg: String?,vararg printers: Printer) {

        if (logLevel.value <= L.logLevel.value) {
            if (tag != null && tag.isNotEmpty() && msg != null && msg.isNotEmpty()) {

                if (msg.contains("\n")) {
                    printers.map {
                        val s = getMethodNames(it.formatter)
                        it.printLog(logLevel, tag, String.format(s, msg.replace("\n", "\n${it.formatter.spliter()}")))
                    }
                } else {
                    printers.map {
                        val s = getMethodNames(it.formatter)
                        it.printLog(logLevel, tag, String.format(s, msg))
                    }
                }
            }
        }
    }

    private fun printLog(logLevel: LogLevel, tag: String?, msg: String?) {

        if (logLevel.value <= L.logLevel.value) {

            if (tag != null && tag.isNotEmpty() && msg != null && msg.isNotEmpty()) {

                if (msg.contains("\n")) {
                    printers.map {
                        val s = getMethodNames(it.formatter)
                        it.printLog(logLevel, tag, String.format(s, msg.replace("\n", "\n${it.formatter.spliter()}")))
                    }
                } else {
                    printers.map {
                        val s = getMethodNames(it.formatter)
                        it.printLog(logLevel, tag, String.format(s, msg))
                    }
                }
            }
        }
    }

    private fun printThrowable(logLevel: LogLevel, tag: String?, msg: String?, tr: Throwable) {

        if (logLevel.value <= L.logLevel.value) {

            if (tag != null && tag.isNotEmpty() && msg != null && msg.isNotEmpty()) {

                when(logLevel) {

                    LogLevel.ERROR -> Log.e(tag, msg, tr)

                    LogLevel.WARN  -> Log.w(tag, msg, tr)

                    LogLevel.INFO  -> Log.i(tag, msg, tr)

                    LogLevel.DEBUG -> Log.d(tag, msg, tr)
                }
            }
        }
    }

    /**
     * 将任何对象转换成json字符串进行打印
     */
    @JvmStatic
    fun json(obj: Any?) {

        if (obj == null) {
            e("object is null")
            return
        }

        firstHandler.handleObject(obj)
    }

    /******************* L 提供打印的方法 End *******************/

    @JvmOverloads
    @JvmStatic
    fun getMethodNames(formatter: Formatter = BorderFormatter): String {

        val sElements = Thread.currentThread().stackTrace

        var stackOffset = LoggerPrinter.getStackOffset(sElements)

        stackOffset++

        return StringBuilder().apply {

            append("  ").append(formatter.top())
        }.apply {

            header?.let {

                if (it.isNotEmpty()) {
                    // 添加 Header
                   append("Header: $header").append(formatter.middle())
                }
            }
        }.apply {

            if (displayThreadInfo) {
                // 添加当前线程名
                append("Thread: ${Thread.currentThread().name}")
                        .append(formatter.middle())
                        .append(formatter.spliter())
            }

            if (displayClassInfo) {

                // 添加类名、方法名、行数
                append(sElements[stackOffset].className)
                        .append(".")
                        .append(sElements[stackOffset].methodName)
                        .append(" ")
                        .append("(")
                        .append(sElements[stackOffset].fileName)
                        .append(":")
                        .append(sElements[stackOffset].lineNumber)
                        .append(")")
                        .append(formatter.middle())
                        // 添加打印的日志信息
                        .append(formatter.spliter())
            }

            append("%s").append(formatter.bottom())

        }.toString()
    }

    @JvmStatic
    fun printers() = printers
}