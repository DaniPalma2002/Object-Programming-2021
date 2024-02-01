package ggc.exceptions;

/**
 * Invalid product key
 */
public class InvalidProductKeyException extends Exception{
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

    /** The requested key */
    String _key;

    /**
     * @param key
     */
    public InvalidProductKeyException(String key) {
        _key = key;
    }

    /**
     * @return the requested key
     */
    public String getKey() { return _key; }
}
