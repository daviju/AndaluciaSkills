package com.viju.andaluciaskills.DTO;

public class EvaluacionItemDTO {
    private Integer idEvaluacionItem;
    private String Evaluacion;
    private String Item;
    private Integer valoracion;

    // Getters and Setters
    public Integer getIdEvaluacionItem() {
        return idEvaluacionItem;
    }

    public void setIdEvaluacionItem(Integer idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    
    public String getEvaluacion() {
        return Evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.Evaluacion = evaluacion;
    }


    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        this.Item = item;
    }


    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }
}