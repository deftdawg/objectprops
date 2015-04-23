package com.google.code.objectprops.format;

import java.math.BigInteger;

import com.google.code.objectprops.IFormat;

public class BigIntegerFormat implements IFormat {
    public String format(Object obj) {
        BigInteger bi = (BigInteger)obj;
        return bi.toString();
    }

    public Object parse(String str) {
        if (str == null) {
            return null;
        } else {
            return new BigInteger(str);
        }
    }
}
