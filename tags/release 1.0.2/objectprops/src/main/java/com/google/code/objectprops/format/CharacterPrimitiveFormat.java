package com.google.code.objectprops.format;

public class CharacterPrimitiveFormat extends CharacterFormat {

    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return new Character(' ');
        } else {
            return super.parse(str);
        }
    }

}
