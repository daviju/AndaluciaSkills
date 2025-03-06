package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.PruebaDTO;
import com.viju.andaluciaskills.entity.Especialidad;
import com.viju.andaluciaskills.entity.Prueba;
import com.viju.andaluciaskills.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Anotación para indicar que esta clase es un componente
public class PruebaMapper implements GenericMapper<Prueba, PruebaDTO> { 

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public PruebaDTO toDto(Prueba entity) {
        if (entity == null) return null;

        PruebaDTO dto = new PruebaDTO();
        
        dto.setIdPrueba(entity.getIdPrueba());
        dto.setEnunciado(entity.getEnunciado());
        dto.setPuntuacionMaxima(entity.getPuntuacionMaxima());
        
        if (entity.getEspecialidad_idEspecialidad() != null) {
            dto.setEspecialidad_idEspecialidad(entity.getEspecialidad_idEspecialidad());
        }
        
        return dto;
    }

    @Override
    public Prueba toEntity(PruebaDTO dto) {
        if (dto == null) return null;

        Prueba entity = new Prueba();
        
        entity.setIdPrueba(dto.getIdPrueba());
        entity.setEnunciado(dto.getEnunciado());
        entity.setPuntuacionMaxima(dto.getPuntuacionMaxima());

        if (dto.getEspecialidad_idEspecialidad() != null) {
            Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidad_idEspecialidad()).orElse(null);

            entity.setEspecialidad_idEspecialidad(especialidad != null ? especialidad.getIdEspecialidad() : null);
        }

        return entity;
    }
}
