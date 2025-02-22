package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.EvaluacionItemService;
import com.viju.andaluciaskills.entity.EvaluacionItem;

@RestController
@RequestMapping("/evaluacion-items")
public class EvaluacionItemController {

    @Autowired
    private EvaluacionItemService evaluacionItemService;

    @GetMapping
    public List<EvaluacionItem> getAllEvaluacionItems() {
        return evaluacionItemService.getAllEvaluacionItems();
    }

    @GetMapping("/{id}")
    public Optional<EvaluacionItem> getEvaluacionItemById(@PathVariable Integer id) {
        return evaluacionItemService.getEvaluacionItemById(id);
    }

    @PostMapping
    public EvaluacionItem createEvaluacionItem(@RequestBody EvaluacionItem evaluacionItem) {
        return evaluacionItemService.saveEvaluacionItem(evaluacionItem);
    }

    @DeleteMapping("/{id}")
    public void deleteEvaluacionItem(@PathVariable Integer id) {
        evaluacionItemService.deleteEvaluacionItem(id);
    }
}

