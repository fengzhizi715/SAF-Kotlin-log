package cn.salesuite.saf.kotlin.activity

import android.content.Intent
import android.os.Bundle
import cn.salesuite.saf.kotlin.R
import cn.salesuite.saf.kotlin.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.activity.MainActivity
 * @author: Tony Shen
 * @date: 2019-10-03 01:02
 * @version: V1.0 <描述当前版本功能>
 */
class MainActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text1.setOnClickListener {

            val intent = Intent(this@MainActivity, BasicActivity::class.java)
            startActivity(intent)
        }

        text2.setOnClickListener {

            val intent = Intent(this@MainActivity, CustomerHandlerActivity::class.java)
            startActivity(intent)
        }

        text3.setOnClickListener {

            val intent = Intent(this@MainActivity, KotlinStyleActivity::class.java)
            startActivity(intent)
        }

        text4.setOnClickListener {

            val intent = Intent(this@MainActivity, FilePrinterActivity::class.java)
            startActivity(intent)
        }

        text5.setOnClickListener {

            val intent = Intent(this@MainActivity, LogLevelWithFilePrinterActivity::class.java)
            startActivity(intent)
        }

        text6.setOnClickListener {

            val intent = Intent(this@MainActivity, LoggingInterceptorActivity::class.java)
            startActivity(intent)
        }

        text7.setOnClickListener {

            val intent = Intent(this@MainActivity, UseJavaActivity::class.java)
            startActivity(intent)
        }

        text8.setOnClickListener {

            val intent = Intent(this@MainActivity, JNIActivity::class.java)
            startActivity(intent)
        }
    }
}