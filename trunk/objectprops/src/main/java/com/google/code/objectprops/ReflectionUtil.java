package com.google.code.objectprops;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.LinkedList;

import sun.misc.Unsafe;
import sun.reflect.ReflectionFactory;

/**
 * 
 * @author Michael Karneim
 */
class ReflectionUtil {
    private final ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
    private Unsafe cachedUnsafe = null;

    public ReflectionUtil() {

    }
    
    /**
     * Returns all (even private) fields of the given class.
     * The private fields will be made accessible by calling  setAccessible(true).
     * @param cls Class
     * @return Field[]
     */
    public Field[] getAllFields(Class cls) {
        LinkedList list = new LinkedList();
        Class c = cls;
        while (c != null) {
            Field[] fields = c.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                list.add(fields[i]);
            }
            c = c.getSuperclass();
        }
        return (Field[])list.toArray(new Field[list.size()]);
    }

    /**
     * Converts of type-casts the given object to an object array if possible.
     * @param obj Object
     * @return Object[]
     * @throws IllegalArgumentException if the given object is not an array
     */
    public Object[] toObjectArray(Object obj)
        throws IllegalArgumentException {
        if (obj == null) {
            return null;
        }
        Class arrayClass = obj.getClass();
        if (arrayClass.isArray() == false) {
            throw new IllegalArgumentException("obj is no array");
        }
        Class compType = arrayClass.getComponentType();
        Object[] result;
        if (compType.isPrimitive()) {
            int len = Array.getLength(obj);
            result = new Object[len];
            for (int i = 0; i < len; i++) {
                result[i] = Array.get(obj, i);
            }
        } else {
            result = (Object[])obj;
        }
        return result;
    }
    
    
    public Constructor newConstructorForSerialization(Class type, Constructor constr) {
        return reflectionFactory.newConstructorForSerialization(type, constr);
    }

    public void setArrayValue(Object arrayObj, int index, Object value) 
    throws ObjectPropertiesStoreException {
    	if ( arrayObj.getClass().isArray()==false) {
    		throw new IllegalArgumentException("arrayObj is no array");
    	}
    	Class type = arrayObj.getClass().getComponentType();
    	
    	if ( value != null ) {
	    	if (type.isPrimitive()) {
	            if (type.equals(Integer.TYPE)) {
	            	Array.setInt(arrayObj, index, ((Integer)value).intValue());
	            } else if (type.equals(Long.TYPE)) {
	            	Array.setLong(arrayObj, index, ((Long)value).longValue());
	            } else if (type.equals(Short.TYPE)) {
	            	Array.setShort(arrayObj, index, ((Short)value).shortValue());
	            } else if (type.equals(Character.TYPE)) {
	            	Array.setChar(arrayObj, index, ((Character)value).charValue());
	            } else if (type.equals(Byte.TYPE)) {
	            	Array.setByte(arrayObj, index, ((Byte)value).byteValue());
	            } else if (type.equals(Float.TYPE)) {
	            	Array.setFloat(arrayObj, index, ((Float)value).floatValue());
	            } else if (type.equals(Double.TYPE)) {
	            	Array.setDouble(arrayObj, index, ((Double)value).doubleValue());
	            } else if (type.equals(Boolean.TYPE)) {
	            	Array.setBoolean(arrayObj, index, ((Boolean)value).booleanValue());
	            } else {
	                throw new RuntimeException("Could not set array elements with unknown component type "+  type);
	            }
	        } else {
	        	if ( type.isAssignableFrom( value.getClass())==false) {
	        		throw new IllegalArgumentException("value must be instance of array's component type "+type.getName()+" but was "+value.getClass().getName());
	        	}
	        	Array.set(arrayObj, index, value);
	        }
    	}
    }
    public void setFieldValue(Field field, Object object, Object value)
        throws ObjectPropertiesStoreException {
        try {
            Unsafe unsafe = getUnsafe();
            long offset = unsafe.objectFieldOffset(field);
            Class type = field.getType();
            if (type.isPrimitive()) {
                if (type.equals(Integer.TYPE)) {
                    unsafe.putInt(object, offset, ((Integer)value).intValue());
                } else if (type.equals(Long.TYPE)) {
                    unsafe.putLong(object, offset, ((Long)value).longValue());
                } else if (type.equals(Short.TYPE)) {
                    unsafe.putShort(object, offset, ((Short)value).shortValue());
                } else if (type.equals(Character.TYPE)) {
                    unsafe.putChar(object, offset, ((Character)value).charValue());
                } else if (type.equals(Byte.TYPE)) {
                    unsafe.putByte(object, offset, ((Byte)value).byteValue());
                } else if (type.equals(Float.TYPE)) {
                    unsafe.putFloat(object, offset, ((Float)value).floatValue());
                } else if (type.equals(Double.TYPE)) {
                    unsafe.putDouble(object, offset, ((Double)value).doubleValue());
                } else if (type.equals(Boolean.TYPE)) {
                    unsafe.putBoolean(object, offset, ((Boolean)value).booleanValue());
                } else {
                    throw new RuntimeException("Could not set field " + object.getClass() + "." + field.getName() + ": Unknown type " + type);
                }
            } else {
                unsafe.putObject(object, offset, value);
            }

        } catch (IllegalAccessException e) {
            throw new ObjectPropertiesStoreException("Could not set field " + object.getClass() + "." + field.getName(), e);
        } catch (NoSuchFieldException e) {
            throw new ObjectPropertiesStoreException("Could not set field " + object.getClass() + "." + field.getName(), e);
        } catch (ClassNotFoundException e) {
            throw new ObjectPropertiesStoreException("Could not set field " + object.getClass() + "." + field.getName(), e);
        }
    }

    private Unsafe getUnsafe()
        throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        if (cachedUnsafe != null) {
            return cachedUnsafe;
        }
        Class objectStreamClass = Class.forName("java.io.ObjectStreamClass$FieldReflector");
        Field unsafeField = objectStreamClass.getDeclaredField("unsafe");
        unsafeField.setAccessible(true);
        cachedUnsafe = (Unsafe)unsafeField.get(null);
        return cachedUnsafe;
    }

}
