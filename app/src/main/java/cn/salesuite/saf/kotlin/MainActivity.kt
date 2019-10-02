package cn.salesuite.saf.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.safframework.log.L
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.MainActivity
 * @author: Tony Shen
 * @date: 2019-10-03 01:02
 * @version: V1.0 <描述当前版本功能>
 */
class MainActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text1.setOnClickListener {

            val intent = Intent(this@MainActivity,BasicActivity::class.java)
            startActivity(intent)
        }

        //        int permissionCheck = ContextCompat.checkSelfPermission(this,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
        //            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        //        } else {
        //            FilePrinter filePrinter = new FileBuilder().folderPath("/storage/emulated/0/logs").fileNameGenerator(new DateWithLevelFileNameGenerator()).build();
        //            L.addPrinter(filePrinter);
        //        }


    }

    //    @Override
    //    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    //
    //        if (requestCode==0) {
    //
    //            FilePrinter filePrinter = new FileBuilder().folderPath("/storage/emulated/0/logs").fileNameGenerator(new DateWithLevelFileNameGenerator()).build();
    //            L.addPrinter(filePrinter);
    //        }
    //    }
}