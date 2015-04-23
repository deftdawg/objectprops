package com.google.code.objectprops;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


import junit.framework.TestCase;

public class TestObjectSerialization extends TestCase {
    
    public static class CustomObject implements Serializable {        
        String firstname;
        String lastname;
        transient int num = 10;
        transient String aString = "";
    }
    
    public void testObjectSerializationWillNotInitializeTransientFields() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        
        CustomObject person = new CustomObject();        
        person.firstname = "John";
        person.lastname = "Doe";
        person.num = 3;
        
        out.writeObject(person);
        out.close();
        
        ByteArrayInputStream byteIn = new ByteArrayInputStream( byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        CustomObject person2 = (CustomObject)in.readObject();
        
        assertEquals(0, person2.num);
        assertNull(person2.aString);
    }
}
