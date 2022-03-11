/*
 * 
 */
package us.jyni.frame.jpa;

/**
 * @author jynius
 *
 */
public class ModelCreationException extends RuntimeException {

	private static final long serialVersionUID = -6489454807408360817L;

	/**
	 * 
	 */
	public ModelCreationException() {}

	/**
	 * @param message
	 */
	public ModelCreationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ModelCreationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ModelCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ModelCreationException(
			String message,
			Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
