package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    // Consulta todos los Item cuya prueba_id_prueba coincida con un valor dado    
    @Query(value = "SELECT * FROM items WHERE prueba_id_prueba = :pruebaId", nativeQuery = true)
    List<Item> findByPruebaIdPrueba(@Param("pruebaId") Integer pruebaId);
}
