package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.entity.Evaluacion;
import com.viju.andaluciaskills.repository.EvaluacionRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public List<Evaluacion> getAllEvaluaciones() {
        return evaluacionRepository.findAll();
    }

    public Optional<Evaluacion> getEvaluacionById(Integer id) {
        return evaluacionRepository.findById(id);
    }

    public Evaluacion saveEvaluacion(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public void deleteEvaluacion(Integer id) {
        evaluacionRepository.deleteById(id);
    }
}

