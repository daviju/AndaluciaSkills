package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.ParticipanteDTO;
import com.viju.andaluciaskills.entity.Participante;
import com.viju.andaluciaskills.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    public ParticipanteDTO save(ParticipanteDTO dto) {
        Participante participante = convertToEntity(dto);
        return convertToDTO(participanteRepository.save(participante));
    }

    public Optional<ParticipanteDTO> findById(Integer id) {
        return participanteRepository.findById(id).map(this::convertToDTO);
    }

    public List<ParticipanteDTO> findAll() {
        return participanteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ParticipanteDTO update(ParticipanteDTO dto) {
        Participante participante = convertToEntity(dto);
        return convertToDTO(participanteRepository.save(participante));
    }

    public void delete(Integer id) {
        participanteRepository.deleteById(id);
    }

    private ParticipanteDTO convertToDTO(Participante participante) {
        ParticipanteDTO participanteDTO = new ParticipanteDTO();
        participanteDTO.setId(participante.getId());
        participanteDTO.setName(participante.getName());
        participanteDTO.setBirthDate(participante.getBirthDate());
        return participanteDTO;
    }

    private Participante convertToEntity(ParticipanteDTO participanteDTO) {
        Participante participante = new Participante();
        participante.setId(participanteDTO.getId());
        participante.setName(participanteDTO.getName());
        participante.setBirthDate(participanteDTO.getBirthDate());
        return participante;
    }
}