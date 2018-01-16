package cn.salesuite.saf.kotlin;

import com.safframework.log.L;
import com.safframework.log.handler.BaseHandler;


/**
 * Created by tony on 2017/11/27.
 */

public class UserHandler extends BaseHandler {

    @Override
    protected boolean handle(Object obj) {

        if (obj instanceof User) {

            User u = (User)obj;

            String s = L.getMethodNames();
            System.out.println(String.format(s, u.userName+":"+u.password));
            return true;
        }

        return false;
    }
}
