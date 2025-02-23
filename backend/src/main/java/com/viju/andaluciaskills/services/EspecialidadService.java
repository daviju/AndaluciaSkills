package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.EspecialidadDTO;
import com.viju.andaluciaskills.entity.Especialidad;
import com.viju.andaluciaskills.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public EspecialidadDTO save(EspecialidadDTO dto) {
        Especialidad especialidad = convertToEntity(dto);
        return convertToDTO(especialidadRepository.save(especialidad));
    }

    public Optional<EspecialidadDTO> findById(Integer id) {
        return especialidadRepository.findById(id).map(this::convertToDTO);
    }

    public List<EspecialidadDTO> findAll() {
        return especialidadRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EspecialidadDTO update(EspecialidadDTO dto) {
        Especialidad especialidad = convertToEntity(dto);
        return convertToDTO(especialidadRepository.save(especialidad));
    }

    public void delete(Integer id) {
        especialidadRepository.deleteById(id);
    }

    private EspecialidadDTO convertToDTO(Especialidad especialidad) {
        EspecialidadDTO especialidadDTO = new EspecialidadDTO();
        especialidadDTO.setId(especialidad.getId());
        especialidadDTO.setName(especialidad.getName());
        especialidadDTO.setDescription(especialidad.getDescription());
        return especialidadDTO;
    }

    private Especialidad convertToEntity(EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = new Especialidad();
        especialidad.setId(especialidadDTO.getId());
        especialidad.setName(especialidadDTO.getName());
        especialidad.setDescription(especialidadDTO.getDescription());
        return especialidad;
    }
}