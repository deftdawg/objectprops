package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class FloatFormat implements IFormat {
    public String format(Object obj) {
        Float f = (Float)obj;
        return f.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            return new Float(str);
        }
    }
}
