package ggc.exceptions;

/**
 * Invalid partner key
 */
public class InvalidProductException extends Exception{
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

    
    String _key;
    int _requested;
    int _available;

    
    public InvalidProductException(String key, int requested, int available) {
        _key = key;
        _requested = requested;
        _available = available;
    }

    
    public String getKey() { return _key; }
    public int getRequested() { return _requested; }
    public int getAvailable() { return _available; }
}
