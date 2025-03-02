package com.viju.andaluciaskills.exceptions.evaluacionitem;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EvaluacionItemBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public EvaluacionItemBadRequestException(String mensaje) {
        super(mensaje);
    }
}
