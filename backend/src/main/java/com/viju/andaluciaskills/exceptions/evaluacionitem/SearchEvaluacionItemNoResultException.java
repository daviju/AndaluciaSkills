package com.viju.andaluciaskills.exceptions.evaluacionitem;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SearchEvaluacionItemNoResultException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public SearchEvaluacionItemNoResultException() {
        super("No se encontraron resultados para la búsqueda de ítems de evaluación");
    }
}