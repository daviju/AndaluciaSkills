package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.*;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

       // Verificamos si existe una evaluación para una prueba y un participante
       @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Evaluacion e " +
                     "WHERE e.prueba_idPrueba = :pruebaId AND e.participante_idParticipante = :participanteId")

       boolean existsByPruebaIdAndParticipanteId(@Param("pruebaId") Integer prueba_idPrueba,
                     @Param("participanteId") Integer participante_idParticipante);



       /*
        * Obtiene la lista de ganadores por especialidad
        * Muestra:
        * - Nombre de la especialidad (esp.nombre)
        * - Nombre y apellidos del ganador (CONCAT(p.nombre, ' ', p.apellidos))
        * - Nota media de ese participante en las evaluaciones (AVG(e.notaFinal))
        * 
        * Se usa map para que devuelva cada fila como un Map<String, Object>, en lugar de un objeto de una entidad.
        * Esto nos permite:
        * 1. Flexibilidad: Puedes mezclar datos de distintas tablas fácilmente.
        * 2. Proyección personalizada: Seleccionar solo los campos específicos que necesitamos
        * 3. No necesitas crear una clase nueva: No tienes que definir una clase específica solo para este resultado.
        * 
        * Esta consulta agrega los resultados por especialidad y participante, y ordena primero por especialidad y luego por nota media descendente, lo que coloca al ganador (mayor nota)
        * primero dentro de cada especialidad.
        * 

        * Es como crear una tabla "al vuelo" donde tú decides qué columnas quieres, sin tener que definir previamente una estructura fija.
        * Cada fila de resultados se convierte en un Map, y tienes una lista de esos Maps.
        */
       @Query("SELECT NEW map(" +
                     "esp.nombre as especialidad, " + // Alias para el nombre de la especialidad
                     "CONCAT(p.nombre, ' ', p.apellidos) as nombreGanador, " + // Nombre y apellidos del ganador
                     "AVG(e.notaFinal) as notaMedia) " + // Nota media del ganador
                     "FROM Evaluacion e " + // Tabla de evaluaciones (para obtener la nota)
                     "JOIN Prueba pr ON e.prueba_idPrueba = pr.idPrueba " + // Tabla de pruebas (para obtener la especialidad)
                     "JOIN Especialidad esp ON pr.especialidad_idEspecialidad = esp.idEspecialidad " + // Tabla de especialidades (para obtener el nombre)
                     "JOIN Participante p ON e.participante_idParticipante = p.idParticipante " + // Tabla de participantes (para obtener el nombre y apellidos)
                     "GROUP BY esp.nombre, p.nombre, p.apellidos " + // Agrupar por especialidad y participante
                     "ORDER BY esp.nombre, AVG(e.notaFinal) DESC") // Ordenar por especialidad y nota media descendente

       List<Map<String, Object>> findGanadoresPorEspecialidad(); // Método para ejecutar la consulta
}
