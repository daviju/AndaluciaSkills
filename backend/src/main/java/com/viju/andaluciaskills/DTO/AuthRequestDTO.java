package com.viju.andaluciaskills.DTO;

import lombok.Data;

@Data // Genera automaticamente getters y setters y constructores
public class AuthRequestDTO {
    private String username;
    private String password;

}