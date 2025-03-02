package com.viju.andaluciaskills.exceptions.prueba;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Esto hace que SpringBoot devuelva un 404 Not Found en caso de que se lance esta excepción
public class PruebaNotFoundException extends RuntimeException{ // Extendemos de RuntimeException para que sea una excepción de tiempo de ejecución

    private static final long serialVersionUID = 1L;

    public PruebaNotFoundException(Integer id) {
        super("No se encontró la prueba con id: " + id);
    }

    public PruebaNotFoundException() {
        super("No se encontraron pruebas");
    }
} 
