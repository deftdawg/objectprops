package com.google.code.objectprops;
/**
 * The FormatException is thrown whenever an {@link IFormat} can't parse or
 * format a value.
 * 
 * @author Michael Karneim
 */
public class FormatException extends RuntimeException {

	public FormatException() {
		super();
	}

	public FormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormatException(String message) {
		super(message);
	}

	public FormatException(Throwable cause) {
		super(cause);
	}

}
