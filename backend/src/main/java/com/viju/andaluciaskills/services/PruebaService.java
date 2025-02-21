package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.entity.Prueba;
import com.viju.andaluciaskills.repository.PruebaRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class PruebaService {

    @Autowired
    private PruebaRepository pruebaRepository;

    public List<Prueba> getAllPruebas() {
        return pruebaRepository.findAll();
    }

    public Optional<Prueba> getPruebaById(Integer id) {
        return pruebaRepository.findById(id);
    }

    public Prueba savePrueba(Prueba prueba) {
        return pruebaRepository.save(prueba);
    }

    public void deletePrueba(Integer id) {
        pruebaRepository.deleteById(id);
    }
}
