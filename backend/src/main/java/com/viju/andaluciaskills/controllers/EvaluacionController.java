package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.EvaluacionService;
import com.viju.andaluciaskills.entity.Evaluacion;

@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping
    public List<Evaluacion> getAllEvaluaciones() {
        return evaluacionService.getAllEvaluaciones();
    }

    @GetMapping("/{id}")
    public Optional<Evaluacion> getEvaluacionById(@PathVariable Integer id) {
        return evaluacionService.getEvaluacionById(id);
    }

    @PostMapping
    public Evaluacion createEvaluacion(@RequestBody Evaluacion evaluacion) {
        return evaluacionService.saveEvaluacion(evaluacion);
    }

    @DeleteMapping("/{id}")
    public void deleteEvaluacion(@PathVariable Integer id) {
        evaluacionService.deleteEvaluacion(id);
    }
}

