package cn.salesuite.saf.kotlin.handler;

import com.safframework.log.L;
import com.safframework.log.LogLevel;
import com.safframework.log.handler.BaseHandler;
import com.safframework.log.printer.Printer;

import cn.salesuite.saf.kotlin.domain.User;


/**
 * Created by tony on 2017/11/27.
 */

public class UserHandler extends BaseHandler {

    @Override
    protected boolean handle(Object obj, LogLevel logLevel, String tag) {

        if (obj instanceof User) {

            User u = (User)obj;

            for(Printer printer:L.printers()) {
                String s = L.getMethodNames(printer.getFormatter());
                printer.printLog(LogLevel.INFO,"UserHandler",String.format(s, u.userName+":"+u.password));
            }

            return true;
        }

        return false;
    }
}
