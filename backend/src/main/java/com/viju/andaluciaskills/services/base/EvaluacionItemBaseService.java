package com.viju.andaluciaskills.services.base;

import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import java.util.List;
import java.util.Optional;

public interface EvaluacionItemBaseService {
    EvaluacionItemDTO save(EvaluacionItemDTO dto);
    Optional<EvaluacionItemDTO> findById(Integer id);
    List<EvaluacionItemDTO> findAll();
    EvaluacionItemDTO update(EvaluacionItemDTO dto);
    void delete(Integer id);
}