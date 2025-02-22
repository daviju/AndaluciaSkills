package com.viju.andaluciaskills.services.base;

import com.viju.andaluciaskills.DTO.EspecialidadDTO;
import java.util.List;
import java.util.Optional;

public interface EspecialidadBaseService {
    EspecialidadDTO save(EspecialidadDTO dto);
    Optional<EspecialidadDTO> findById(Integer id);
    List<EspecialidadDTO> findAll();
    EspecialidadDTO update(EspecialidadDTO dto);
    void delete(Integer id);
}