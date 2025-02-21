package com.viju.andaluciaskills.entity;


import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    private String role;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String dni;

    
    @ManyToOne
    @JoinColumn(name = "Especialidad_idEspecialidad", nullable = true)
    private Especialidad especialidad;
}
