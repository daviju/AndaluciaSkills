package com.viju.andaluciaskills.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data // Genera los getters y setters
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class AuthResponseDTO {
    private String token;
    private String username;
    private String role;
    private String nombre;
    private String apellidos;
    private Integer especialidadId;
    private Integer idUser;
}