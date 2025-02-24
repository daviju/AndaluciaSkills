package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.EvaluacionItemService;
import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/evaluacion-items")
public class EvaluacionItemController {

    @Autowired
    private EvaluacionItemService evaluacionItemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluacionItemDTO> findAll() {
        return evaluacionItemService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<EvaluacionItemDTO> findById(@PathVariable Integer id) {
        return evaluacionItemService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluacionItemDTO save(@RequestBody EvaluacionItemDTO evaluacionItem) {
        return evaluacionItemService.save(evaluacionItem);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluacionItemDTO update(@RequestBody EvaluacionItemDTO evaluacionItem, @PathVariable Integer id) {
        evaluacionItem.setIdEvaluacionItem(id);
        return evaluacionItemService.update(evaluacionItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        evaluacionItemService.delete(id);
    }
}

