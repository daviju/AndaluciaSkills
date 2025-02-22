package com.viju.andaluciaskills.services.base;

import com.viju.andaluciaskills.DTO.PruebaDTO;
import java.util.List;
import java.util.Optional;

public interface PruebaBaseService {
    PruebaDTO save(PruebaDTO dto);
    Optional<PruebaDTO> findById(Integer id);
    List<PruebaDTO> findAll();
    PruebaDTO update(PruebaDTO dto);
    void delete(Integer id);
}