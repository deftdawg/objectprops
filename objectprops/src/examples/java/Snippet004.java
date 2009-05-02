import java.awt.Color;
import java.io.IOException;
import java.util.StringTokenizer;

import com.google.code.objectprops.FormatException;
import com.google.code.objectprops.IFormat;
import com.google.code.objectprops.ObjectPropertiesStore;

/*
 * This snippet demonstrates 
 * - how to use a custom IFormat 
 */
public class Snippet004 {

	public static void main(String[] args) throws IOException {

		Color[] colors = new Color[] { Color.RED, new Color(1.0f, 0.0f, 0.0f, 0.5f), };

		// create an empty store
		ObjectPropertiesStore store = new ObjectPropertiesStore();

		// create the custom format
		ColorFormat myColorFormat = new ColorFormat();
		// and tell the store to use it for Color objects
		store.getFormatter().setFormat(myColorFormat, Color.class);

		// write the colors into the store
		store.writeObject("colors", colors);
		// store.getDatabase().store(System.out, "");

		// read the colors from the store
		Color[] rColors = (Color[]) store.readObject("colors", Color[].class);
		// print them to System.out
		for (int i = 0; i < rColors.length; ++i) {
			System.out.println("rColors[" + i + "]: " + "r=" + rColors[i].getRed() + ",g=" + rColors[i].getGreen() + ",b=" + rColors[i].getBlue() + ",a=" + rColors[i].getAlpha());
		}

	}
}

/**
 * The ColorFormat is an IFormat for java.awt.Color.
 */
class ColorFormat implements IFormat {
	/**
	 * Converts a Color object into a String.
	 * 
	 * @param obj
	 * @return a string representation of the Color object
	 * @throws FormatException
	 */
	public String format(Object obj) throws FormatException {
		Color color = (Color) obj;
		int alpha = color.getAlpha();
		if (color.getAlpha() == 255) {
			return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
		} else {
			return color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + color.getAlpha();
		}

	}

	/**
	 * Converts the given String into a new Color object.
	 * 
	 * @param str
	 * @return a new Color object
	 * @throws FormatException
	 */
	public Object parse(String str) throws FormatException {
		StringTokenizer st = new StringTokenizer(str, ",");
		if (st.countTokens() == 3) {
			int red = Integer.parseInt(st.nextToken());
			int green = Integer.parseInt(st.nextToken());
			int blue = Integer.parseInt(st.nextToken());
			return new Color(red, green, blue);
		} else if (st.countTokens() == 4) {
			int red = Integer.parseInt(st.nextToken());
			int green = Integer.parseInt(st.nextToken());
			int blue = Integer.parseInt(st.nextToken());
			int alpha = Integer.parseInt(st.nextToken());
			return new Color(red, green, blue, alpha);
		} else {
			throw new FormatException("Can't parse Color from String: " + str);
		}
	}

}
