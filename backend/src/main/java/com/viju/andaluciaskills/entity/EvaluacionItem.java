package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class EvaluacionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvaluacionItem;


    @ManyToOne
    @JoinColumn(name = "Evaluacion_idEvaluacion")
    private Evaluacion evaluacion;


    @ManyToOne
    @JoinColumn(name = "Item_idItem")
    private Item item;

    
    private Integer valoracion;
}
