package com.google.code.objectprops.format;

public class IntegerPrimitiveFormat extends IntegerFormat {
    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return new Integer(0);
        } else {
            return super.parse(str);
        }
    }

}
