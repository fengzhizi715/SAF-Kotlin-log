package cn.salesuite.saf.kotlin.utils;

/**
 * @FileName: cn.salesuite.saf.kotlin.utils.JNIUtils
 * @author: Tony Shen
 * @date: 2020-07-22 20:53
 * @version: V1.0 <描述当前版本功能>
 */
public class JNIUtils {

    static {
        System.loadLibrary("test");
    }

//    public static native String stringFromJNI();
}
