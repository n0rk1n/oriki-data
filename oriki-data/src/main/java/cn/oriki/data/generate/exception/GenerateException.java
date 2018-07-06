package cn.oriki.data.generate.exception;

public class GenerateException extends Exception {

    private static final long serialVersionUID = 8774328224371754801L;

    public GenerateException(String message) {
        super(message);
    }

    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }

}
