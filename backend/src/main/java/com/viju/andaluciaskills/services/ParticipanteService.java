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

@Service // Indicamos que esta clase es un servicio de SpringBoot
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository; // Repositorio de Participante (operaciones en BD)
    
    @Autowired
    private ParticipanteMapper participanteMapper; // Mapper de Participante (mapeo de Entity a DTO y viceversa)

    
    // SAVE
    // Guarda un Participante en la BD
    public ParticipanteDTO save(ParticipanteDTO dto) {
        Participante participante = participanteMapper.toEntity(dto);
        return participanteMapper.toDto(participanteRepository.save(participante));
    }

    // FIND BY ID
    // Busca un Participante por su ID
    public Optional<ParticipanteDTO> findById(Integer id) {
        return participanteRepository.findById(id)
                .map(participanteMapper::toDto);
    }

    // FIND ALL
    // Devuelve una lista con todos los Participantes
    public List<ParticipanteDTO> findAll() {
        return participanteRepository.findAll().stream()
                .map(participanteMapper::toDto)
                .collect(Collectors.toList());
    }

    // FIND BY ESPECIALIDAD
    // Devuelve una lista con todos los Participantes de una especialidad
    public List<ParticipanteDTO> findByEspecialidad(Integer idEspecialidad) {
        List<Participante> participantes = participanteRepository.findByEspecialidadIdEspecialidad(idEspecialidad);
        return participanteMapper.toDtoList(participantes);
    }

    // UPDATE
    // Actualiza un Participante en la BD
    public ParticipanteDTO update(ParticipanteDTO dto) {
        Participante participante = participanteMapper.toEntity(dto);
        return participanteMapper.toDto(participanteRepository.save(participante));
    }

    // DELETE
    // Elimina un Participante de la BD
    public void delete(Integer id) {
        participanteRepository.deleteById(id);
    }
}