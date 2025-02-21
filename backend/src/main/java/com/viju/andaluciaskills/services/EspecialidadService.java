package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.entity.Especialidad;
import com.viju.andaluciaskills.repository.EspecialidadRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public List<Especialidad> getAllEspecialidades() {
        return especialidadRepository.findAll();
    }

    public Optional<Especialidad> getEspecialidadById(Integer id) {
        return especialidadRepository.findById(id);
    }

    public Especialidad saveEspecialidad(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public void deleteEspecialidad(Integer id) {
        especialidadRepository.deleteById(id);
    }
}
