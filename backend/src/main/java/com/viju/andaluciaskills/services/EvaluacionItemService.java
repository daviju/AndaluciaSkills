package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.entity.*;
import com.viju.andaluciaskills.repository.*;
import com.viju.andaluciaskills.mapper.*;
import com.viju.andaluciaskills.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service // Indicamos que esta clase es un servicio de SpringBoot
public class EvaluacionItemService {

    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository; // Repositorio de EvaluacionItem (operaciones en BD)

    @Autowired
    private EvaluacionItemMapper evaluacionItemMapper; // Mapper de EvaluacionItem (mapeo de Entity a DTO y viceversa)

    @Autowired
    private EvaluacionService evaluacionService; // Servicio de Evaluacion (operaciones en BD con las notas de las evaluaciones)

    // SAVE
    // Metodo para guardar una evaluacion item
    public EvaluacionItemDTO save(EvaluacionItemDTO dto) throws Exception {
        try {
            System.out.println("Entramos en guardar evaluaciones");

            // Validamos
            if (dto.getEvaluacion_idEvaluacion() == null) {
                throw new IllegalArgumentException("El id de la evaluación no puede ser nulo");
            }

            if (dto.getItem_idItem() == null) {
                throw new IllegalArgumentException("El id del item no puede ser nulo");
            }

            if (dto.getPrueba_id_prueba() == null) {
                throw new IllegalArgumentException("El id de la prueba no puede ser nulo");
            }

            // Pasamos de DTO a Entity
            EvaluacionItem evaluacionItem = evaluacionItemMapper.toEntity(dto);

            // Con esto hacemos a caso hecho que el ID de la prueba se asigne correctamente
            evaluacionItem.setPrueba_idPrueba(dto.getPrueba_id_prueba());

            // Guardamos en la base de datos
            EvaluacionItem savedEvaluacionItem = evaluacionItemRepository.save(evaluacionItem);

            // Pasamos de Entity a DTO y lo devolvemos
            return evaluacionItemMapper.toDto(savedEvaluacionItem);

        } catch (Exception e) {
            System.out.println("Error (EvaluacionItemService): " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // SAVE ALL
    // Metodo para guardar varios EvaluacionItem a la vez
    public List<EvaluacionItemDTO> saveAll(List<EvaluacionItemDTO> dtos) throws Exception {
        try {
            List<EvaluacionItemDTO> savedItems = new ArrayList<>();

            for (EvaluacionItemDTO dto : dtos) {
                try {
                    if (dto.getEvaluacion_idEvaluacion() == null ||
                            dto.getItem_idItem() == null ||
                            dto.getPrueba_id_prueba() == null ||
                            dto.getValoracion() == null) {
                        throw new IllegalArgumentException("Faltan datos para guardar el item");
                    }

                    EvaluacionItemDTO saved = save(dto);
                    savedItems.add(saved);

                } catch (Exception e) {
                    System.err.println("Error guardando item: " + e.getMessage());
                    throw e;
                }
            }

            return savedItems;

        } catch (Exception e) {
            System.err.println("Error en SaveALL: " + e.getMessage());
            throw e;
        }
    }

    // FIND BY ID
    // Metodo para buscar un EvaluacionItem por su ID
    public Optional<EvaluacionItemDTO> findById(Integer id) {
        return evaluacionItemRepository.findById(id)
                .map(evaluacionItemMapper::toDto);
    }

    // FIND ALL
    // Metodo para buscar todos los EvaluacionItem
    public List<EvaluacionItemDTO> findAll() {
        return evaluacionItemRepository.findAll().stream()
                .map(evaluacionItemMapper::toDto)
                .collect(Collectors.toList());
    }

    // UPDATE
    // Metodo para actualizar un EvaluacionItem
    public EvaluacionItemDTO update(EvaluacionItemDTO dto) {
        // Validamos que el ID no sea nulo
        if (dto.getIdEvaluacionItem() == null) {
            throw new IllegalArgumentException("El id del EvaluacionItem no puede ser nulo para actualizar");
        }

        // Pasamos de DTO a Entity, lo actualizamos en la BD y actualizamos la nota
        // final de la evaluacion
        EvaluacionItem evaluacionItem = evaluacionItemMapper.toEntity(dto);
        EvaluacionItem updatedEvaluacionItem = evaluacionItemRepository.save(evaluacionItem);
        evaluacionService.actualizarNotaFinal(dto.getEvaluacion_idEvaluacion());

        // Pasamos de Entity a DTO y lo devolvemos
        return evaluacionItemMapper.toDto(updatedEvaluacionItem);
    }

    // DELETE
    // Metodo para eliminar un EvaluacionItem por su ID
    public void delete(Integer id) {
        evaluacionItemRepository.deleteById(id);
    }

    // FIND EVALUACION ITEMS BY EVALUACION ID
    // Metodo para buscar todos los items de una evaluacion
    public List<EvaluacionItemDTO> findByEvaluacionId(Integer idEvaluacion) {
        List<EvaluacionItem> items = evaluacionItemRepository.findByEvaluacionIdEvaluacion(idEvaluacion);
        return evaluacionItemMapper.toDtoList(items);
    }
}