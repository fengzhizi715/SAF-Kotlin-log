package cn.salesuite.saf.kotlin.handler;

import com.safframework.log.L;
import com.safframework.log.LogLevel;
import com.safframework.log.bean.JSONConfig;
import com.safframework.log.handler.BaseHandler;
import com.safframework.log.printer.Printer;

import cn.salesuite.saf.kotlin.domain.User;


/**
 * Created by tony on 2017/11/27.
 */

public class UserHandler extends BaseHandler {

    @Override
    protected boolean handle(Object obj, JSONConfig jsonConfig) {

        if (obj instanceof User) {

            User u = (User)obj;

            for(Printer printer:L.printers()) {
                String s = L.getMethodNames(printer.getFormatter());
                printer.printLog(jsonConfig.getLogLevel(),jsonConfig.getTag(),String.format(s, u.userName+":"+u.password));
            }

            return true;
        }

        return false;
    }
}
