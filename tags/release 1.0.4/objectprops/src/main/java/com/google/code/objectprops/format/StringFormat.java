package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class StringFormat implements IFormat {
    public String format(Object obj) {
        return (String)obj;
    }

    public Object parse(String str) {
        return str;
    }
}
