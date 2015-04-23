package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class CharacterFormat implements IFormat {
    public String format(Object obj) {
        Character c = (Character)obj;
        return obj.toString();
    }

    public Object parse(String str) {
        if (str == null || str.length() == 0) {
            return null;
        } else if (str.length() == 1) {
            return new Character(str.charAt(0));
        } else {
            throw new IllegalArgumentException("Can't parse string '" + str + "' into a character");
        }
    }
}
