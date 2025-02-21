package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvaluacion;


    @ManyToOne
    @JoinColumn(name = "Participante_idParticipante")
    private Participante participante;


    @ManyToOne
    @JoinColumn(name = "Prueba_idPrueba")
    private Prueba prueba;


    @ManyToOne
    @JoinColumn(name = "User_idUser")
    private User usuario;

    
    private Double notaFinal;
}
