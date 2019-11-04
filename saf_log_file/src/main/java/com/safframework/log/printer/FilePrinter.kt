package com.safframework.log.printer

import com.alibaba.fastjson.JSON
import com.safframework.log.LogLevel
import com.safframework.log.formatter.Formatter
import com.safframework.log.printer.file.name.DateFileNameGenerator
import com.safframework.log.printer.file.FileBuilder
import com.safframework.log.printer.file.name.FileNameGenerator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import com.safframework.log.printer.file.clean.CleanStrategy
import com.safframework.log.printer.file.clean.NeverCleanStrategy


/**
 *
 * @FileName:
 *          com.safframework.log.printer.FilePrinter
 * @author: Tony Shen
 * @date: 2019-08-31 10:58
 * @since: V2.0 打印到文件的Printer，默认的 formatter 使用 SimpleFormatter
 */
class FilePrinter(fileBuilder: FileBuilder,override val formatter: Formatter):Printer{

    private val channel = Channel<LogItem>()
    private val folderPath:String
    private val fileNameGenerator: FileNameGenerator
    private val cleanStrategy: CleanStrategy
    private val writer: Writer

    init {
        GlobalScope.launch {
            channel.consumeEach {

                doWrite(it)
            }
        }

        folderPath        = fileBuilder.folderPath?: "/sdcard/logs/"
        fileNameGenerator = fileBuilder.fileNameGenerator?: DateFileNameGenerator()
        cleanStrategy     = fileBuilder.cleanStrategy?: NeverCleanStrategy()

        writer            = Writer(folderPath)
    }

    override fun printLog(logLevel: LogLevel, tag: String, msg: String) {

        GlobalScope.launch {
            channel.send(LogItem(System.currentTimeMillis(), logLevel, tag, msg))
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

                cleanLogFilesIfNecessary()

                if (!writer.open(newFileName)) {
                    return
                }
            }
        }

        writer.appendLog(JSON.toJSONString(logItem))
    }

    private fun cleanLogFilesIfNecessary() {

        val logDir = File(folderPath)
        val files = logDir.listFiles() ?: return

        files.map {

            if (cleanStrategy.shouldClean(it)) {
                it.delete()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FilePrinter

        if (formatter != other.formatter) return false
        if (channel != other.channel) return false
        if (folderPath != other.folderPath) return false
        if (fileNameGenerator != other.fileNameGenerator) return false
        if (cleanStrategy != other.cleanStrategy) return false
        if (writer != other.writer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = formatter.hashCode()
        result = 31 * result + channel.hashCode()
        result = 31 * result + folderPath.hashCode()
        result = 31 * result + fileNameGenerator.hashCode()
        result = 31 * result + cleanStrategy.hashCode()
        result = 31 * result + writer.hashCode()
        return result
    }

    /**
     * 每次文件写入的内容
     */
    private class LogItem internal constructor(internal var timeMillis: Long, internal var level: LogLevel, internal var tag: String, internal var msg: String)


    /**
     * 文件写入
     */
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

            file?.let {

                if (!it.exists()) {

                    try {
                        val parent = it.parentFile
                        if (!parent.exists()) {
                            parent.mkdirs()
                        }
                        it.createNewFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        lastFileName = null
                        file = null
                        return false
                    }
                }

                try {
                    bufferedWriter = BufferedWriter(FileWriter(it, true))
                } catch (e: Exception) {
                    e.printStackTrace()
                    lastFileName = null
                    file = null
                    return false
                }

                return true
            }

            return false
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

            bufferedWriter?.let {

                try {
                    it.write(log)
                    it.newLine()
                    it.flush()
                } catch (e: IOException) {
                }
            }
        }
    }
}