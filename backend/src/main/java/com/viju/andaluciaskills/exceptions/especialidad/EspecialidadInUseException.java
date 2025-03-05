package com.viju.andaluciaskills.exceptions.especialidad;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EspecialidadInUseException extends RuntimeException {
    public EspecialidadInUseException(Integer id) {
        super("La especialidad con ID " + id + " está siendo utilizada y no puede ser eliminada");
    }
}
