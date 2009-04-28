package com.google.code.objectprops;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * @author Michael Karneim
 */
class ObjectWriter {
    private static final ReflectionUtil reflectionUtil = new ReflectionUtil();
    
    private final Path path = new Path();
    private final Properties properties;
    private final Formatter formatter;

    ObjectWriter(Properties properties, Formatter formatter) {
        this.properties = properties;
        this.formatter = formatter;
    }

    ObjectWriter(Properties properties, Formatter formatter, String path) {
        this.properties = properties;
        this.formatter = formatter;
        this.path.setPathString(path);
    }

    public void writeObject(Object obj)
        throws ObjectPropertiesStoreException {
        this.saveValue(obj, obj.getClass());
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

    private boolean shouldSave(Field field) {
        return !Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers());
    }

    private void saveObjectFields(Object obj, Class type)
        throws ObjectPropertiesStoreException {

        Field[] fields = reflectionUtil.getAllFields(type);
        for (int i = 0; i < fields.length; i++) {
            if (shouldSave(fields[i])) {
                saveFieldValue(obj, fields[i]);
            }
        }
    }

    private void saveFieldValue(Object owner, Field field)
        throws ObjectPropertiesStoreException {
        try {
            String fieldname = field.getName();
            Object value = field.get(owner);
            if (value != null) {
                this.getPath().addElement(fieldname);
                Class type = field.getType();
                this.saveValue(value, type);
                this.getPath().removeLastElement();
            }
        } catch (IllegalAccessException ex) {
            throw new ObjectPropertiesStoreException(ex);
        } catch (IllegalArgumentException ex) {
            throw new ObjectPropertiesStoreException(ex);
        }
    }

    private void saveValue(Object value, Class type)
        throws ObjectPropertiesStoreException {
        if (type.isArray()) {
            this.saveArrayValue(reflectionUtil.toObjectArray(value), type.getComponentType());
        } else {
            this.saveSingleValue(value, type);
        }
    }

    private void saveArrayValue(Object[] objects, Class componentType)
        throws ObjectPropertiesStoreException {
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                Object value = objects[i];
                if (value != null) {
                    this.getPath().addElement("" + i);
                    this.saveValue(value, componentType);
                    this.getPath().removeLastElement();
                }
            }
        }
    }

    private void saveComplexValue(Object value, Class type)
        throws ObjectPropertiesStoreException {
        if (value != null) {
            this.saveObjectFields(value, type);
        }
    }

    private void saveSingleValue(Object value, Class type)
        throws ObjectPropertiesStoreException {
        if (value != null) {
            if (this.saveFormattableValue(value, type) == false) {
                if (type.isPrimitive() == false) {
                    this.saveComplexValue(value, type);
                } else {
                    throw new ObjectPropertiesStoreException("Unexpected fuield type '" + type.getName() + "', path=" + this.getPath().getPathString());
                }
            }
        }
    }

    private boolean saveFormattableValue(Object value, Class type)
        throws ObjectPropertiesStoreException {
        if (this.getFormatter().hasFormat(type)) {
            try {
                String path = this.getPath().getPathString();
                String str = this.getFormatter().format(value, type);
                this.getProperties().setProperty(path, str);
                return true;
            } catch (UnknownFormatException ex) {
                throw new ObjectPropertiesStoreException(ex);
            } catch (FormatException ex) {
            	throw new ObjectPropertiesStoreException(ex);
            }
        } else {
            return false;
        }
    }

}
