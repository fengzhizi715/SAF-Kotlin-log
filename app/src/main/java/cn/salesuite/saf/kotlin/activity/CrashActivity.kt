package cn.salesuite.saf.kotlin.activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.activity.CrashActivity
 * @author: Tony Shen
 * @date: 2021-10-14 21:08
 * @version: V1.0 <描述当前版本功能>
 */
class CrashActivity: Activity()  {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }

        throw NullPointerException()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode==0) {

        }
    }

}