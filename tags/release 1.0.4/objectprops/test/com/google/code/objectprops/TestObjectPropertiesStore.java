package com.google.code.objectprops;

import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

/*
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

    public void testReadObjectWithString()
        throws ObjectPropertiesStoreException {
        store.writeObject("Hello, World!");
        String result = (String)store.readObject(String.class);
        this.assertEquals("result", "Hello, World!", result);
    }

    public void testWriteObjectWithInteger()
        throws ObjectPropertiesStoreException {
        store.writeObject(new Integer(42));
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
        store.writeObject(new BigInteger("42"));

        String result = store.getDatabase().getProperty("");
        this.assertEquals("result", "42", result);
    }

    public void testWriteObjectWithDate()
        throws ObjectPropertiesStoreException, ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date aDate = dateformat.parse("2008-12-31");

        store.writeObject(aDate);
        Date result = (Date)store.readObject(Date.class);
        this.assertEquals("result", aDate, result);
    }

    public void testWriteObjectWithFile()
        throws ObjectPropertiesStoreException, ParseException {
        File aFile = new File("../somedir/somefile.txt");

        store.writeObject(aFile);

        File result = (File)store.readObject(File.class);
        this.assertEquals("result", aFile, result);
    }

    public void testWriteObjectWithURL()
        throws ObjectPropertiesStoreException, MalformedURLException {
        URL url = new URL("http://code.google.com/p/objectprops/");

        store.writeObject(url);

        URL result = (URL)store.readObject(URL.class);
        this.assertEquals("result", url, result);
    }

    public void testWriteObjectWithIntVector()
        throws ObjectPropertiesStoreException, MalformedURLException {
        IntVector vector = new IntVector();
        vector.elements = new int[] { 0, 1, 2, 3, 4 };
        store.writeObject(vector);        

        IntVector result = (IntVector)store.readObject(IntVector.class);
        this.assertEquals("result", vector, result);
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

    public void testTransientFieldsWillNotBeInitialized() throws ObjectPropertiesStoreException {
        TransObject objWrite = new TransObject(1,2);
        store.writeObject("obj", objWrite);
        
        TransObject objRead = (TransObject) store.readObject("obj",  TransObject.class);
        
        this.assertNotNull("objOut", objRead);
        this.assertEquals("objOut.num", objWrite.num, objRead.num);
        this.assertEquals("objOut.transNum", 0, objRead.transNum);
        this.assertEquals("objOut.transString", null, objRead.transString);
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
        Person orig = new Person();
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
        Person origA = new Person();
        origA.firstname = "Donald";
        origA.lastname = "Duck";
        store.writeObject(origA);

        Person origB = new Person();
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

        Person newP = new Person();
        newP.firstname = "Donald";
        newP.lastname = "Duck";
        store.writeObject("personnel.5", newP);

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

    public int hashCode() {
        return street.hashCode() * 37 + city.hashCode() * 41 + state.hashCode() * 43 + zipcode;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Address oA = (Address)other;
        return this.street.equals(oA.street) && this.city.equals(oA.city) && this.state.equals(oA.state) && this.zipcode == oA.zipcode;
    }
}

class IntVector {
    public int[] elements;

    public int hashCode() {
        if (elements == null || elements.length == 0) {
            return 0;
        } else {
            return elements[0];
        }
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        IntVector oI = (IntVector)other;
        if (this.elements == oI.elements) {
            return true;
        }
        if (this.elements == null || oI.elements == null) {
            return false;
        }
        if (this.elements.length != oI.elements.length) {
            return false;
        }
        for (int i = 0; i < this.elements.length; ++i) {
            if (this.elements[i] != oI.elements[i]) {
                return false;
            }
        }
        return true;
    }
}

class TransObject {
    public transient int transNum;
    public int num;
    public transient String transString = "hello";
    
    private TransObject() {
        num = 5;
        transNum = 10;
    }
    
    public TransObject(int num, int transNum) {
        super();
        this.num = num;
        this.transNum = transNum;
    }

    
    
}
