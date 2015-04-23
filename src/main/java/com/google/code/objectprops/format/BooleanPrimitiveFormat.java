package com.google.code.objectprops.format;

public class BooleanPrimitiveFormat extends BooleanFormat {
    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return Boolean.FALSE;
        } else {
            return super.parse(str);
        }
    }

}
