package cn.salesuite.saf.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

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

        text2.setOnClickListener {

            val intent = Intent(this@MainActivity,CustomerHandlerActivity::class.java)
            startActivity(intent)
        }

        text3.setOnClickListener {

            val intent = Intent(this@MainActivity,KotlinStyleActivity::class.java)
            startActivity(intent)
        }

        text4.setOnClickListener {

            val intent = Intent(this@MainActivity,FilePrinterActivity::class.java)
            startActivity(intent)
        }

    }
}