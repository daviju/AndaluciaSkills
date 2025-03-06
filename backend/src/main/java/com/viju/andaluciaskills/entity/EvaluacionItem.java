package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "evaluacion_items")
public class EvaluacionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID con autoincrement
    @Column(name = "id_evaluacion_item")
    private Integer idEvaluacionItem;

    
    @Column(name = "evaluacion_id_evaluacion")
    private Integer evaluacion_idEvaluacion;

    @Column(name = "item_id_item")
    private Integer item_idItem;

    @Column(name = "prueba_id_prueba")
    private Integer prueba_idPrueba;

    @Column(name = "valoracion")
    private Double valoracion;
}
