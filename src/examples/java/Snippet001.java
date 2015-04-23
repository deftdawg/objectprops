import java.awt.Rectangle;
import java.io.IOException;

import com.google.code.objectprops.ObjectPropertiesStore;

/*
 * This snippet demonstrates 
 * - how to write an object into the ObjectPropertiesStore
 * - how to read an object from the ObjectPropertiesStore
 */
public class Snippet001 {

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
    
    
}
