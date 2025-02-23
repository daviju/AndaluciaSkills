package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.PruebaDTO;
import com.viju.andaluciaskills.entity.Prueba;
import com.viju.andaluciaskills.repository.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PruebaService {

    @Autowired
    private PruebaRepository pruebaRepository;

    public PruebaDTO save(PruebaDTO dto) {
        Prueba prueba = convertToEntity(dto);
        return convertToDTO(pruebaRepository.save(prueba));
    }

    public Optional<PruebaDTO> findById(Integer id) {
        return pruebaRepository.findById(id).map(this::convertToDTO);
    }

    public List<PruebaDTO> findAll() {
        return pruebaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PruebaDTO update(PruebaDTO dto) {
        Prueba prueba = convertToEntity(dto);
        return convertToDTO(pruebaRepository.save(prueba));
    }

    public void delete(Integer id) {
        pruebaRepository.deleteById(id);
    }

    private PruebaDTO convertToDTO(Prueba prueba) {
        PruebaDTO pruebaDTO = new PruebaDTO();
        pruebaDTO.setId(prueba.getId());
        pruebaDTO.setTitle(prueba.getTitle());
        pruebaDTO.setDescription(prueba.getDescription());
        return pruebaDTO;
    }

    private Prueba convertToEntity(PruebaDTO pruebaDTO) {
        Prueba prueba = new Prueba();
        prueba.setId(pruebaDTO.getId());
        prueba.setTitle(pruebaDTO.getTitle());
        prueba.setDescription(pruebaDTO.getDescription());
        return prueba;
    }
}