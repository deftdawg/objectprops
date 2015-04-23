<table width='100%' border='0'>
<tr><td width='700px'>bla</td><td width='100px' align='right'>lall</td></tr>
</table>

Blah...
<a href='Hidden comment: 
This text will be removed from the rendered page.
'></a>

Blah...

```java

public static void main(String[] args) throws IOException {

Rectangle rect = new Rectangle(100,10,400,300);

// writing an object into the store
ObjectPropertiesStore store = new ObjectPropertiesStore();
store.writeObject("window.bounds", rect);

store.getDatabase().store( System.out, "My application preferences");

// reading an object
Rectangle retrieved = (Rectangle)store.readObject("window.bounds", Rectangle.class);

System.out.println("retrieved: "+retrieved.toString());

}
```