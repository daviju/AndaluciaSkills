package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.ItemDTO;
import com.viju.andaluciaskills.entity.Item;
import com.viju.andaluciaskills.repository.ItemRepository;
import com.viju.andaluciaskills.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ItemMapper itemMapper;

    public ItemDTO save(ItemDTO dto) {
        Item item = itemMapper.toEntity(dto);
        return itemMapper.toDto(itemRepository.save(item));
    }

    public Optional<ItemDTO> findById(Integer id) {
        return itemRepository.findById(id)
                .map(itemMapper::toDto);
    }

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO update(ItemDTO dto) {
        Item item = itemMapper.toEntity(dto);
        return itemMapper.toDto(itemRepository.save(item));
    }

    public void delete(Integer id) {
        itemRepository.deleteById(id);
    }
}