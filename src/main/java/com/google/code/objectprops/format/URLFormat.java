package com.google.code.objectprops.format;


import java.net.MalformedURLException;
import java.net.URL;

import com.google.code.objectprops.FormatException;
import com.google.code.objectprops.IFormat;

public class URLFormat implements IFormat {
    public String format(Object obj) {
        URL url = (URL)obj;
        return url.toExternalForm();
    }

    public Object parse(String str) throws FormatException {
        if (str == null) {
            return null;
        } else {
            try {
				return new URL(str);
			} catch (MalformedURLException e) {
				throw new FormatException(e);
			}
        }
    }
}
