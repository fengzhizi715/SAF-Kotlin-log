package cn.salesuite.saf.kotlin;

import android.app.Activity;
import android.os.Bundle;

import com.safframework.log.L;

/**
 * Created by Tony Shen on 2017/1/7.
 */

public class MainActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.init(this.getClass());
//        L.setLogLevel(L.LogLevel.INFO);
        L.i("test","haha");
        L.i("111321frehtyjuyikuloil'0[xwrgrtehcytbk8ynfggrgr4hytj");
        User u = new User();
        u.userName = "tony";
        u.password = "123456";
        L.json(u);
    }
}
