package exceptions;

public class NumberOutOfRangeException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = -5657096563200579341L;

    public NumberOutOfRangeException(String error) {
        super(error);
    }
} 