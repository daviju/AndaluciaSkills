package com.viju.andaluciaskills.DTO;

public class EvaluacionItemDTO {
    private Integer idEvaluacionItem;
    private Integer evaluacion;
    private Integer item;
    private Integer valoracion;

    // Getters and Setters
    public Integer getIdEvaluacionItem() {
        return idEvaluacionItem;
    }

    public void setIdEvaluacionItem(Integer idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    public Integer getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Integer evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }
}