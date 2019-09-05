package cn.salesuite.saf.kotlin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.safframework.log.L;
import com.safframework.log.handler.BaseHandler;
import com.safframework.log.printer.FilePrinter;
import com.safframework.log.printer.file.DateFileNameGenerator;
import com.safframework.log.printer.file.FileBuilder;

import java.lang.ref.WeakReference;
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
//        L.init(this.getClass()).header("app version 1.1");
//        L.setLogLevel(L.LogLevel.INFO);
//        L.i("test","haha");
//        L.w("test","haha");
//        L.d("test","haha");
//        L.e("test","haha");

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            FilePrinter filePrinter = new FileBuilder("/storage/emulated/0").fileNameGenerator(new DateFileNameGenerator()).build();
            L.printer(filePrinter);
        }


        L.i("111321frehtyjuyikuloil'0[xwrgrtehcytbk8ynfggrgr4hytj");

        User u = new User();
        u.userName = "tony";
        u.password = "123456";
//        L.json(u);

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

//        List<User> list = new ArrayList<>();
//        list.add(u);
//        list.add(u);
//        L.json(list);
//
//        List<String> ids = new ArrayList<>();
//        ids.add("123");
//        ids.add("456");
//        L.json(ids);
//
//        List<Double> idd = new ArrayList<>();
//        idd.add(123D);
//        idd.add(456D);
//        L.json(idd);

        Uri uri = Uri.parse("http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4#harvic");
        L.json(uri);

//        L.addCustomerHandler(new UserHandler());
//        L.json(u);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode==0) {

            FilePrinter filePrinter = new FileBuilder("/storage/emulated/0").fileNameGenerator(new DateFileNameGenerator()).build();
            L.printer(filePrinter);
        }
    }
}
