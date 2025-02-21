package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.entity.Participante;
import com.viju.andaluciaskills.repository.ParticipanteRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    public List<Participante> getAllParticipantes() {
        return participanteRepository.findAll();
    }

    public Optional<Participante> getParticipanteById(Integer id) {
        return participanteRepository.findById(id);
    }

    public Participante saveParticipante(Participante participante) {
        return participanteRepository.save(participante);
    }

    public void deleteParticipante(Integer id) {
        participanteRepository.deleteById(id);
    }
}