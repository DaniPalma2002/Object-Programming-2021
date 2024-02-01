package ggc.exceptions;

public class InvalidTransactionKeyException extends Exception {

       /** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

    /** The requested key */
    int _key;

    /**
     * @param key
     */
    public InvalidTransactionKeyException(int key) {
        _key = key;
    }

    /**
     * @return the requested key
     */
    public int getKey() { return _key; }
}
