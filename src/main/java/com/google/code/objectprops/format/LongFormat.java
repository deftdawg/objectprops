package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class LongFormat implements IFormat {
    public String format(Object obj) {
        Long l = (Long)obj;
        return l.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            return new Long(str);
        }
    }

}
