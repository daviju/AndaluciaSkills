package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.*;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    // Verificamos si existe una evaluación en la BD para una determinada prueba y un participante
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Evaluacion e " +
           "WHERE e.prueba_idPrueba = :pruebaId AND e.participante_idParticipante = :participanteId")
    boolean existsByPruebaIdAndParticipanteId(@Param("pruebaId") Integer prueba_idPrueba, 
                                             @Param("participanteId") Integer participante_idParticipante);



    /* 
    Obtiene la lista de ganadores por especialidad
    Muestra: 
            - Nombre de la especialidad (esp.nombre)
            - Nombre y apellidos del ganador  (CONCAT(p.nombre, ' ', p.apellidos))
            - Nota media de ese participante en las evaluaciones (AVG(e.notaFinal))

    Se usa map para que devuelva cada fila como un Map<String, Object>, en lugar de un objeto de una entidad
    */
    @Query("SELECT NEW map(" +
           "esp.nombre as especialidad, " +
           "CONCAT(p.nombre, ' ', p.apellidos) as nombreGanador, " +
           "AVG(e.notaFinal) as notaMedia) " +
           "FROM Evaluacion e " +
           "JOIN Prueba pr ON e.prueba_idPrueba = pr.idPrueba " +
           "JOIN Especialidad esp ON pr.especialidad_idEspecialidad = esp.idEspecialidad " +
           "JOIN Participante p ON e.participante_idParticipante = p.idParticipante " +
           "GROUP BY esp.nombre, p.nombre, p.apellidos " +
           "ORDER BY esp.nombre, AVG(e.notaFinal) DESC")

    List<Map<String, Object>> findGanadoresPorEspecialidad();
}

