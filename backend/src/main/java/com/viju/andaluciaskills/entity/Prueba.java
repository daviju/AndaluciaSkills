package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrueba;

    private String enunciado;
    private Integer puntuacionMaxima;

    
    @ManyToOne
    @JoinColumn(name = "Especialidad_idEspecialidad")
    private Especialidad especialidad;
}