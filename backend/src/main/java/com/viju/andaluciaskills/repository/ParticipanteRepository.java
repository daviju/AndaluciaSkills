package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
    List<Participante> findByEspecialidadIdEspecialidad(Integer especialidadId);
}

