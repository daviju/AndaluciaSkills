package com.viju.andaluciaskills.services.base;

import com.viju.andaluciaskills.DTO.ItemDTO;
import java.util.List;
import java.util.Optional;

public interface ItemBaseService {
    ItemDTO save(ItemDTO dto);
    Optional<ItemDTO> findById(Integer id);
    List<ItemDTO> findAll();
    ItemDTO update(ItemDTO dto);
    void delete(Integer id);
}