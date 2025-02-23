package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.EvaluacionDTO;
import com.viju.andaluciaskills.entity.Evaluacion;
import com.viju.andaluciaskills.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public EvaluacionDTO save(EvaluacionDTO dto) {
        Evaluacion evaluacion = convertToEntity(dto);
        return convertToDTO(evaluacionRepository.save(evaluacion));
    }

    public Optional<EvaluacionDTO> findById(Integer id) {
        return evaluacionRepository.findById(id).map(this::convertToDTO);
    }

    public List<EvaluacionDTO> findAll() {
        return evaluacionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EvaluacionDTO update(EvaluacionDTO dto) {
        Evaluacion evaluacion = convertToEntity(dto);
        return convertToDTO(evaluacionRepository.save(evaluacion));
    }

    public void delete(Integer id) {
        evaluacionRepository.deleteById(id);
    }

    private EvaluacionDTO convertToDTO(Evaluacion evaluacion) {
        EvaluacionDTO evaluacionDTO = new EvaluacionDTO();
        evaluacionDTO.setId(evaluacion.getId());
        evaluacionDTO.setTitle(evaluacion.getTitle());
        evaluacionDTO.setDescription(evaluacion.getDescription());
        return evaluacionDTO;
    }

    private Evaluacion convertToEntity(EvaluacionDTO evaluacionDTO) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setId(evaluacionDTO.getId());
        evaluacion.setTitle(evaluacionDTO.getTitle());
        evaluacion.setDescription(evaluacionDTO.getDescription());
        return evaluacion;
    }
}