package com.safframework.log.printer

import com.safframework.log.LogLevel
import com.safframework.log.printer.file.FileBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException


/**
 *
 * @FileName:
 *          com.safframework.log.printer.FilePrinter
 * @author: Tony Shen
 * @date: 2019-08-31 10:58
 * @version: V1.0 <描述当前版本功能>
 */
class FilePrinter(fileBuilder: FileBuilder):Printer{

    private val channel = Channel<LogItem>()
    private var folderPath:String?=null

    init {
        GlobalScope.launch {
            channel.consumeEach {

                doWrite(it)
            }
        }

        folderPath = fileBuilder.folderPath
    }

    override fun println(logLevel: LogLevel, tag: String, msg: String) {

        val timeMillis = System.currentTimeMillis()

        GlobalScope.launch {
            channel.send(LogItem(timeMillis, logLevel, tag, msg))
        }
    }

    private fun doWrite(logItem:LogItem) {


    }

    private class LogItem internal constructor(internal var timeMillis: Long, internal var level: LogLevel, internal var tag: String, internal var msg: String)


    private class Writer(private val folderPath:String) {

        var lastFileName: String? = null
            private set

        var file: File? = null
            private set

        lateinit var bufferedWriter: BufferedWriter

        val isOpened: Boolean
            get() = bufferedWriter != null

        fun open(newFileName: String): Boolean {
            lastFileName = newFileName
            file = File(folderPath, newFileName)

            if (!file!!.exists()) {
                try {
                    val parent = file!!.getParentFile()
                    if (!parent.exists()) {
                        parent.mkdirs()
                    }
                    file!!.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    lastFileName = null
                    file = null
                    return false
                }

            }

            try {
                bufferedWriter = BufferedWriter(FileWriter(file, true))
            } catch (e: Exception) {
                e.printStackTrace()
                lastFileName = null
                file = null
                return false
            }

            return true
        }

        fun close(): Boolean {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return false
                } finally {
                    lastFileName = null
                    file = null
                }
            }
            return true
        }

        fun appendLog(log: String) {
            try {
                bufferedWriter.write(log)
                bufferedWriter.newLine()
                bufferedWriter.flush()
            } catch (e: IOException) {
            }

        }
    }

}