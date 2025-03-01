package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.ItemDTO;
import com.viju.andaluciaskills.entity.Item;
import com.viju.andaluciaskills.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

import com.viju.andaluciaskills.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Indicamos que esta clase es un servicio de SpringBoot
@RequiredArgsConstructor // Lombok genera un constructor con los atributos de la clase
public class ItemService {

    // Inyectamos las dependencias necesarias
    @Autowired
    private ItemRepository itemRepository; // Repositorio de Item (operaciones en BD)
    
    @Autowired
    private ItemMapper itemMapper; // Mapper de Item (mapeo de Entity a DTO y viceversa)


    // SAVE
    // Metodo para guardar un item
    public ItemDTO save(ItemDTO dto) {
        // Pasamos de DTO a Entity
        Item item = itemMapper.toEntity(dto);

        // Guardamos en la base de datos
        Item savedItem = itemRepository.save(item);

        // Pasamos de Entity a DTO y lo devolvemos
        return itemMapper.toDto(savedItem);
    }

    // FIND BY ID
    // Metodo para buscar un item por su ID
    public Optional<ItemDTO> findById(Integer id) {
        // Buscamos en la base de datos por ID y mapeamos a DTO
        return itemRepository.findById(id)
                .map(itemMapper::toDto);
    }

    // FIND ALL
    // Metodo para buscar todos los items
    public List<ItemDTO> findAll() {
        // Buscamos en la base de datos y mapeamos a DTO todos los items
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }


    // UPDATE
    // Metodo para actualizar un item por su ID
    public ItemDTO update(ItemDTO dto) {
        // Convertimos el DTO a Entity
        Item item = itemMapper.toEntity(dto);

        // Actualizamos el item en la base de datos
        Item updatedItem = itemRepository.save(item);

        // Devolvemos el item actualizado en formato DTO
        return itemMapper.toDto(updatedItem);
    }

    // DELETE
    // Metodo para eliminar un item por su ID
    public void delete(Integer id) {
        itemRepository.deleteById(id);
    }


    // SAVE ALL
    // Metodo para guardar todos los items a la vez
    public List<ItemDTO> saveAll(List<ItemDTO> items) {
        try {
            // Convertimos la lista de DTOs a entidades usando Stream
            List<Item> entities = items.stream()
                .map(itemMapper::toEntity)
                .collect(Collectors.toList());
            
            // Guardamos todas las entidades en la base de datos
            List<Item> savedItems = itemRepository.saveAll(entities);
            
            // Convertimos las entidades guardadas de vuelta a DTOs
            return savedItems.stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            // Manejo de errores con logging
            System.err.println("Error en ItemService.saveAll: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // FIND BY PRUEBA ID
    // Método para encontrar items por ID de prueba
    public List<ItemDTO> findByPruebaId(Integer pruebaId) {
        // Buscamos en la base de datos por ID de prueba y mapeamos a DTO
        return itemRepository.findByPruebaIdPrueba(pruebaId)
            .stream()
            .map(item -> {
                ItemDTO dto = itemMapper.toDto(item);
                dto.setPuntuacionMaxima(10.0); // Valor por defecto para puntuación máxima
                return dto;
            })
            .collect(Collectors.toList()); // Devolvemos la lista de DTOs
    }
}