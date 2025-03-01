package com.viju.andaluciaskills.DTO;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para manejar evaluaciones de items.
 * Usamos @JsonProperty para mapear nombres de JSON a variables Java.
 */

@Data
public class EvaluacionItemDTO {
    private Integer idEvaluacionItem;

    @JsonProperty("evaluacion_id_evaluacion")
    private Integer evaluacion_idEvaluacion;

    @JsonProperty("item_id_item")
    private Integer item_idItem;
    
    @JsonProperty("prueba_id_prueba")
    private Integer prueba_id_prueba;

    private Double valoracion;

}