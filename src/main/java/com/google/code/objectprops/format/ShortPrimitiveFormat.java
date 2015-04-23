package com.google.code.objectprops.format;

public class ShortPrimitiveFormat extends ShortFormat {
    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return new Short((short)0);
        } else {
            return super.parse(str);
        }
    }

}
