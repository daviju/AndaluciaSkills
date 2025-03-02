package com.viju.andaluciaskills.exceptions.especialidad;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EspecialidadNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public EspecialidadNotFoundException(Integer id) {
        super("No se pudo encontrar la especialidad con el proporcionado");
    }
    
    public EspecialidadNotFoundException() {
        super("No se pudo encontrar las especialidades");
    }
}
