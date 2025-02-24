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
        dto.setIdEvaluacion(entity.getIdEvaluacion());
        dto.setNotaFinal(entity.getNotaFinal());
        
        if (entity.getParticipante() != null) {
            dto.setParticipanteId(entity.getParticipante().getIdParticipante());
        }
        if (entity.getPrueba() != null) {
            dto.setPruebaId(entity.getPrueba().getIdPrueba());
        }
        if (entity.getUsuario() != null) {
            dto.setUsuarioId(entity.getUsuario().getIdUser());
        }
        
        return dto;
    }

    @Override
    public Evaluacion toEntity(EvaluacionDTO dto) {
        if (dto == null) return null;

        Evaluacion entity = new Evaluacion();
        entity.setIdEvaluacion(dto.getIdEvaluacion());
        entity.setNotaFinal(dto.getNotaFinal());

        if (dto.getParticipanteId() != null) {
            participanteRepository.findById(dto.getParticipanteId())
                .ifPresent(entity::setParticipante);
        }
        if (dto.getPruebaId() != null) {
            pruebaRepository.findById(dto.getPruebaId())
                .ifPresent(entity::setPrueba);
        }
        if (dto.getUsuarioId() != null) {
            userRepository.findById(dto.getUsuarioId())
                .ifPresent(entity::setUsuario);
        }

        return entity;
    }
}