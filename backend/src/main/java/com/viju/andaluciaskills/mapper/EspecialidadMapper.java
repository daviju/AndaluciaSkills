package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.EspecialidadDTO;
import com.viju.andaluciaskills.entity.Especialidad;
import org.springframework.stereotype.Component;

@Component // Anotación que indica que la clase es un componente de Spring
public class EspecialidadMapper implements GenericMapper<Especialidad, EspecialidadDTO> { // Implementa la interfaz GenericMapper con los tipos Especialidad y EspecialidadDTO

    @Override 
    public EspecialidadDTO toDto(Especialidad entity) { // Convierte una entidad Especialidad en un DTO EspecialidadDTO
        if (entity == null) return null;

        EspecialidadDTO dto = new EspecialidadDTO(); // Crea un nuevo EspecialidadDTO
        
        dto.setIdEspecialidad(entity.getIdEspecialidad()); // Asigna el ID de la entidad al ID del DTO
        dto.setNombre(entity.getNombre()); // Asigna el nombre de la entidad al nombre del DTO
        dto.setCodigo(entity.getCodigo()); // Asigna el código de la entidad al código del DTO
        
        return dto; // Devuelve el DTO
    }

    @Override
    public Especialidad toEntity(EspecialidadDTO dto) { // Convierte un DTO EspecialidadDTO en una entidad Especialidad
        if (dto == null) return null;

        Especialidad entity = new Especialidad(); // Crea una nueva entidad Especialidad
        
        entity.setIdEspecialidad(dto.getIdEspecialidad()); // Asigna el ID del DTO al ID de la entidad
        entity.setNombre(dto.getNombre()); // Asigna el nombre del DTO al nombre de la entidad
        entity.setCodigo(dto.getCodigo()); // Asigna la descripción del DTO a la descripción de la entidad
        
        return entity; // Devuelve la entidad
    }
}