package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.entity.EvaluacionItem;
import com.viju.andaluciaskills.repository.EvaluacionItemRepository;
import com.viju.andaluciaskills.mapper.EvaluacionItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionItemService {

    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository;
    
    @Autowired
    private EvaluacionItemMapper evaluacionItemMapper;

    public EvaluacionItemDTO save(EvaluacionItemDTO dto) {
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