package cn.salesuite.saf.kotlin.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.safframework.log.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.salesuite.saf.kotlin.domain.User;

/**
 * @FileName: cn.salesuite.saf.kotlin.activity.UseJavaActivity
 * @author: Tony Shen
 * @date: 2020-06-20 20:22
 * @version: V1.0 <描述当前版本功能>
 */
public class UseJavaActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        L.i("111321frehtyjuyikuloil'0[xwrgrtehcytbk8ynfggrgr4hytj");

        User u = new User();
        u.userName = "tony";
        u.password = "123456";
        L.json(u);

        Map<String, User> map = new HashMap<>();
        map.put("tony",u);
        map.put("tt",u);
        L.json(map);

        Map<String, String> map2 = new HashMap<>();
        map2.put("tony","shen");
        map2.put("tt","ziyu");
        L.json(map2);

        Map<String, Boolean> map3 = new HashMap<>();
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
        idd.add(Double.valueOf("123"));
        idd.add(Double.valueOf("456"));
        L.json(idd);

        Uri uri = Uri.parse ("http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4#harvic");
        L.json(uri);
    }
}
