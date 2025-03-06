package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID con autoincrement
    @Column(name = "id_item")
    private Integer idItem;

    
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "peso")
    private Integer peso;

    @Column(name = "grados_consecucion")
    private Integer gradosConsecucion;

    @Column(name = "prueba_id_prueba")
    private Integer pruebaIdPrueba;
}
