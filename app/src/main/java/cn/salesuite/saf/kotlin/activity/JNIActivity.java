package cn.salesuite.saf.kotlin.activity;

import android.app.Activity;
import android.os.Bundle;

import com.safframework.utils.ToastUtilsKt;

import cn.salesuite.saf.kotlin.utils.JNIUtils;

/**
 * @FileName: cn.salesuite.saf.kotlin.activity.JNIActivity
 * @author: Tony Shen
 * @date: 2020-07-22 21:36
 * @version: V1.0 <描述当前版本功能>
 */
public class JNIActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ToastUtilsKt.showLong(this, JNIUtils.stringFromJNI());
    }
}
