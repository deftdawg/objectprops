package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class ByteFormat implements IFormat {
    public String format(Object obj) {
        Byte b = (Byte)obj;
        return b.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            return new Byte(str);
        }
    }
}
