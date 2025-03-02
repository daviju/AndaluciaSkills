package com.viju.andaluciaskills.exceptions.evaluacion;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SearchEvaluacionNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public SearchEvaluacionNotFoundException() {
        super("No se encontraron resultados para la búsqueda de evaluaciones");
    }
}
