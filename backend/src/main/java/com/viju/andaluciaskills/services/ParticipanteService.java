package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.ParticipanteDTO;
import com.viju.andaluciaskills.entity.Participante;
import com.viju.andaluciaskills.repository.ParticipanteRepository;
import com.viju.andaluciaskills.mapper.ParticipanteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;
    
    @Autowired
    private ParticipanteMapper participanteMapper;

    public ParticipanteDTO save(ParticipanteDTO dto) {
        Participante participante = participanteMapper.toEntity(dto);
        return participanteMapper.toDto(participanteRepository.save(participante));
    }

    public Optional<ParticipanteDTO> findById(Integer id) {
        return participanteRepository.findById(id)
                .map(participanteMapper::toDto);
    }

    public List<ParticipanteDTO> findAll() {
        return participanteRepository.findAll().stream()
                .map(participanteMapper::toDto)
                .collect(Collectors.toList());
    }

    public ParticipanteDTO update(ParticipanteDTO dto) {
        Participante participante = participanteMapper.toEntity(dto);
        return participanteMapper.toDto(participanteRepository.save(participante));
    }

    public void delete(Integer id) {
        participanteRepository.deleteById(id);
    }
}