package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.ItemDTO;
import com.viju.andaluciaskills.entity.Item;

import org.springframework.stereotype.Component;

@Component
public class ItemMapper implements GenericMapper<Item, ItemDTO> {

    @Override
    public ItemDTO toDto(Item entity) {
        if (entity == null) return null;

        ItemDTO dto = new ItemDTO();
        dto.setIdItem(entity.getIdItem());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPeso(entity.getPeso());
        dto.setGrados_consecucion(entity.getGradosConsecucion());
        dto.setPrueba_id_prueba(entity.getPruebaIdPrueba());
        
        return dto;
    }

    @Override
    public Item toEntity(ItemDTO dto) {
        if (dto == null) return null;

        Item entity = new Item();
        entity.setIdItem(dto.getIdItem());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPeso(dto.getPeso());
        entity.setGradosConsecucion(dto.getGrados_consecucion());
        entity.setPruebaIdPrueba(dto.getPrueba_id_prueba());

        return entity;
    }
}
