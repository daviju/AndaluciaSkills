package com.viju.andaluciaskills.services.base;

import com.viju.andaluciaskills.DTO.ParticipanteDTO;
import java.util.List;
import java.util.Optional;

public interface ParticipanteBaseService {
    ParticipanteDTO save(ParticipanteDTO dto);
    Optional<ParticipanteDTO> findById(Integer id);
    List<ParticipanteDTO> findAll();
    ParticipanteDTO update(ParticipanteDTO dto);
    void delete(Integer id);
}
