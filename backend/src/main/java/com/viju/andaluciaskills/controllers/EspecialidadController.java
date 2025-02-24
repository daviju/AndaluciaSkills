package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.EspecialidadService;
import com.viju.andaluciaskills.DTO.EspecialidadDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EspecialidadDTO> findAll() {
        return especialidadService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<EspecialidadDTO> findById(@PathVariable Integer id) {
        return especialidadService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EspecialidadDTO save(@RequestBody EspecialidadDTO especialidad) {
        return especialidadService.save(especialidad);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EspecialidadDTO update(@RequestBody EspecialidadDTO especialidad, @PathVariable Integer id) {
        especialidad.setIdEspecialidad(id);
        return especialidadService.update(especialidad);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        especialidadService.delete(id);
    }
}

