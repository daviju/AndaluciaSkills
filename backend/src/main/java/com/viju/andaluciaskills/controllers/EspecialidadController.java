package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.EspecialidadService;
import com.viju.andaluciaskills.entity.Especialidad;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public List<Especialidad> getAllEspecialidades() {
        return especialidadService.getAllEspecialidades();
    }

    @GetMapping("/{id}")
    public Optional<Especialidad> getEspecialidadById(@PathVariable Integer id) {
        return especialidadService.getEspecialidadById(id);
    }

    @PostMapping
    public Especialidad createEspecialidad(@RequestBody Especialidad especialidad) {
        return especialidadService.saveEspecialidad(especialidad);
    }

    @DeleteMapping("/{id}")
    public void deleteEspecialidad(@PathVariable Integer id) {
        especialidadService.deleteEspecialidad(id);
    }
}

