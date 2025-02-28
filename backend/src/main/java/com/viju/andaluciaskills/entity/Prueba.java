package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prueba")
    private Integer idPrueba;


    @NotBlank(message = "El enunciado es obligatorio")
    @Column(name = "enunciado", nullable = false)
    private String enunciado;

    
    @Column(name = "puntuacion_maxima")
    private Integer puntuacionMaxima;

    @Column(name = "especialidad_id_especialidad")
    private Integer especialidad_idEspecialidad;
}