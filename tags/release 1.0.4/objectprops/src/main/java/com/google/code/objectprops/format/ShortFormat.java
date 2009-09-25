package com.google.code.objectprops.format;

import com.google.code.objectprops.IFormat;

public class ShortFormat implements IFormat {
    public String format(Object obj) {
        Short s = (Short)obj;
        return s.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            return new Short(str);
        }
    }

}
