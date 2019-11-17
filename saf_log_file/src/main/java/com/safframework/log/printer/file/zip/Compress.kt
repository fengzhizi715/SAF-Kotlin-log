package com.safframework.log.printer.file.zip

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipOutputStream

/**
 *
 * @FileName:
 *          com.safframework.log.printer.file.zip.Compress
 * @author: Tony Shen
 * @date: 2019-11-17 10:46
 * @version: V2.2 <描述当前版本功能>
 */
private const val BUFFER_SIZE = 4096

/**
 * 使用时，需要先判断 Manifest.permission.WRITE_EXTERNAL_STORAGE 权限
 * <code>
 *     ioScope().launch {
 *
 *         val result =  File("/sdcard/logs").listFiles().asList()
 *         zip(result,"/sdcard/test.zip")
 *      }
 * </code>
 *
 */
suspend fun zip(filesToSend: List<File>, outputPath: String){

    flow<Boolean> {

        try {
            ZipOutputStream(BufferedOutputStream(FileOutputStream(outputPath))).use { zos ->

                for (f in filesToSend) {
                    if (f.exists() && !f.name.contains(".zip")) {

                        //Write file to zip
                        writeToZip(f, zos, createZipEntry(f.name, f))
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.collect{

    }
}

private fun createZipEntry(path: String, f: File) = ZipEntry(path).apply {

    time = f.lastModified()
    isDirectory
    size = f.length()
}

private fun writeToZip(f: File, zos: ZipOutputStream, zipEntry: ZipEntry) {
    val data = ByteArray(BUFFER_SIZE)

    FileInputStream(f).use { fi ->
        BufferedInputStream(fi).use { origin ->

            try {
                zos.putNextEntry(zipEntry)
            } catch (e: ZipException) {

            }

            while (true) {
                val readBytes = origin.read(data)
                if (readBytes == -1) {
                    break
                }

                try {
                    zos.write(data, 0, readBytes)
                } catch (e: ZipException) {
                }
            }
        }
    }
}