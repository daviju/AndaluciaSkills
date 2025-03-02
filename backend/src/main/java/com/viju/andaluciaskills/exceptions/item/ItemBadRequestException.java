package com.viju.andaluciaskills.exceptions.item;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ItemBadRequestException(String mensaje) {
        super(mensaje);
    }
}