package com.viju.andaluciaskills.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para manejar participantes.
 * Usamos @JsonProperty para mapear nombres de JSON a variables Java.
 */

@Data
public class ParticipanteDTO {
    private Integer idParticipante;
    private String nombre;
    private String apellidos;
    private String centro;

    @JsonProperty("especialidad_id_especialidad")
    private Integer especialidad_idEspecialidad;

    private String nombreEspecialidad;

}