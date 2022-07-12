package jp.ac.keio.pro2finalproject.exception;

public class FileAccessException extends RuntimeException {
    public FileAccessException() {
        super();
    }

    public FileAccessException(String m) {
        super(m);
    }
}
