package br.com.alura.adopet.api.controller;


import br.com.alura.adopet.api.exception.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ErrorHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    public EntityNotFoundException handleException(EntityNotFoundException exception) {
        return exception;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidacaoException.class})
    public ValidacaoException handleException(ValidacaoException exception) {
        return exception;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Exception handleException(Exception exception) {
        return exception;
    }

}
