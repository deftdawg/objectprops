package com.google.code.objectprops;
/**
 * The ObjectPropertiesStoreException.
 * 
 * @author Michael Karneim
 */
public class ObjectPropertiesStoreException extends RuntimeException {
    public ObjectPropertiesStoreException(String message) {
        super(message);
    }

    public ObjectPropertiesStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectPropertiesStoreException(Throwable cause) {
        super(cause);
    }

}
