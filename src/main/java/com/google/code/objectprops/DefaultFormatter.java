package com.google.code.objectprops;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;

import com.google.code.objectprops.format.BigDecimalFormat;
import com.google.code.objectprops.format.BigIntegerFormat;
import com.google.code.objectprops.format.BooleanFormat;
import com.google.code.objectprops.format.BooleanPrimitiveFormat;
import com.google.code.objectprops.format.ByteFormat;
import com.google.code.objectprops.format.BytePrimitiveFormat;
import com.google.code.objectprops.format.CharacterFormat;
import com.google.code.objectprops.format.CharacterPrimitiveFormat;
import com.google.code.objectprops.format.DateFormat;
import com.google.code.objectprops.format.DoubleFormat;
import com.google.code.objectprops.format.DoublePrimitiveFormat;
import com.google.code.objectprops.format.FloatFormat;
import com.google.code.objectprops.format.FloatPrimitiveFormat;
import com.google.code.objectprops.format.IntegerFormat;
import com.google.code.objectprops.format.IntegerPrimitiveFormat;
import com.google.code.objectprops.format.LongFormat;
import com.google.code.objectprops.format.LongPrimitiveFormat;
import com.google.code.objectprops.format.ShortFormat;
import com.google.code.objectprops.format.ShortPrimitiveFormat;
import com.google.code.objectprops.format.StringFormat;
import com.google.code.objectprops.format.URLFormat;

/**
 * The DefaultFormatter is a formatter with preconfigured formats for generally used types.
 * 
 * @author Michael Karneim 
 */
public class DefaultFormatter extends Formatter {
    
    public DefaultFormatter() {
        this.setFormat(new StringFormat(), String.class);
        this.setFormat(new CharacterPrimitiveFormat(), char.class);
        this.setFormat(new BooleanPrimitiveFormat(), boolean.class);
        this.setFormat(new BytePrimitiveFormat(), byte.class);
        this.setFormat(new IntegerPrimitiveFormat(), int.class);
        this.setFormat(new FloatPrimitiveFormat(), float.class);
        this.setFormat(new DoublePrimitiveFormat(), double.class);
        this.setFormat(new LongPrimitiveFormat(), long.class);
        this.setFormat(new ShortPrimitiveFormat(), short.class);
        this.setFormat(new CharacterFormat(), Character.class);
        this.setFormat(new BooleanFormat(), Boolean.class);
        this.setFormat(new ByteFormat(), Byte.class);
        this.setFormat(new IntegerFormat(), Integer.class);
        this.setFormat(new FloatFormat(), Float.class);
        this.setFormat(new DoubleFormat(), Double.class);
        this.setFormat(new LongFormat(), Long.class);
        this.setFormat(new ShortFormat(), Short.class);
        this.setFormat(new BigDecimalFormat(), BigDecimal.class);
        this.setFormat(new BigIntegerFormat(), BigInteger.class);
        this.setFormat(new DateFormat(), Date.class);
        this.setFormat(new URLFormat(), URL.class);
    }
}
