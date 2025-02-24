package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.ParticipanteDTO;
import com.viju.andaluciaskills.entity.Participante;
import com.viju.andaluciaskills.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParticipanteMapper implements GenericMapper<Participante, ParticipanteDTO> {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public ParticipanteDTO toDto(Participante entity) {
        if (entity == null) return null;

        ParticipanteDTO dto = new ParticipanteDTO();
        dto.setIdParticipante(entity.getIdParticipante());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setCentro(entity.getCentro());
        
        if (entity.getEspecialidad() != null) {
            dto.setEspecialidad(entity.getEspecialidad().getIdEspecialidad());
        }
        
        return dto;
    }

    @Override
    public Participante toEntity(ParticipanteDTO dto) {
        if (dto == null) return null;

        Participante entity = new Participante();
        entity.setIdParticipante(dto.getIdParticipante());
        entity.setNombre(dto.getNombre());
        entity.setApellidos(dto.getApellidos());
        entity.setCentro(dto.getCentro());

        if (dto.getEspecialidad() != null) {
            especialidadRepository.findById(dto.getEspecialidad())
                .ifPresent(entity::setEspecialidad);
        }

        return entity;
    }
}
