package com.google.code.objectprops;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Karneim
 */
class ObjectReader {
    private static final ReflectionUtil reflectionUtil = new ReflectionUtil();
    
    private final Path path = new Path();
    private final Properties properties;
    private final Formatter formatter;

    ObjectReader(Properties properties, Formatter formatter) {
        super();
        this.properties = properties;
        this.formatter = formatter;
    }

    ObjectReader(Properties properties, Formatter formatter, String path) {
        super();
        this.properties = properties;
        this.formatter = formatter;
        this.path.setPathString(path);
    }

    /**
    *
    *
    * @param type Class
    * @return Object
    * @throws ObjectPropertiesStoreException
    */
    public Object readObject(Class type)
        throws ObjectPropertiesStoreException {
        return this.loadValue(type);
    }

    private Properties getProperties() {
        return properties;
    }

    private Path getPath() {
        return path;
    }

    private Formatter getFormatter() {
        return formatter;
    }

    /**
     *
     *
     * @param type Class
     * @return Object
     * @throws ObjectPropertiesStoreException
     */
    private Object loadObjectFields(Class type)
        throws ObjectPropertiesStoreException {
        Field[] fields = reflectionUtil.getAllFields(type);
        String pathStr = this.getPath().getPathString();
        if ( new Path( pathStr).existsIn(this.properties)) {        
            Object result = this.createObject(type);
            boolean allFieldsAreNull = true;
            for (int i = 0; i < fields.length; i++) {
                if (shouldLoad(fields[i])) {
                    boolean wasNull = !loadFieldValue(result, fields[i]);
                    allFieldsAreNull = allFieldsAreNull && wasNull;
                }
            }
            if (!allFieldsAreNull) {
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 
     * @param field Field
     * @return boolean
     */
    private boolean shouldLoad(Field field) {
        return !Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers());
    }

    private Object createObject(Class type)
        throws ObjectPropertiesStoreException {
        try {
            Constructor javaLangObjectConstructor = Object.class.getDeclaredConstructor(new Class[0]);
            Constructor customConstructor = reflectionUtil.newConstructorForSerialization(type, javaLangObjectConstructor);
            Object obj = customConstructor.newInstance(new Object[0]);
            return obj;
        } catch (InvocationTargetException ex) {
            throw new ObjectPropertiesStoreException(ex);
        } catch (IllegalArgumentException ex) {
            throw new ObjectPropertiesStoreException(ex);
        } catch (IllegalAccessException ex) {
            throw new ObjectPropertiesStoreException(ex);
        } catch (InstantiationException ex) {
            throw new ObjectPropertiesStoreException(ex);
        } catch (SecurityException ex) {
            throw new ObjectPropertiesStoreException(ex);
        } catch (NoSuchMethodException ex) {
            throw new ObjectPropertiesStoreException(ex);
        }
    }

    private boolean loadFieldValue(Object owner, Field field)
        throws ObjectPropertiesStoreException {
        Class type = field.getType();
        this.getPath().addElement(field.getName());
        Object value = this.loadValue(type);
        this.getPath().removeLastElement();
        reflectionUtil.setFieldValue(field, owner, value);
        return (value != null);
    }

    private Object loadValue(Class type)
        throws ObjectPropertiesStoreException {
        if (type.isArray()) {
            return this.loadArrayValue(type.getComponentType());
        } else {
            if (this.getFormatter().hasFormat(type)) {
                return this.loadFormattableValue(type);
            } else if (type.isPrimitive() == false) {
                return this.loadComplexValue(type);
            } else {
                throw new ObjectPropertiesStoreException("Unexpected field type '" + type.getName() + "', path=" + this.getPath().getPathString());
            }
        }
    }

    private Object loadArrayValue(Class componentType)
        throws ObjectPropertiesStoreException {
        String path = this.getPath().getPathString();
        Set keys = this.getProperties().keySet();
        Iterator it = keys.iterator();
        HashSet indices = new HashSet();
        while (it.hasNext()) {
            String key = (String)it.next();
            if (key.equals(path) == false && key.startsWith(path + Path.ELEMENT_SEPARATOR_CHAR)) {
                String tail = key.substring(path.length() + 1);
                Integer number = findFirstNumber(tail);
                if (number != null) {
                    indices.add(number);
                }
            }
        }
        ArrayList indicesList = new ArrayList(indices);
        Collections.sort(indicesList);
        int maxIndex;
        if (indicesList.size() > 0) {
            Integer last = (Integer)indicesList.get(indicesList.size() - 1);
            maxIndex = last.intValue();
        } else {
            maxIndex = -1;
        }
        if (maxIndex > -1) {
            return loadArrayValue(componentType, maxIndex + 1);
        } else {
            return null;
        }
    }

    private Object loadArrayValue(Class componentType, int size)
        throws ObjectPropertiesStoreException {
        Object arrayObj = Array.newInstance(componentType, size);
        final int len = Array.getLength(arrayObj);
        for (int i = 0; i < len; i++) {
            this.getPath().addElement("" + i);
            Object value = loadValue(componentType);
            reflectionUtil.setArrayValue(arrayObj, i, value);
            this.getPath().removeLastElement();
        }
        return arrayObj;
    
    }

    private Object loadComplexValue(Class type)
        throws ObjectPropertiesStoreException {
        return this.loadObjectFields(type);
    }

    private Object loadFormattableValue(Class type)
        throws ObjectPropertiesStoreException {
        try {
            String path = this.getPath().getPathString();
            String str = this.getProperties().getProperty(path);
            Object value = this.getFormatter().parse(str, type);
            return value;
        } catch (UnknownFormatException ex) {
            throw new ObjectPropertiesStoreException(ex);
        } catch (FormatException ex) {
        	throw new ObjectPropertiesStoreException(ex);
        }
    }

    
    //// STATIC ///

    private static Integer findFirstNumber(String text) {
        int idx = text.indexOf(Path.ELEMENT_SEPARATOR_CHAR);
        if (idx != -1) {
            text = text.substring(0, idx);
        }
        char[] chrs = text.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            if (Character.isDigit(chrs[i]) == false) {
                return null;
            }
        }
        return new Integer(text);
    }

}
