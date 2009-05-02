package com.google.code.objectprops.format;

public class DoublePrimitiveFormat extends DoubleFormat {
    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return new Double(0d);
        } else {
            return super.parse(str);
        }
    }

}
