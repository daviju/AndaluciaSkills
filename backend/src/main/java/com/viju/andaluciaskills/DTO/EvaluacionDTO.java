package com.viju.andaluciaskills.DTO;

public class EvaluacionDTO {
    private Integer idEvaluacion;
    private Integer idParticipante;
    private Integer idPrueba;
    private Integer idUser;
    private Double notaFinal;


    // Getters and Setters
    public Integer getId() {
        return idEvaluacion;
    }

    public void setId(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }


    public Integer getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Integer idParticipante) {
        this.idParticipante = idParticipante;
    }


    public Integer getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
    }


    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    
    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

}