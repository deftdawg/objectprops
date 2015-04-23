**ObjectProps** is a Java API for serializing the state of any Java object into a `java.util.Properties` object in a human-readable format.

## Features ##
With ObjectProps you can
  * write the state of a Java object into a `java.util.Properties` object
  * create a new Java object by reading that state
  * define custom `IFormat` objects for parsing and formatting special classes

## Requirements ##
  * Java 1.2 or later

## Restrictions ##
Please note that
  * currently the object tree is flattened when written into the `ObjectPropertiesStore`. Therefor you can't write object trees with cyclic references.
  * object fields are stored and loaded by using the _declared field type_. Therefor you _can't store_ any field values that are of a _subtype_ of the declared field type.

## News ##

<wiki:gadget url="http://google-code-feed-gadget.googlecode.com/svn/trunk/gadget.xml" up\_feeds="http://objectprops.blogspot.com/atom.xml" width="600" height="220" border="0" up\_showaddbutton="0"/>

# Quickstart #
## Writing an object into the store ##
```

        // let's store a java.awt.Rectangle into the store

        // create a rectangle
        Rectangle rect = new Rectangle(100,10,400,300);
        
        // create a new and empty store
        ObjectPropertiesStore store = new ObjectPropertiesStore();

        // write the rectangle's state to the location "window.bounds" inside the store         
        store.writeObject("window.bounds", rect);
        
        // let's get the Properties object and print the contents to System.out
        Properties properties = store.getDatabase();
        properties.store( System.out, "My application preferences");
```

The output will be like the following:
```
 #My application preferences
 #Tue Apr 28 07:13:59 CEST 2009 
 window.bounds.x=100
 window.bounds.y=10
 window.bounds.width=400
 window.bounds.height=300
```

## Reading an object from the store ##
```
        // create a new rectangle using the state from the location "window.bounds" 
        Rectangle newRect = (Rectangle)store.readObject("window.bounds", Rectangle.class);
        
        // print the new rectangle to System.out
        System.out.println("newRect: "+newRect.toString());
```

The output will be the following:
```
 newRect: java.awt.Rectangle[x=100,y=10,width=400,height=300]
```

## Other examples ##
Look at the [code snippets](Snippet001.md) for more examples.


---

<table border='0'><tr>
<td>
<wiki:gadget url="http://www.ohloh.net/p/339180/widgets/project_partner_badge.xml" height="80"  border="0" /><br>
</td>
</tr></table>