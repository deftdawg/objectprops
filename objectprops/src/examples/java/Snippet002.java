import java.awt.Rectangle;
import java.io.IOException;

import com.google.code.objectprops.ObjectPropertiesStore;

/*
 * This snippet demonstrates 
 * - how to write an array into the ObjectPropertiesStore
 * - how to read an array from the ObjectPropertiesStore
 * - how to read a single array element from the ObjectPropertiesStore
 */
public class Snippet002 {
    
    public static void main(String[] args) throws IOException {
        
        Rectangle[] rects = new Rectangle[3];
        rects[0] = new Rectangle(0,0,10,20);
        rects[1] = new Rectangle(55,88,200,400);
        rects[2] = new Rectangle(0,0,100,20);
        
        
        // writing an array of objects
        ObjectPropertiesStore store = new ObjectPropertiesStore();
        store.writeObject("bounds",rects);
        
        store.getDatabase().store( System.out, "My application preferences");
        
        // reading the whole array
        Rectangle[] retrieved = (Rectangle[])store.readObject("bounds", Rectangle[].class);
        for( int i=0; i<retrieved.length; ++i) {
            System.out.println("retrieved["+i+"]: "+retrieved[i].toString());
        }
        
        // reading an element from a specific location
        Rectangle aRect = (Rectangle)store.readObject("bounds.1", Rectangle.class);
        
        System.out.println("aRect: "+aRect.toString());
        
    }
}
