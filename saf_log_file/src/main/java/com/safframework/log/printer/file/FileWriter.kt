package com.safframework.log.printer.file

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.FileWriter
 * @author: Tony Shen
 * @date: 2019-11-04 17:11
 * @version: V2.0 文件写入
 */
class FileWriter(var folderPath:String) {

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