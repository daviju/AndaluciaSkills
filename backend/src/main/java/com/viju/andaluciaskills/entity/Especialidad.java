package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "especialidades")
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Integer idEspecialidad;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El código no puede estar vacío")
    @Size(max = 4, message = "El código no puede tener más de 4 caracteres")
    @Column(name = "codigo", nullable = false)
    private String codigo;
}
