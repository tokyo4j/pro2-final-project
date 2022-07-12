package jp.ac.keio.pro2finalproject.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String m) {
        super(m);
    }
}
