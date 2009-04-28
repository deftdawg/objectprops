package com.google.code.objectprops.format;

public class FloatPrimitiveFormat extends FloatFormat {
    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return new Float(0f);
        } else {
            return super.parse(str);
        }
    }

}
