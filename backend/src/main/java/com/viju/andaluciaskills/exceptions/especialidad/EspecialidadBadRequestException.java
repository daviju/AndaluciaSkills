package com.viju.andaluciaskills.exceptions.especialidad;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EspecialidadBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public EspecialidadBadRequestException() {
        super("No se puede crear una especialidad con un ID ya existente");
    }
}
