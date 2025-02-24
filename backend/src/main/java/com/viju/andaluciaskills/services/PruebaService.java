package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.PruebaDTO;
import com.viju.andaluciaskills.entity.Prueba;
import com.viju.andaluciaskills.repository.PruebaRepository;
import com.viju.andaluciaskills.mapper.PruebaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PruebaService {

    @Autowired
    private PruebaRepository pruebaRepository;
    
    @Autowired
    private PruebaMapper pruebaMapper;

    public PruebaDTO save(PruebaDTO dto) {
        Prueba prueba = pruebaMapper.toEntity(dto);
        return pruebaMapper.toDto(pruebaRepository.save(prueba));
    }

    public Optional<PruebaDTO> findById(Integer id) {
        return pruebaRepository.findById(id)
                .map(pruebaMapper::toDto);
    }

    public List<PruebaDTO> findAll() {
        return pruebaRepository.findAll().stream()
                .map(pruebaMapper::toDto)
                .collect(Collectors.toList());
    }

    public PruebaDTO update(PruebaDTO dto) {
        Prueba prueba = pruebaMapper.toEntity(dto);
        return pruebaMapper.toDto(pruebaRepository.save(prueba));
    }

    public void delete(Integer id) {
        pruebaRepository.deleteById(id);
    }
}