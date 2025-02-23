package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.entity.EvaluacionItem;
import com.viju.andaluciaskills.repository.EvaluacionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionItemService {

    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository;

    public EvaluacionItemDTO save(EvaluacionItemDTO dto) {
        EvaluacionItem evaluacionItem = convertToEntity(dto);
        return convertToDTO(evaluacionItemRepository.save(evaluacionItem));
    }

    public Optional<EvaluacionItemDTO> findById(Integer id) {
        return evaluacionItemRepository.findById(id).map(this::convertToDTO);
    }

    public List<EvaluacionItemDTO> findAll() {
        return evaluacionItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EvaluacionItemDTO update(EvaluacionItemDTO dto) {
        EvaluacionItem evaluacionItem = convertToEntity(dto);
        return convertToDTO(evaluacionItemRepository.save(evaluacionItem));
    }

    public void delete(Integer id) {
        evaluacionItemRepository.deleteById(id);
    }

    private EvaluacionItemDTO convertToDTO(EvaluacionItem evaluacionItem) {
        EvaluacionItemDTO evaluacionItemDTO = new EvaluacionItemDTO();
        evaluacionItemDTO.setId(evaluacionItem.getId());
        evaluacionItemDTO.setName(evaluacionItem.getName());
        evaluacionItemDTO.setDescription(evaluacionItem.getDescription());
        return evaluacionItemDTO;
    }

    private EvaluacionItem convertToEntity(EvaluacionItemDTO evaluacionItemDTO) {
        EvaluacionItem evaluacionItem = new EvaluacionItem();
        evaluacionItem.setId(evaluacionItemDTO.getId());
        evaluacionItem.setName(evaluacionItemDTO.getName());
        evaluacionItem.setDescription(evaluacionItemDTO.getDescription());
        return evaluacionItem;
    }
}