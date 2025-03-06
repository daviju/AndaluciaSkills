package com.viju.andaluciaskills.DTO;

import lombok.Data;

@Data // Genera los getters y setters
public class PruebaDTO {
    private Integer idPrueba;
    private String enunciado;
    private Integer puntuacionMaxima;

    private Integer especialidad_idEspecialidad;
    private Integer participante_idParticipante;

    private String nombreUser;
    private String apellidosUser;

}