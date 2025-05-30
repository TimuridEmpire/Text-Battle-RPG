package game.textbasedRPG;

class CustomException extends Exception {
	
	//Explanation copied straight from stack overflow
    /**
	 * The serialVersionUID is a universal version identifier for a Serializable class. 
	 * Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object. 
	 * If no match is found, then an InvalidClassException is thrown.
	 * 
	 * https://stackoverflow.com/questions/14274480/static-final-long-serialversionuid-1l
	 */
	private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}