package jp.ac.keio.pro2finalproject.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("Unauthorized.");
    }

    public AuthorizationException(String m) {
        super(m);
    }
}
