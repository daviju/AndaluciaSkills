package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.EvaluacionDTO;
import com.viju.andaluciaskills.entity.Evaluacion;
import com.viju.andaluciaskills.repository.EvaluacionRepository;
import com.viju.andaluciaskills.mapper.EvaluacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;
    
    @Autowired
    private EvaluacionMapper evaluacionMapper;

    public EvaluacionDTO save(EvaluacionDTO dto) {
        Evaluacion evaluacion = evaluacionMapper.toEntity(dto);
        return evaluacionMapper.toDto(evaluacionRepository.save(evaluacion));
    }

    public Optional<EvaluacionDTO> findById(Integer id) {
        return evaluacionRepository.findById(id)
                .map(evaluacionMapper::toDto);
    }

    public List<EvaluacionDTO> findAll() {
        return evaluacionRepository.findAll().stream()
                .map(evaluacionMapper::toDto)
                .collect(Collectors.toList());
    }

    public EvaluacionDTO update(EvaluacionDTO dto) {
        Evaluacion evaluacion = evaluacionMapper.toEntity(dto);
        return evaluacionMapper.toDto(evaluacionRepository.save(evaluacion));
    }

    public void delete(Integer id) {
        evaluacionRepository.deleteById(id);
    }
}