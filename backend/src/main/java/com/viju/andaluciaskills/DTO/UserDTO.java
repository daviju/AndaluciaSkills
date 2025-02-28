package com.viju.andaluciaskills.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

//  Usamos @JsonProperty para mapear nombres de JSON a variables Java.

@Data
public class UserDTO {
    private Integer idUser;
    private String role;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String dni;

    @JsonProperty("especialidad_id_especialidad")
    private Integer especialidad;

    private String nombreEspecialidad;

}