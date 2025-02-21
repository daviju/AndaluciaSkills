package com.viju.andaluciaskills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    private String descripcion;
    private Integer peso;
    private Integer gradosConsecucion;
}
