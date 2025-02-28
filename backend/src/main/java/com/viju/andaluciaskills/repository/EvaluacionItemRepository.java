package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluacionItemRepository extends JpaRepository<EvaluacionItem, Integer> {
    // Consulta todos los EvaluacionItem cuyo evaluacion_idEvaluacion coincida con un valor dado    
    @Query("SELECT evit FROM EvaluacionItem evit WHERE evit.evaluacion_idEvaluacion = :evaluacionId")
    List<EvaluacionItem> findByEvaluacionIdEvaluacion(@Param("evaluacionId") Integer evaluacionId);
}

