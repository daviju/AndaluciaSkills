package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.ItemDTO;
import com.viju.andaluciaskills.entity.Item;
import com.viju.andaluciaskills.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemDTO save(ItemDTO dto) {
        Item item = convertToEntity(dto);
        return convertToDTO(itemRepository.save(item));
    }

    public Optional<ItemDTO> findById(Integer id) {
        return itemRepository.findById(id).map(this::convertToDTO);
    }

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemDTO update(ItemDTO dto) {
        Item item = convertToEntity(dto);
        return convertToDTO(itemRepository.save(item));
    }

    public void delete(Integer id) {
        itemRepository.deleteById(id);
    }

}