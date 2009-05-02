package com.google.code.objectprops.format;

import java.math.BigDecimal;

import com.google.code.objectprops.IFormat;

public class BigDecimalFormat implements IFormat {
    public String format(Object obj) {
        BigDecimal bd = (BigDecimal)obj;
        return bd.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            return new BigDecimal(str);
        }
    }
}
