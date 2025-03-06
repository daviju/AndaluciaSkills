package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

// No necesitamos ningun metodo extra para este repositorio
public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {
    
}
