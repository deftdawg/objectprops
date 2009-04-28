package com.google.code.objectprops;

import java.math.BigInteger;

import com.google.code.objectprops.ObjectPropertiesStore;
import com.google.code.objectprops.ObjectPropertiesStoreException;

import junit.framework.TestCase;

/**
 * 
 * @author Michael Karneim
 */
public class TestObjectPropertiesStore extends TestCase {
    private ObjectPropertiesStore store = null;

    protected void setUp()
        throws Exception {
        super.setUp();
        store = new ObjectPropertiesStore();
    }

    protected void tearDown()
        throws Exception {
        store = null;
        super.tearDown();
    }

    public void testWriteObjectWithString()
        throws ObjectPropertiesStoreException {
        store.writeObject("Hello, World!");
        String text = store.getDatabase().getProperty("");
        this.assertEquals("text", "Hello, World!", text);
    }

    public void testReadObjectAsString()
       throws ObjectPropertiesStoreException {
       store.writeObject("Hello, World!");
       String result = (String)store.readObject(String.class);
       this.assertEquals("result", "Hello, World!", result);
   }

    public void testWriteObjectWithInteger()
        throws ObjectPropertiesStoreException {
        store.writeObject( new Integer(42));
        String textvalue = store.getDatabase().getProperty("");
        this.assertEquals("textvalue", "42", textvalue);
    }
 
    public void testReadObjectWithInteger()
        throws ObjectPropertiesStoreException {
        store.writeObject(new Integer(42));
        Integer result = (Integer)store.readObject(Integer.class);
        this.assertEquals("result", new Integer(42), result);
    }

    public void testWriteObjectWithBigInteger()
        throws ObjectPropertiesStoreException {
        store.writeObject( new BigInteger("42"));
        store.getDatabase().list(System.out);
        String result = store.getDatabase().getProperty("");
        this.assertEquals("result", "42", result);
    }

    
    public void testWriteObjectWithPath()
        throws ObjectPropertiesStoreException {
        store.writeObject("my.path", "Hello, World!");
        String result = (String)store.readObject("my.path", String.class);
        this.assertEquals("result", "Hello, World!", result);
    }

    public void testWriteObjectWithPathMultipleTimes()
        throws ObjectPropertiesStoreException {
        store.writeObject("my.path", "Hello, World!");
        store.writeObject("my.other.path", new Integer(42));

        String resultA = (String)store.readObject("my.path", String.class);
        this.assertEquals("resultA", "Hello, World!", resultA);
        Integer resultB = (Integer)store.readObject("my.other.path", Integer.class);
        this.assertEquals("resultB", new Integer(42), resultB);
    }

    public void testWriteObjectWithComplexType()
        throws ObjectPropertiesStoreException {
        Person p = new Person();
        p.firstname = "Michael";
        p.lastname = "Karneim";
        store.writeObject(p);
        this.assertEquals("store.getProperties().getProperty(\"firstname\")", p.firstname, store.getDatabase().getProperty("firstname"));
        this.assertEquals("store.getProperties().getProperty(\"lastname\")", p.lastname, store.getDatabase().getProperty("lastname"));
    }


    public void testReadObjectWithComplexType()
        throws ObjectPropertiesStoreException {
        Person orig = new Person();
        orig.firstname = "Johann Sebastian";
        orig.lastname = "Bach";
        store.writeObject(orig);

        Person result = (Person)store.readObject(Person.class);
        this.assertNotNull("result", result);
        this.assertEquals("result.lastname", orig.lastname, result.lastname);
        this.assertEquals("result.firstname", orig.firstname, result.firstname);
    }

    public void testWriteObjectWithPathAndComplexType()
        throws ObjectPropertiesStoreException {
        Person orig = new Person();
        orig.firstname = "Donald";
        orig.lastname = "Duck";
        store.writeObject("this.is.my.path", orig);

        Person result = (Person)store.readObject("this.is.my.path", Person.class);
        this.assertNotNull("result", result);
        this.assertEquals("result.lastname", orig.lastname, result.lastname);
        this.assertEquals("result.firstname", orig.firstname, result.firstname);
    }

    public void testComplex()
        throws ObjectPropertiesStoreException {
        Company b = new Company();
        b.address = new Address("1 Infinite Loop", "Cupertino", "California", 95014);
        b.personnel = new Person[10];
        for (int i = 0; i < b.personnel.length; i++) {
            b.personnel[i] = new Person();
            b.personnel[i].firstname = "firstname" + i;
            b.personnel[i].lastname = "lastname" + i;
        }
        store.writeObject(b);

        Company bLoad = (Company)store.readObject(Company.class);
        this.assertNotNull("bLoad", bLoad);
        this.assertEquals("bLoad.name", b.name, bLoad.name);
        this.assertEquals("bLoad.personnel.length", b.personnel.length, bLoad.personnel.length);
        for (int i = 0; i < b.personnel.length; i++) {
            this.assertEquals("bLoad.personnel[i].firstname", b.personnel[i].firstname, bLoad.personnel[i].firstname);
            this.assertEquals("bLoad.personnel[i].lastname", b.personnel[i].lastname, bLoad.personnel[i].lastname);
        }
        this.assertEquals("bLoad.address", b.address, bLoad.address);
    }

    public void testReplaceValue()
        throws ObjectPropertiesStoreException {
        Person orig =  new Person();
        orig.firstname = "Donald";
        orig.lastname = "Duck";
                
        store.writeObject(orig);        
        store.writeObject("lastname", "Mouse");
        
        Person result = (Person)store.readObject(Person.class);
        this.assertEquals("result.firstname", "Donald", result.firstname);
        this.assertEquals("result.lastname", "Mouse", result.lastname);
    }
    
    public void testMerge()
        throws ObjectPropertiesStoreException {
        Person origA =  new Person();
        origA.firstname = "Donald";
        origA.lastname = "Duck";                
        store.writeObject(origA);
        
        Person origB =  new Person();
        origB.firstname = "Mickey";
        origB.lastname = null;             
        store.writeObject(origB);        
              
        Person result = (Person)store.readObject(Person.class);
        this.assertEquals("result.firstname", "Mickey", result.firstname);
        this.assertEquals("result.lastname", "Duck", result.lastname);
}
    
    public void testMerge2()
        throws ObjectPropertiesStoreException {
        Company b = new Company();
        b.name = "Funny Corp.";
        b.personnel = new Person[10];
        for (int i = 0; i < b.personnel.length; i++) {
            b.personnel[i] = new Person();
            b.personnel[i].firstname = "firstname" + i;
            b.personnel[i].lastname = "lastname" + i;
        }
        store.writeObject(b);
//        store.getProperties().list(System.out);
//        System.out.println("path=" + store.getLocation().getPath());

        Person newP = new Person();
        newP.firstname = "Donald";
        newP.lastname = "Duck";
        store.writeObject("personnel.5", newP);
//        store.getProperties().list(System.out);
//        System.out.println("path=" + store.getLocation().getPath());

        Company bLoad = (Company)store.readObject(Company.class);
        this.assertNotNull("bLoad", bLoad);
        this.assertEquals("bLoad.name", b.name, bLoad.name);
        this.assertEquals("bLoad.personnel.length", b.personnel.length, bLoad.personnel.length);
        for (int i = 0; i < 5; i++) {
            this.assertEquals("bLoad.personnel[i].firstname", b.personnel[i].firstname, bLoad.personnel[i].firstname);
            this.assertEquals("bLoad.personnel[i].lastname", b.personnel[i].lastname, bLoad.personnel[i].lastname);
        }
        this.assertEquals("bLoad.personnel[5].firstname", newP.firstname, bLoad.personnel[5].firstname);
        this.assertEquals("bLoad.personnel[5].lastname", newP.lastname, bLoad.personnel[5].lastname);

        for (int i = 6; i < b.personnel.length; i++) {
            this.assertEquals("bLoad.personnel[i].firstname", b.personnel[i].firstname, bLoad.personnel[i].firstname);
            this.assertEquals("bLoad.personnel[i].lastname", b.personnel[i].lastname, bLoad.personnel[i].lastname);
        }
    }

}

class Person {
    String firstname;
    String lastname;
}

class Company {
    String name;
    Person[] personnel;
    Address address;
}

class Address {
    private final String street;
    private final String city;
    private final String state;
    private final int zipcode;    
    
    public Address(String street, String city, String state, int zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zip;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Address oA = (Address)other;
        return this.street.equals(oA.street)
            && this.city.equals(oA.city)
            && this.state.equals(oA.state)
            && this.zipcode == oA.zipcode;
    }
}
