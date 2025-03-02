package com.viju.andaluciaskills.exceptions.participante;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SearchParticipanteNoResultException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public SearchParticipanteNoResultException() {
        super("No se encontraron resultados para la búsqueda de participantes");
    }
}
