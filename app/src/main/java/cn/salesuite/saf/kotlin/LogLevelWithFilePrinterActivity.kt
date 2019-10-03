package cn.salesuite.saf.kotlin

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.safframework.log.L
import com.safframework.log.printer.FilePrinter
import com.safframework.log.printer.file.FileBuilder
import com.safframework.log.printer.file.name.LevelFileNameGenerator

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.LogLevelWithFilePrinterActivity
 * @author: Tony Shen
 * @date: 2019-10-03 09:59
 * @version: V1.0 <描述当前版本功能>
 */
class LogLevelWithFilePrinterActivity : Activity() {

    private lateinit var filePrinter: FilePrinter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        } else {
            filePrinter = FileBuilder().folderPath("/storage/emulated/0/logs").fileNameGenerator(LevelFileNameGenerator()).build()
            L.addPrinter(filePrinter);
        }

        L.d("111321frehtyjuyikuloil'0[xwrgrtehcytbk8ynfggrgr4hytj")

        val u = User()
        u.userName = "tony"
        u.password = "123456"

        val map = HashMap<String, User>()
        map["tony"] = u
        map["tt"] = u
        L.json(map)

        val map2 = HashMap<String, String>()
        map2["tony"] = "shen"
        map2["tt"] = "ziyu"
        L.json(map2)

        val map3 = HashMap<String, Boolean>()
        map3["tony"] = true
        map3["tt"] = false
        L.json(map3)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode==0) {

            filePrinter = FileBuilder().folderPath("/storage/emulated/0/logs").fileNameGenerator(LevelFileNameGenerator()).build()
            L.addPrinter(filePrinter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        L.removePrinter(filePrinter)
    }
}