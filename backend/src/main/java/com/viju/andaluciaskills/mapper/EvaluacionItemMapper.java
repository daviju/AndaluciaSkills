package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.entity.EvaluacionItem;

import org.springframework.stereotype.Component;

@Component // Anotación para indicar que esta clase es un componente
public class EvaluacionItemMapper implements GenericMapper<EvaluacionItem, EvaluacionItemDTO> { // Implementa la interfaz GenericMapper con los tipos EvaluacionItem y EvaluacionItemDTO

    @Override
    public EvaluacionItemDTO toDto(EvaluacionItem entity) { // Método para convertir una entidad EvaluacionItem en un DTO EvaluacionItemDTO
        if (entity == null) return null;

        EvaluacionItemDTO dto = new EvaluacionItemDTO(); // Crea un nuevo DTO EvaluacionItemDTO

        dto.setIdEvaluacionItem(entity.getIdEvaluacionItem()); // Asigna el ID de la entidad al ID del DTO
        dto.setValoracion(entity.getValoracion()); // Asigna la valoración de la entidad a la valoración del DTO
        dto.setEvaluacion_idEvaluacion(entity.getEvaluacion_idEvaluacion()); // Asigna el ID de la evaluación de la entidad al ID de la evaluación del DTO
        dto.setItem_idItem(entity.getItem_idItem()); // Asigna el ID del item de la entidad al ID del item del DTO

        return dto;
    }

    @Override
    public EvaluacionItem toEntity(EvaluacionItemDTO dto) { // Método para convertir un DTO EvaluacionItemDTO en una entidad EvaluacionItem
        if (dto == null) return null; 

        EvaluacionItem entity = new EvaluacionItem(); // Crea una nueva entidad EvaluacionItem
        
        entity.setIdEvaluacionItem(dto.getIdEvaluacionItem()); // Asigna el ID del DTO al ID de la entidad
        entity.setValoracion(dto.getValoracion()); // Asigna la valoración del DTO a la valoración de la entidad
        entity.setEvaluacion_idEvaluacion(dto.getEvaluacion_idEvaluacion()); // Asigna el ID de la evaluación del DTO al ID de la evaluación de la entidad
        entity.setItem_idItem(dto.getItem_idItem()); // Asigna el ID del item del DTO al ID del item de la entidad

        return entity;
    }
}
