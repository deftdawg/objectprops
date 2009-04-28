package com.google.code.objectprops;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 * The ObjectPropertiesStore can serialize (and deserialize) the state 
 * of any Java object into a {@link Properties} object 
 * by serializing all (non-static and non-transient) fields.
 * 
 * @author Michael Karneim
 */
public class ObjectPropertiesStore {
          
    private final Properties database;
    private final Formatter formatter;
    
    /**
     * Creates a new ObjectPropertiesStore.
     */
    public ObjectPropertiesStore() {
        this( new OrderedProperties());        
    }
    /**
     * Creates a new ObjectPropertiesStore with the given Properties object as database. 
     * @param properties
     */
    public ObjectPropertiesStore(Properties properties) {
        this.database = properties;
        this.formatter = this.createDefaultFormatter();
    }
    
    /**
     * Create a new ObjectPropertiesStore with the given Properties object as database
     * and the given Formatter.
     * 
     * @param properties
     * @param formatter
     */
    public ObjectPropertiesStore(Properties properties, Formatter formatter) {
        this.database = properties;
        this.formatter = formatter;
    }
    
    /**
     * Returns the formatter. 
     * @return the formatter
     */
    public Formatter getFormatter() {
        return this.formatter;
    }

    /**
     * Returns the database.
     * @return the database
     */
    public Properties getDatabase() {
        return this.database;
    }
    
    /**
     * Clears the database.
     */
    public void clear() {
        this.database.clear();
    }
    
    /**
     * Clears all entries at the given location from the database.
     * @param location
     * @throws ObjectPropertiesStoreException
     */
    public void clear(String location)
        throws ObjectPropertiesStoreException {
        Collection subPaths = new Path( location).findIn(this.database);
        Iterator it = subPaths.iterator();
        while (it.hasNext()) {
            this.database.remove(it.next());
        }
    }
    
    /**
     * Creates and returns a new Java object of the given type 
     * populated with the values found at the given location inside the database.
     * 
     * @param location
     * @param type
     * @return a new Java object of he given type
     * @throws ObjectPropertiesStoreException
     */
    public Object readObject(String location, Class type)
        throws ObjectPropertiesStoreException {
        return new ObjectReader(this.database,  this.formatter, location).readObject(type);
    }
    
    /**
     * Creates and returns a new Java object of the given type 
     * populated with the values from the database.
     * 
     * @param type
     * @return a new Java object of the given type
     * @throws ObjectPropertiesStoreException
     */
    public Object readObject(Class type)
        throws ObjectPropertiesStoreException {
        return new ObjectReader(this.database, this.formatter).readObject(type);
    }

    /**
     * Writes the state of the given Java object into the specified location inside the database. 
     * @param location
     * @param obj
     * @throws ObjectPropertiesStoreException
     */
    public void writeObject(String location, Object obj)
        throws ObjectPropertiesStoreException {
        new ObjectWriter(this.database, this.formatter, location).writeObject(obj);
    }
    
    /**
     * Writes the state of the given Java object into the database.
     * @param obj
     * @throws ObjectPropertiesStoreException
     */
    public void writeObject(Object obj)
        throws ObjectPropertiesStoreException {
        new ObjectWriter(this.database, this.formatter).writeObject(obj);
    }    
    
    /**
     * Returns true, if the database contains any object at the specified location.
     * @param location
     * @return true, if the database contains any object at the specified location
     */
    public boolean containsObjectAt(String location) {
        return new Path( location).existsIn( this.database);        
    }
    
    /**
     * Creates the default formatter.
     * @return the default formatter
     */
    protected Formatter createDefaultFormatter() {
        return new DefaultFormatter();
    }

}
