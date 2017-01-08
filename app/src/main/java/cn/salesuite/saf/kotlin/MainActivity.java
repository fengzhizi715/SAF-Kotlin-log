package cn.salesuite.saf.kotlin;

import android.app.Activity;
import android.os.Bundle;

import cn.salesuite.saf.log.L;

/**
 * Created by Tony Shen on 2017/1/7.
 */

public class MainActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.init(this.getClass());
        L.i("test");
    }
}
