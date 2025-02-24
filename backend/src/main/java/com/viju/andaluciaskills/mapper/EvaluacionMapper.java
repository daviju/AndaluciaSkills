package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.EvaluacionDTO;
import com.viju.andaluciaskills.entity.Evaluacion;
import com.viju.andaluciaskills.repository.ParticipanteRepository;
import com.viju.andaluciaskills.repository.PruebaRepository;
import com.viju.andaluciaskills.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionMapper implements GenericMapper<Evaluacion, EvaluacionDTO> {

    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private PruebaRepository pruebaRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public EvaluacionDTO toDto(Evaluacion entity) {
        if (entity == null) return null;

        EvaluacionDTO dto = new EvaluacionDTO();
        dto.setId(entity.getIdEvaluacion());
        dto.setNotaFinal(entity.getNotaFinal());
        
        if (entity.getParticipante() != null) {
            dto.setIdParticipante(entity.getParticipante().getIdParticipante());
        }
        if (entity.getPrueba() != null) {
            dto.setIdPrueba(entity.getPrueba().getIdPrueba());
        }
        if (entity.getUsuario() != null) {
            dto.setIdUser(entity.getUsuario().getIdUser());
        }
        
        return dto;
    }

    @Override
    public Evaluacion toEntity(EvaluacionDTO dto) {
        if (dto == null) return null;

        Evaluacion entity = new Evaluacion();
        entity.setIdEvaluacion(dto.getId());
        entity.setNotaFinal(dto.getNotaFinal());

        if (dto.getIdParticipante() != null) {
            participanteRepository.findById(dto.getIdParticipante())
                .ifPresent(entity::setParticipante);
        }
        if (dto.getIdPrueba() != null) {
            pruebaRepository.findById(dto.getIdPrueba())
                .ifPresent(entity::setPrueba);
        }
        if (dto.getIdUser() != null) {
            userRepository.findById(dto.getIdUser())
                .ifPresent(entity::setUsuario);
        }

        return entity;
    }
}