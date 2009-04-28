import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.code.objectprops.ObjectPropertiesStore;

/*
 * This snippet demonstrates 
 * - how to write objects into a properties file
 * - how to read objects from a properties file
 */
public class Snippet003 {
    
    public static void main(String[] args) throws IOException {
    	File file = File.createTempFile("myapp", ".properties");
    	file.deleteOnExit();
    	
    	save(file);
    	
    	load(file);
    }
    
    static void save(File file) throws IOException {
    	System.out.println("writing objects into a properties file");
    	
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
        store.getDatabase().store( new FileOutputStream(file), "My application settings");
        
    }
    
    static void load(File file) throws IOException {        
        System.out.println("reading objects from a properties file");
    	
        // create a new empty store
        ObjectPropertiesStore store = new ObjectPropertiesStore();
        
        // load properties from file
        store.getDatabase().load( new FileInputStream(file));
        
        // read the colors
        Color[] colors = (Color[])store.readObject("colors", Color[].class);
        // print the colors to System.out
        for( int i=0; i<colors.length; ++i) {
            System.out.println(colors[i]);
        }
        
    }
}
