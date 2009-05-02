package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class IntegerFormat implements IFormat {
    public String format(Object obj) {
        Integer i = (Integer)obj;
        return i.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            return new Integer(str);
        }
    }
}
