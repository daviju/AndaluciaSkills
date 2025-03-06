package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "participantes")
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID con autoincrement
    @Column(name = "idParticipante")
    private Integer idParticipante;
    

    @NotBlank(message = "El nombre es obligatorio") // Verificamos que no sea null, que no este vacio y que al menos tenga un carácter
    @Column(nullable = false)
    private String nombre;

    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(nullable = false)
    private String apellidos;

    
    @NotBlank(message = "El centro es obligatorio")
    @Column(nullable = false)
    private String centro;

    
    @NotNull(message = "La especialidad es obligatoria")
    @ManyToOne
    @JoinColumn(name = "Especialidad_idEspecialidad", nullable = false)
    private Especialidad especialidad;
}
