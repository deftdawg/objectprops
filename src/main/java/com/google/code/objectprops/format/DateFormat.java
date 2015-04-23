package com.google.code.objectprops.format;

import java.lang.reflect.UndeclaredThrowableException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.code.objectprops.IFormat;

public class DateFormat implements IFormat {
    private static SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public String format(Object obj) {
        Date d = (Date)obj;
        return FORMAT.format(d);
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return FORMAT.parse(str);
            } catch (ParseException ex) {
                throw new UndeclaredThrowableException(ex);
            }
        }
    }
}
