package cn.salesuite.saf.kotlin;

import com.safframework.log.L;
import com.safframework.log.formatter.Formatter;
import com.safframework.log.handler.BaseHandler;
import com.safframework.log.printer.Printer;


/**
 * Created by tony on 2017/11/27.
 */

public class UserHandler extends BaseHandler {

    private Printer printer;
    private Formatter formatter;

    public UserHandler(Printer printer,Formatter formatter) {
        super(printer,formatter);
        this.printer = printer;
        this.formatter = formatter;
    }

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
