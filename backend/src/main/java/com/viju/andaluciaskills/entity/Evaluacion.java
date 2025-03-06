package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "evaluacion")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID con autoincrement
    private Integer idEvaluacion;

    
    @Column(name = "prueba_idPrueba")
    private Integer prueba_idPrueba;

    @Column(name = "participante_idParticipante")
    private Integer participante_idParticipante;

    @Column(name = "notaFinal")
    private Double notaFinal;

    @Column(name = "user_idUser")
    private Integer user_idUser;
}
