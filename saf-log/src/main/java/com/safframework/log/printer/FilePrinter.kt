package com.safframework.log.printer

import com.alibaba.fastjson.JSON
import com.safframework.log.LogLevel
import com.safframework.log.formatter.Formatter
import com.safframework.log.printer.file.FileBuilder
import com.safframework.log.printer.file.FileNameGenerator
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
 * @version: V1.0 打印到文件的Printer
 */
class FilePrinter(fileBuilder: FileBuilder,override val formatter: Formatter):Printer{

    private val channel = Channel<LogItem>()
    private val folderPath:String
    private val fileNameGenerator: FileNameGenerator
    private val writer: Writer

    init {
        GlobalScope.launch {
            channel.consumeEach {

                doWrite(it)
            }
        }

        folderPath = fileBuilder.folderPath
        fileNameGenerator = fileBuilder.fileNameGenerator!!

        writer = Writer(folderPath)
    }

    override fun println(logLevel: LogLevel, tag: String, msg: String) {

        val timeMillis = System.currentTimeMillis()

        GlobalScope.launch {
            channel.send(LogItem(timeMillis, logLevel, tag, msg))
        }
    }

    private fun doWrite(logItem:LogItem) {

        var lastFileName = writer.lastFileName
        if (lastFileName == null || fileNameGenerator.isFileNameChangeable()) {
            val newFileName = fileNameGenerator.generateFileName(logItem.level.value, System.currentTimeMillis())
            if (newFileName == null || newFileName.trim { it <= ' ' }.length == 0) {
                throw IllegalArgumentException("File name should not be empty.")
            }
            if (newFileName != lastFileName) {
                if (writer.isOpened) {
                    writer.close()
                }

                if (!writer.open(newFileName)) {
                    return
                }
            }
        }

        writer.appendLog(JSON.toJSONString(logItem))
    }


    private class LogItem internal constructor(internal var timeMillis: Long, internal var level: LogLevel, internal var tag: String, internal var msg: String)


    private class Writer(var folderPath:String) {

        var lastFileName: String? = null
            private set

        var file: File? = null
            private set

        var bufferedWriter: BufferedWriter?=null

        val isOpened: Boolean
            get() = bufferedWriter != null

        fun open(newFileName: String): Boolean {
            lastFileName = newFileName
            file = File(folderPath, newFileName)

            if (!file!!.exists()) {
                try {
                    val parent = file!!.parentFile
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

            bufferedWriter?.let {

                try {
                    it.close()
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

            bufferedWriter?.run {
                try {
                    write(log)
                    newLine()
                    flush()
                } catch (e: IOException) {
                }
            }
        }
    }
}