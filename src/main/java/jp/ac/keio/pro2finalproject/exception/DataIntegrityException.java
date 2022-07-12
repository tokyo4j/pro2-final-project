package jp.ac.keio.pro2finalproject.exception;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException() {
        super();
    }

    public DataIntegrityException(String m) {
        super(m);
    }
}
