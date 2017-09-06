package cn.salesuite.saf.kotlin;

import android.app.Activity;
import android.os.Bundle;

import com.safframework.log.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        Map<String,User> map = new HashMap<>();
        map.put("tony",u);
        map.put("tt",u);
        L.json(map);

        Map<String,String> map2 = new HashMap<>();
        map2.put("tony","shen");
        map2.put("tt","ziyu");
        L.json(map2);

        Map<String,Boolean> map3 = new HashMap<>();
        map3.put("tony",true);
        map3.put("tt",false);
        L.json(map3);

        List<User> list = new ArrayList<>();
        list.add(u);
        list.add(u);
        L.json(list);

        List<String> ids = new ArrayList<>();
        ids.add("123");
        ids.add("456");
        L.json(ids);

        List<Double> idd = new ArrayList<>();
        idd.add(123D);
        idd.add(456D);
        L.json(idd);

        List<Boolean> idb = new ArrayList<>();
        idb.add(true);
        idb.add(false);
        L.json(idb);
    }
}
