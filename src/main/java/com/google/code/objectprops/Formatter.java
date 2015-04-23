package com.google.code.objectprops;

import java.util.HashMap;
import java.util.Iterator;

/**
 * The Formatter can convert a Java object to a String representation and vice versa.
 * 
 * It can be extended to format a specific type by adding an appropriate instance of {@link IFormat}.
 * 
 * @author Michael Karneim
 */
public class Formatter {
    private final HashMap formatsForTypes = new HashMap();

    /**
     * Creates a new empty Formatter.
     */
    public Formatter() {        
    }
    
    /**
     * Sets the format to use for the given type.  
     * @param format
     * @param forType
     */
    public void setFormat(IFormat format, Class forType) {
        this.formatsForTypes.put(forType, format);
    }
    /**
     * Removes the format for the given type.
     * @param forType
     */
    public void removeFormat(Class forType) {
        this.formatsForTypes.remove(forType);
    }
    /**
     * Returns an iterator over all types that have an asigned format.
     * @return an iterator over all types that have an asigned format
     */
    public Iterator getAllTypes() {
        return this.formatsForTypes.keySet().iterator();
    }
    /**
     * Clears the formats for all types.
     */
    public void clearFormats() {
        this.formatsForTypes.clear();
    }
    /**
     * Formats the contents of the given object into a String using the format that is assigned to the given type.
     * @param obj
     * @param type
     * @return the String representation of the given object created by the format assigned to given type
     * @throws UnknownFormatException
     * @throws FormatException
     */
    public String format(Object obj, Class type)
        throws UnknownFormatException, FormatException {
        IFormat format = this.findFormat(type);
        if (format != null) {
            return format.format(obj);
        } else {
            throw new UnknownFormatException("Can't find format for type '" + type.getName() + "'");
        }
    }    
    /**
     * Parses the given String using the format that is assigned to the given type and returns 
     * the created object. 
     * @param str
     * @param type
     * @return the newly created object
     * @throws UnknownFormatException
     * @throws FormatException
     */
    public Object parse(String str, Class type)
        throws UnknownFormatException, FormatException {
        IFormat format = this.findFormat(type);
        if (format != null) {
            return format.parse(str);
        } else {
            throw new UnknownFormatException("Can't find format for type '" + type.getName() + "'");
        }
    }
    /**
     * Returns true if there has been assigned a format to the given type.
     * @param type
     * @return true if there has been assigned a format to the given type
     */
    public boolean hasFormat(Class type) {
        return this.findFormat(type) != null;
    }
    
    /**
     * Returns the format that has been assigned to the given type (or null).
     * @param type
     * @return the format that has been assigned to the given type (or null)
     */
    public IFormat findFormat(Class type) {
        IFormat format = (IFormat)this.formatsForTypes.get(type);
        return format;
    }

}
