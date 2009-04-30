package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class DoubleFormat implements IFormat {
    public String format(Object obj) {
        Double d = (Double)obj;
        return d.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            return new Double(str);
        }
    }
}
