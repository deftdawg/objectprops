import java.awt.Rectangle;
import java.util.Properties;

import com.google.code.objectprops.ObjectPropertiesStore;

/**
 * 
 * @author Michael Karneim
 */
public class QuickStart {

    public static void main(String[] args) throws Exception {
        //// Part 1: writing an object into the store        
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
        
        //// Part 2: reading an object from the store
        // create a new rectangle using the state from the location "window.bounds" 
        Rectangle newRect = (Rectangle)store.readObject("window.bounds", Rectangle.class);
        
        // print the new rectangle to System.out
        System.out.println("newRect: "+newRect.toString());
        
    }
}
