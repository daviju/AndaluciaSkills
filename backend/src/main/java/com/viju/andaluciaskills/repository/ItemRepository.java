package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    
}

