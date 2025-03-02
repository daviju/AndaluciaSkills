package com.viju.andaluciaskills.exceptions.especialidad;

public class SearchEspecialidadNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public SearchEspecialidadNotFoundException() {
        super("No se encontraron resultados para la búsqueda solicitada");
    }
}
