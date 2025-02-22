package com.viju.andaluciaskills.controllers;

import com.viju.andaluciaskills.entity.Participante;
import com.viju.andaluciaskills.services.ParticipanteService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping
    public List<Participante> getAllParticipantes() {
        return participanteService.getAllParticipantes();
    }

    @GetMapping("/{id}")
    public Optional<Participante> getParticipanteById(@PathVariable Integer id) {
        return participanteService.getParticipanteById(id);
    }

    @PostMapping
    public Participante createParticipante(@RequestBody Participante participante) {
        return participanteService.saveParticipante(participante);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipante(@PathVariable Integer id) {
        participanteService.deleteParticipante(id);
    }

}
