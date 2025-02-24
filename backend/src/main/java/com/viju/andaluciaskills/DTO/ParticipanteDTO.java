package com.viju.andaluciaskills.DTO;

public class ParticipanteDTO {
    private Integer idParticipante;
    private String nombre;
    private String apellidos;
    private String centro;
    private Integer especialidad;

    // Getters and Setters
    public Integer getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Integer idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public Integer getEspecialidad() {
        return especialidad;
    }


    public void setEspecialidad(Integer especialidad) {
        this.especialidad = especialidad;
    }
}