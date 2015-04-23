package com.google.code.objectprops.format;

public class BytePrimitiveFormat extends ByteFormat {
    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return new Byte((byte)0);
        } else {
            return super.parse(str);
        }
    }

}
