package jp.ac.keio.pro2finalproject.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jp.ac.keio.pro2finalproject.exception.AuthenticationException;
import jp.ac.keio.pro2finalproject.exception.AuthorizationException;
import jp.ac.keio.pro2finalproject.exception.DataIntegrityException;
import jp.ac.keio.pro2finalproject.exception.FileAccessException;
import lombok.Getter;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { AuthenticationException.class, AuthorizationException.class })
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage authorizationException(Exception ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(value = { DataIntegrityException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage dataIntegrityException(Exception ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(value = { FileAccessException.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage internalException(Exception ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage sqlException(Exception ex) {
        return new ErrorMessage("Could not execute query.");
    }

    @Getter
    private static class ErrorMessage {
        String error;

        public ErrorMessage(String m) {
            this.error = m;
        }
    }
}
