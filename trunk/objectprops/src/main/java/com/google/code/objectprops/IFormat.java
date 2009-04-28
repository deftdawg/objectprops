package com.google.code.objectprops;

/**
 * The IFormat declares the common interface of classes that can convert  
 * a Java object of a specific type to a String representation and vice versa.
 * 
 * @author Michael Karneim
 */
public interface IFormat {
    /**
     * Converts the given Java object into a String.
     * @param obj
     * @return a string representation of the given Java object
     */
    public String format(Object obj);
    
    /**
     * Converts the given String into a new Java object.
     * @param str
     * @return a new Java object 
     */
    public Object parse(String str);
}
