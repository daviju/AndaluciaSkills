package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.PruebaService;
import com.viju.andaluciaskills.entity.Prueba;

@RestController
@RequestMapping("/pruebas")
public class PruebaController {

    @Autowired
    private PruebaService pruebaService;

    @GetMapping
    public List<Prueba> getAllPruebas() {
        return pruebaService.getAllPruebas();
    }

    @GetMapping("/{id}")
    public Optional<Prueba> getPruebaById(@PathVariable Integer id) {
        return pruebaService.getPruebaById(id);
    }

    @PostMapping
    public Prueba createPrueba(@RequestBody Prueba prueba) {
        return pruebaService.savePrueba(prueba);
    }

    @DeleteMapping("/{id}")
    public void deletePrueba(@PathVariable Integer id) {
        pruebaService.deletePrueba(id);
    }
}

