import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.code.objectprops.ObjectPropertiesStore;


public class Snippet003 {
    
    public static void main(String[] args) throws IOException {
        
        Color[] colors = new Color[5];
        colors[0] = Color.BLACK;
        colors[1] = Color.BLUE;
        colors[2] = Color.WHITE;
        colors[3] = Color.GREEN;
        colors[4] = Color.RED;
        
        // write colors into store
        ObjectPropertiesStore store = new ObjectPropertiesStore();
        store.writeObject("colors", colors);
        
        // print contents to System.out
        store.getDatabase().store( System.out, "My application settings");
        
        // save properties into file
        File file = File.createTempFile("myapp", ".properties");        
        store.getDatabase().store( new FileOutputStream(file), "My application settings");
        
        
        
        
        
        // create a new empty store
        ObjectPropertiesStore anotherStore = new ObjectPropertiesStore();
        
        // load properties from file
        anotherStore.getDatabase().load( new FileInputStream(file));
        
        // read the colors
        Color[] retrieved = (Color[])anotherStore.readObject("colors", Color[].class);
        for( int i=0; i<retrieved.length; ++i) {
            System.out.println(retrieved[i]);
        }
        
        file.deleteOnExit();        
        
    }
}
