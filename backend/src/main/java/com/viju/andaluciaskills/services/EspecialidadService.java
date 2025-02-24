package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.EspecialidadDTO;
import com.viju.andaluciaskills.entity.Especialidad;
import com.viju.andaluciaskills.repository.EspecialidadRepository;
import com.viju.andaluciaskills.mapper.EspecialidadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    @Autowired
    private EspecialidadMapper especialidadMapper;

    public EspecialidadDTO save(EspecialidadDTO dto) {
        Especialidad especialidad = especialidadMapper.toEntity(dto);
        return especialidadMapper.toDto(especialidadRepository.save(especialidad));
    }

    public Optional<EspecialidadDTO> findById(Integer id) {
        return especialidadRepository.findById(id)
                .map(especialidadMapper::toDto);
    }

    public List<EspecialidadDTO> findAll() {
        return especialidadRepository.findAll().stream()
                .map(especialidadMapper::toDto)
                .collect(Collectors.toList());
    }

    public EspecialidadDTO update(EspecialidadDTO dto) {
        Especialidad especialidad = especialidadMapper.toEntity(dto);
        return especialidadMapper.toDto(especialidadRepository.save(especialidad));
    }

    public void delete(Integer id) {
        especialidadRepository.deleteById(id);
    }
}