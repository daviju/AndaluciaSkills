package com.viju.andaluciaskills.DTO;

import lombok.Data;

@Data // Genera los getters y setters
public class ItemDTO {
    private Integer idItem;
    private String descripcion;
    private Integer peso;
    private Integer grados_consecucion;
    
    private Integer prueba_id_prueba;

    // Para sacar al mejor puntuado
    private Double puntuacionMaxima;

}