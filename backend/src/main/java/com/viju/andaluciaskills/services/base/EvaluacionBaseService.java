package com.viju.andaluciaskills.services.base;

import com.viju.andaluciaskills.DTO.EvaluacionDTO;
import java.util.List;
import java.util.Optional;

public interface EvaluacionBaseService {
    EvaluacionDTO save(EvaluacionDTO dto);
    Optional<EvaluacionDTO> findById(Integer id);
    List<EvaluacionDTO> findAll();
    EvaluacionDTO update(EvaluacionDTO dto);
    void delete(Integer id);
}