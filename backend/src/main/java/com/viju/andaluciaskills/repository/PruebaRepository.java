package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PruebaRepository extends JpaRepository<Prueba, Integer> {

    // Encontrar todas las pruebas que pertenezcan a una especialidad

    // Busca todas las pruebas que pertenezcan a una especialidad específica (especialidadId)
    @Query("SELECT p FROM Prueba p WHERE p.especialidad_idEspecialidad = :especialidadId")
    List<Prueba> findByEspecialidadId(@Param("especialidadId") Integer especialidadId);
}
