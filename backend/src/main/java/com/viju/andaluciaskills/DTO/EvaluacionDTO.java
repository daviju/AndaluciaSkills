package com.viju.andaluciaskills.DTO;

import lombok.Data;

@Data // Genera los getters y setters
public class EvaluacionDTO {
    private Integer idEvaluacion;
    private Integer prueba_idPrueba;
    private Integer participante_idParticipante;
    private Integer user_idUser;
    private Double notaFinal;
}