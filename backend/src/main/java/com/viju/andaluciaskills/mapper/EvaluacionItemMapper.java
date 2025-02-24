package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.entity.EvaluacionItem;
import com.viju.andaluciaskills.repository.EvaluacionRepository;
import com.viju.andaluciaskills.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionItemMapper implements GenericMapper<EvaluacionItem, EvaluacionItemDTO> {

    @Autowired
    private EvaluacionRepository evaluacionRepository;
    
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public EvaluacionItemDTO toDto(EvaluacionItem entity) {
        if (entity == null) return null;

        EvaluacionItemDTO dto = new EvaluacionItemDTO();
        dto.setIdEvaluacionItem(entity.getIdEvaluacionItem());
        dto.setValoracion(entity.getValoracion());
        
        if (entity.getEvaluacion() != null) {
            dto.setEvaluacion(entity.getEvaluacion().getIdEvaluacion());
        }
        
        if (entity.getItem() != null) {
            dto.setItem(entity.getItem().getIdItem());
        }
        
        return dto;
    }

    @Override
    public EvaluacionItem toEntity(EvaluacionItemDTO dto) {
        if (dto == null) return null;

        EvaluacionItem entity = new EvaluacionItem();
        entity.setIdEvaluacionItem(dto.getIdEvaluacionItem());
        entity.setValoracion(dto.getValoracion());

        if (dto.getEvaluacion() != null) {
            evaluacionRepository.findById(dto.getEvaluacion())
                .ifPresent(entity::setEvaluacion);
        }

        if (dto.getItem() != null) {
            itemRepository.findById(dto.getItem())
                .ifPresent(entity::setItem);
        }

        return entity;
    }
}
