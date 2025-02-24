package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.EvaluacionService;
import com.viju.andaluciaskills.DTO.EvaluacionDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluacionDTO> findAll() {
        return evaluacionService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<EvaluacionDTO> findById(@PathVariable Integer id) {
        return evaluacionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluacionDTO save(@RequestBody EvaluacionDTO evaluacion) {
        return evaluacionService.save(evaluacion);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluacionDTO update(@RequestBody EvaluacionDTO evaluacion, @PathVariable Integer id) {
        evaluacion.setId(id);
        return evaluacionService.update(evaluacion);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        evaluacionService.delete(id);
    }
}

