package cn.salesuite.saf.kotlin;

import android.app.Activity;
import android.os.Bundle;

import com.safframework.log.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony Shen on 2017/1/7.
 */

public class MainActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.init(this.getClass()).header("app version 1.1");
//        L.setLogLevel(L.LogLevel.INFO);
        L.i("test","haha");
        L.w("test","haha");
        L.d("test","haha");
        L.e("test","haha");
        L.i("111321frehtyjuyikuloil'0[xwrgrtehcytbk8ynfggrgr4hytj");
        User u = new User();
        u.userName = "tony";
        u.password = "123456";
//        L.json(u);
//        L.json("dsafds");

        List<User> list = new ArrayList<>();
        list.add(u);
        list.add(u);
        L.json(list);
    }
}
