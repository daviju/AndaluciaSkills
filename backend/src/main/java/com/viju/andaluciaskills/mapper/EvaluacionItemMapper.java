package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.entity.EvaluacionItem;

import org.springframework.stereotype.Component;

@Component // Anotación para indicar que esta clase es un componente
public class EvaluacionItemMapper implements GenericMapper<EvaluacionItem, EvaluacionItemDTO> { // Implementa la interfaz GenericMapper con los tipos EvaluacionItem y EvaluacionItemDTO

    @Override
    public EvaluacionItemDTO toDto(EvaluacionItem entity) { // Método para convertir una entidad EvaluacionItem en un DTO EvaluacionItemDTO
        if (entity == null) return null;

        EvaluacionItemDTO dto = new EvaluacionItemDTO();
        dto.setIdEvaluacionItem(entity.getIdEvaluacionItem());
        dto.setValoracion(entity.getValoracion());
        dto.setEvaluacion_idEvaluacion(entity.getEvaluacion_idEvaluacion());
        dto.setItem_idItem(entity.getItem_idItem()); 
        return dto;
    }

    @Override
    public EvaluacionItem toEntity(EvaluacionItemDTO dto) {
        if (dto == null) return null;

        EvaluacionItem entity = new EvaluacionItem();
        entity.setIdEvaluacionItem(dto.getIdEvaluacionItem());
        entity.setValoracion(dto.getValoracion());
        entity.setEvaluacion_idEvaluacion(dto.getEvaluacion_idEvaluacion());
        entity.setItem_idItem(dto.getItem_idItem());

        return entity;
    }
}
