package com.viju.andaluciaskills.DTO;

public class ItemDTO {
    private Integer idItem;
    private String descripcion;
    private Integer peso;
    private Integer gradosConsecucion;

    // Getters and Setters
    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getGradosConsecucion() {
        return gradosConsecucion;
    }

    public void setGradosConsecucion(Integer gradosConsecucion) {
        this.gradosConsecucion = gradosConsecucion;
    }
}