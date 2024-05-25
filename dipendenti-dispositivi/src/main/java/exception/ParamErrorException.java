package exception;

public class ParamErrorException extends RuntimeException {

    public ParamErrorException(String message) {
        super(message);
    }
}
