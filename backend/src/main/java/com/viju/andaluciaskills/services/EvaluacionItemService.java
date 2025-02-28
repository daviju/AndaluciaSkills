package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.entity.*;
import com.viju.andaluciaskills.repository.*;
import com.viju.andaluciaskills.mapper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Indicamos que esta clase es un servicio de SpringBoot
public class EvaluacionItemService {
    
    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository;
    
    @Autowired
    private EvaluacionItemMapper evaluacionItemMapper;

    public EvaluacionItemDTO save(EvaluacionItemDTO dto) {
        try {
            System.out.println("Entramos en guardar evaluaciones");

            // validamos
            if (dto.getEvaluacion_idEvaluacion() == null) {
                throw new Exception("El id de la evaluación no puede ser nulo");
            }

            if (dto.getItem_idItem() == null) {
                throw new Exception("El id del item no puede ser nulo");
            }

            if (dto.getPrueba_id_prueba() == null) {
                throw new Exception("El id de la prueba no puede ser nulo");
            }

            // Pasamos de DTO a Entity
            EvaluacionItem = evaluacionItem = evaluacionItemMapper.toEntity(dto);

            // Con esto hacemos a caso hecho que el ID de la prueba se asigne correctamente
            evaluacionItem.setPrueba_idPrueba(dto.getPrueba_id_prueba());

            EvaluacionItem saveEvaluacionItem = evaluacionItemRepository.save(evaluacionItem);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
        EvaluacionItem evaluacionItem = evaluacionItemMapper.toEntity(dto);
        return evaluacionItemMapper.toDto(evaluacionItemRepository.save(evaluacionItem));
    }

    public Optional<EvaluacionItemDTO> findById(Integer id) {
        return evaluacionItemRepository.findById(id)
                .map(evaluacionItemMapper::toDto);
    }

    public List<EvaluacionItemDTO> findAll() {
        return evaluacionItemRepository.findAll().stream()
                .map(evaluacionItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public EvaluacionItemDTO update(EvaluacionItemDTO dto) {
        EvaluacionItem evaluacionItem = evaluacionItemMapper.toEntity(dto);
        return evaluacionItemMapper.toDto(evaluacionItemRepository.save(evaluacionItem));
    }

    public void delete(Integer id) {
        evaluacionItemRepository.deleteById(id);
    }
}