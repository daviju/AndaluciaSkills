package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.EspecialidadDTO;
import com.viju.andaluciaskills.entity.Especialidad;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadMapper implements GenericMapper<Especialidad, EspecialidadDTO> {

    @Override
    public EspecialidadDTO toDto(Especialidad entity) {
        if (entity == null) return null;

        EspecialidadDTO dto = new EspecialidadDTO();
        dto.setIdEspecialidad(entity.getIdEspecialidad());
        dto.setNombre(entity.getNombre());
        dto.setCodigo(entity.getCodigo());
        
        return dto;
    }

    @Override
    public Especialidad toEntity(EspecialidadDTO dto) {
        if (dto == null) return null;

        Especialidad entity = new Especialidad();
        entity.setIdEspecialidad(dto.getIdEspecialidad());
        entity.setNombre(dto.getNombre());
        entity.setCodigo(dto.getDescription());
        
        return entity;
    }
}