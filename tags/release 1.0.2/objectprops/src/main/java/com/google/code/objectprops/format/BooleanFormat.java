package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class BooleanFormat implements IFormat {
    public String format(Object obj) {
        Boolean b = (Boolean)obj;
        return b.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            Boolean result = Boolean.valueOf(str);
            return result;
        }
    }
}
