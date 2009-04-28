package com.google.code.objectprops;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
/**
 * 
 * @author Michael Karneim
 */
class Path {
    public static final char ELEMENT_SEPARATOR_CHAR = '.';
    private String pathString = "";
    private int length = 0;

    Path() {        
    }
    
    Path( String str) {
        setPathString(str);
    }
    
    public void setPathString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("str==null");
        }
        this.pathString = str;
        if ("".equals(this.pathString)) {
            this.length = 0;
        } else {
            char[] chrs = this.pathString.toCharArray();
            this.length = 1;
            for (int i = 0; i < chrs.length; i++) {
                if (chrs[i] == ELEMENT_SEPARATOR_CHAR) {
                    this.length++;
                }
            }
        }
    }

    public String getPathString() {
        return this.pathString;
    }

    public void addElement(String element) {
        if (pathString.equals("")) {
            this.setPathString(element);
        } else {
            this.setPathString(this.getPathString() + ELEMENT_SEPARATOR_CHAR + element);
        }
    }

    public void removeLastElement() {
        if (this.pathString.equals("")) {
            throw new NoSuchElementException("path contains no elements");
        } else {
            int idx = this.pathString.lastIndexOf(ELEMENT_SEPARATOR_CHAR);
            if (idx == -1) {
                this.setPathString("");
            } else {
                String parentPath = this.pathString.substring(0, idx);
                this.setPathString(parentPath);
            }
        }
    }

    public int getLength() {
        return this.length;
    }
    
    public boolean existsIn(Properties props) {
        if ("".equals(pathString)) {
            return props.size() > 0;
        } else {
            Set keys = props.keySet();
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                String key = (String)it.next();
                if (key.equals(pathString) || key.startsWith(pathString + Path.ELEMENT_SEPARATOR_CHAR)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public Collection/*String*/ findIn(Properties props) {
        Set keys = props.keySet();
        Iterator it = keys.iterator();
        LinkedList result = new LinkedList();
        while (it.hasNext()) {
            String key = (String)it.next();
            if (key.equals(pathString) || key.startsWith(pathString + ".")) {
                result.add(key);
            }
        }
        return result;
    }


}
