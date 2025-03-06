package com.viju.andaluciaskills.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/*
 * Usamos los MAPPERS para convertir objetos de un tipo a otro, por ejemplo, de una entidad a un DTO y viceversa.
 
 * Así facilitamos la conversión entre objetos
 * Mejoramos la organización del código
 * Facilitamos la reutilización de código
 * No exponemos entidades en los controllers
 * 
 * 
 * (Por lo que he visto hay una Librería que los genera automáticamente -> MapStruct)
 */

@Component
public interface GenericMapper<E, D> {

    /**
     * Convierte una entidad a DTO
     * @param entity la entidad a convertir
     * @return el DTO resultante
     */
    D toDto(E entity);

    /**
     * Convierte un DTO a entidad
     * @param dto el DTO a convertir
     * @return la entidad resultante
     */
    E toEntity(D dto);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param entities lista de entidades
     * @return lista de DTOs
     */
    default List<D> toDtoList(List<E> entities) {
        if (entities == null) return null;
        
        return entities.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de DTOs a lista de entidades
     * @param dtos lista de DTOs
     * @return lista de entidades
     */
    default List<E> toEntityList(List<D> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
}
