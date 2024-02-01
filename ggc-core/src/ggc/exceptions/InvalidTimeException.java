package ggc.exceptions;

/**
 * Invalid date
 */
public class InvalidTimeException extends Exception{
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

    /** The requested time */
    int _time;

    /**
     * @param time
     */
    public InvalidTimeException(int time) {
        _time = time;
    }

    /**
     * @return the requested time
     */
    public int getTime() {
        return _time;
    }

}
