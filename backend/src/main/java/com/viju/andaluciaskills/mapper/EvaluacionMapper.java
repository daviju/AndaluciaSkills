package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.EvaluacionDTO;
import com.viju.andaluciaskills.entity.Evaluacion;

import org.springframework.stereotype.Component;

@Component
public class EvaluacionMapper implements GenericMapper<Evaluacion, EvaluacionDTO> {


    @Override
    public EvaluacionDTO toDto(Evaluacion entity) {
        if (entity == null) return null;

        EvaluacionDTO dto = new EvaluacionDTO();
        dto.setIdEvaluacion(entity.getIdEvaluacion());
        dto.setNotaFinal(entity.getNotaFinal());
        dto.setParticipante_idParticipante(entity.getParticipante_idParticipante());
        dto.setUser_idUser(entity.getUser_idUser());
        dto.setPrueba_idPrueba(entity.getPrueba_idPrueba());
        
        return dto;
    }

    @Override
    public Evaluacion toEntity(EvaluacionDTO dto) {
        if (dto == null) return null;

        Evaluacion entity = new Evaluacion();
        entity.setIdEvaluacion(dto.getIdEvaluacion());
        entity.setNotaFinal(dto.getNotaFinal());
        entity.setParticipante_idParticipante(dto.getParticipante_idParticipante());
        entity.setUser_idUser(dto.getUser_idUser());
        entity.setPrueba_idPrueba(dto.getPrueba_idPrueba());

        return entity;
    }
}