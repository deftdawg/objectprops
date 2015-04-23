package com.google.code.objectprops.format;

public class LongPrimitiveFormat extends LongFormat {
    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return new Long((long)0);
        } else {
            return super.parse(str);
        }
    }

}
