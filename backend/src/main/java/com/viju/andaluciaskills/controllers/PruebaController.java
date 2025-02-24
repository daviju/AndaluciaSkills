package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.PruebaService;
import com.viju.andaluciaskills.DTO.PruebaDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    @Autowired
    private PruebaService pruebaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PruebaDTO> findAll() {
        return pruebaService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<PruebaDTO> findById(@PathVariable Integer id) {
        return pruebaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PruebaDTO save(@RequestBody PruebaDTO prueba) {
        return pruebaService.save(prueba);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PruebaDTO update(@RequestBody PruebaDTO prueba, @PathVariable Integer id) {
        prueba.setIdPrueba(id);
        return pruebaService.update(prueba);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        pruebaService.delete(id);
    }
}

