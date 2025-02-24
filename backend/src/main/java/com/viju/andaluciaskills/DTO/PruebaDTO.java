package com.viju.andaluciaskills.DTO;

public class PruebaDTO {
    private Integer idPrueba;
    private String enunciado;
    private Integer puntuacionMaxima;
    private Integer especialidad;

    // Getters and Setters
    public Integer getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
    }

    
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }


    public Integer getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public void setPuntuacionMaxima(Integer puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }


    public Integer getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Integer especialidad) {
        this.especialidad = especialidad;
    }
}