package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.ParticipanteService;
import com.viju.andaluciaskills.DTO.ParticipanteDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipanteDTO> findAll() {
        return participanteService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ParticipanteDTO> findById(@PathVariable Integer id) {
        return participanteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipanteDTO save(@RequestBody ParticipanteDTO participante) {
        return participanteService.save(participante);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParticipanteDTO update(@RequestBody ParticipanteDTO participante, @PathVariable Integer id) {
        participante.setIdParticipante(id);
        return participanteService.update(participante);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        participanteService.delete(id);
    }
}
