package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.*;
import com.viju.andaluciaskills.entity.*;
import com.viju.andaluciaskills.repository.*;
import com.viju.andaluciaskills.mapper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Indicamos que esta clase es un servicio de SpringBoot
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository; // Repositorio de la entidad Evaluacion (operaciones en BD)
    
    @Autowired
    private EvaluacionMapper evaluacionMapper; // Mapper de la entidad Evaluacion (Convertir entre Entity y DTO)

    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository; // Repositorio de la entidad EvaluacionItem (items de evaluacion)

    @Autowired
    private ItemService itemService; // Servicio de la entidad Item (informacion items)


    public Double calcNotaFinal(Integer idEvaluacion) {
        List<EvaluacionItem> items = evaluacionItemRepository.findByEvaluacionIdEvaluacion(idEvaluacion);

        if (items.isEmpty()) {
            return 0.0;
        }

        double notaFinal = 0.0;
        double sumaDePesos = 0.0;

        for (EvaluacionItem evaluacionItem : items) {
            if (evaluacionItem.getValoracion() != null) {
                Optional<ItemDTO> item = itemService.findById(evaluacionItem.getItem_idItem());

                if (item.isPresent()) {
                    Double puntuacionMax = item.get().getPuntuacionMaxima();
                    Double valoracion = evaluacionItem.getValoracion();
                    Integer peso = item.get().getPeso();

                    double nota = (valoracion / puntuacionMax) * peso;
                    notaFinal += nota;
                    sumaDePesos += peso;

                    // Quitar
                    System.out.println("Item: " + item.get().getDescripcion());
                    System.out.println("Peso: " + peso + "%");
                    System.out.println("Puntuación máxima: " + puntuacionMax);
                    System.out.println("Valoración: " + valoracion);
                    System.out.println("Nota ponderada: " + nota);
                }
            }
        }

        if (sumaDePesos > 0) {
            notaFinal = (notaFinal / sumaDePesos) * 100;
        }

        return Math.round(notaFinal * 100.0) / 100.0;
    }


    // ACTUALIZAR NOTA FINAL
    public void actualizarNotaFinal (Integer idEvaluacion) {
        Double notaFinal = calcNotaFinal(idEvaluacion);

        evaluacionRepository.findById(idEvaluacion).ifPresent(evaluacion -> {
            evaluacion.setNotaFinal(notaFinal);
            evaluacionRepository.save(evaluacion);
        });
    }

    // SAVE
    // Metodo para guardar una evaluacion
    public EvaluacionDTO save(EvaluacionDTO dto) {
        try {
            // Convertimos de DTO a Entity
            Evaluacion ev = evaluacionMapper.toEntity(dto);
            
            // Guardamos la evaluacion en la BD
            Evaluacion savedEv = evaluacionRepository.save(ev);

            // Calculamos y actualizamos la nota final
            Double notaFinal = calcNotaFinal(savedEv.getIdEvaluacion());
            savedEv.setNotaFinal(notaFinal);

            // Guardamos la evaluacion con la nota final actualizada
            savedEv = evaluacionRepository.save(savedEv);

            // Convertimos de Entity a DTO y lo devolvemos 
            return evaluacionMapper.toDto(savedEv);

        } catch (Exception e) {
            System.err.println("Error guardando evaluacion: " + e.getMessage());
            throw e;
        }
    }

    // FIND BY ID
    // Metodo para buscar una evaluacion por su ID
    public Optional<EvaluacionDTO> findById(Integer id) {
        return evaluacionRepository.findById(id)
                .map(evaluacionMapper::toDto);
    }


    // FIND ALL
    // Metodo para buscar todas las evaluaciones
    public List<EvaluacionDTO> findAll() {
        return evaluacionRepository.findAll().stream()
                .map(evaluacionMapper::toDto)
                .collect(Collectors.toList());
    }


    // UPDATE
    // Metodo para actualizar una evaluacion
    public EvaluacionDTO update(EvaluacionDTO dto) {
        if (dto.getIdEvaluacion() == null) {
            throw new IllegalArgumentException("No se puede actualizar una evaluación sin ID");
        }
        
        Evaluacion evaluacion = evaluacionMapper.toEntity(dto);
        Evaluacion updatedEvaluacion = evaluacionRepository.save(evaluacion);
        
        // Recalcular nota final después de la actualización
        actualizarNotaFinal(updatedEvaluacion.getIdEvaluacion());
        
        return evaluacionMapper.toDto(updatedEvaluacion);
    }


    // DELETE
    // Metodo para eliminar una evaluacion por su ID
    public void delete(Integer id) {
        evaluacionRepository.deleteById(id);
    }



    // CREAR EVALUACION
    public EvaluacionDTO crearEvaluacion(Integer idPrueba, Integer idParticipante, Integer idUser, Double notaFinal) {
        try {
            // Creamos una nueva instancia de Evaluacion
            Evaluacion evaluacion = new Evaluacion();
            
            // Asignamos los datos proporcionados a la entidad Evaluacion
            evaluacion.setPrueba_idPrueba(idPrueba);
            evaluacion.setParticipante_idParticipante(idParticipante);
            evaluacion.setUser_idUser(idUser);
            evaluacion.setNotaFinal(notaFinal);
            
            // Guardamos la evaluación en la base de datos
            Evaluacion evaluacionGuardada = evaluacionRepository.save(evaluacion);
    
            // Verificamos si se obtuvo un ID después de guardar la evaluación
            if (evaluacionGuardada.getIdEvaluacion() == null) {
                throw new RuntimeException("No se pudo obtener el ID de la evaluación guardada");
            }
    
            // Comprobamos si la evaluación realmente se encuentra en la base de datos
            Optional<Evaluacion> verificacion = evaluacionRepository.findById(evaluacionGuardada.getIdEvaluacion());
            if (!verificacion.isPresent()) {
                throw new RuntimeException("La evaluación no se encontró después de guardarla");
            }
    
            // Convertimos la entidad Evaluacion en un objeto DTO
            EvaluacionDTO dto = evaluacionMapper.toDto(evaluacionGuardada);
            
            // Retornamos el objeto DTO con los datos guardados
            return dto;
    
        } catch (Exception e) {
            // Registramos el error en la consola con su mensaje y traza de pila
            System.err.println("Error en crearEvaluacion: " + e.getMessage());
            e.printStackTrace();
    
            // Relanzamps la excepción con un mensaje descriptivo y la causa original
            throw new RuntimeException("Error al crear la evaluación", e);
        }
    }


    // ACTUALIZAR NOTA FINAL
    // Metodo para actualizar la nota final de una evaluación existente
    public EvaluacionDTO actualizarNotaFinal(Integer idEvaluacion, Double notaFinal) {
        try {
            // Buscamos la evaluación en la base de datos por su ID
            Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con ID: " + idEvaluacion));
            
            // Actualizamos la nota final de la evaluación
            evaluacion.setNotaFinal(notaFinal);

            // Guardamos la evaluación actualizada en la base de datos
            Evaluacion evaluacionActualizada = evaluacionRepository.save(evaluacion);
            
            // Convertimos la entidad Evaluacion en un objeto DTO
            return evaluacionMapper.toDto(evaluacionActualizada);

        } catch (Exception e) {
            System.err.println("Error al actualizar nota final: " + e.getMessage());
            throw new RuntimeException("Error al actualizar la nota final", e);
        }
    }


    // ACTUALIZAR NOTA FINAL AUTOMÁTICA
    // Metodo para calcular y actualizar la nota final de una evaluación automáticamente
    public EvaluacionDTO actualizarNotaFinalAutomatica(Integer idEvaluacion) {
        try {
            // Buscamos la evaluación en la base de datos por su ID
            Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

            // Obtenemos todos los items de la evaluación
            List<EvaluacionItem> evaluacionItems = evaluacionItemRepository.findByEvaluacionIdEvaluacion(idEvaluacion);
            
            // Verificamos si hay items para la evaluación
            if (evaluacionItems.isEmpty()) {
                throw new RuntimeException("No se encontraron items para la evaluación");
            }

            // Inicializamos las variables a 0
            double sumaPonderada = 0.0;
            double sumaPesos = 0.0;

            // Iteramos sobre cada item de la evaluación
            for (EvaluacionItem evaluacionItem : evaluacionItems) {
                
                // Obtenemos el Item usando ItemService
                Optional<ItemDTO> itemOpt = itemService.findById(evaluacionItem.getItem_idItem());
                
                // Verificamos si el item existe
                if (itemOpt.isPresent()) {
                    
                    // Obtenemos el peso y la valoración del item
                    ItemDTO item = itemOpt.get();
                    double valoracion = evaluacionItem.getValoracion();
                    double pesoItem = item.getPeso(); // El peso es el porcentaje (ej: 50 significa 50%)
                    
                    // Si el peso es 50%, el máximo que puede poner es 5
                    // Convertimos la valoración a una escala sobre 10
                    double valoracionSobre10 = (valoracion * 10) / (pesoItem / 10);
                    
                    // Sumamos la valoración ponderada
                    sumaPonderada += (valoracionSobre10 * pesoItem);
                    
                    // Sumamos el peso
                    sumaPesos += pesoItem;
                }
            }

            // Calculaomos la nota final sobre 10 puntos
            double notaFinal = (sumaPesos > 0) ? (sumaPonderada / sumaPesos) : 0.0;
            
            // Redondeamos a 2 decimales (0.00)
            notaFinal = Math.round(notaFinal * 100.0) / 100.0;
            
            // Guardamos la nota final
            evaluacion.setNotaFinal(notaFinal);

            // Guardamos la evaluación actualizada en la base de datos
            Evaluacion evaluacionActualizada = evaluacionRepository.save(evaluacion);
            
            // Convertimos la entidad Evaluacion en un objeto DTO
            return evaluacionMapper.toDto(evaluacionActualizada);
            
        } catch (Exception e) {
            System.err.println("Error al calcular nota final: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al calcular la nota final", e);
        }
    }


    // OBTENER GANADORES
    // Metodo para obtener los ganadores de cada especialidad (mejor nota)
    public List<Map<String, Object>> obtenerGanadores() {
        // Obtenemos todos los resultados de la base de datos
        List<Map<String, Object>> todosLosResultados = evaluacionRepository.findGanadoresPorEspecialidad(); //MAP -> Nombre especialidad + Nombre ganador + nota media
        
        // Creamos un mapa para almacenar solo el mejor de cada especialidad
        Map<String, Map<String, Object>> ganadoresPorEspecialidad = new HashMap<>(); //MAP -> nombre especialidad + datos del ganador
        
        // Procesamos cada resultado
        for (Map<String, Object> resultado : todosLosResultados) {
            // Obtenemos los datos del resultado
            String especialidad = (String) resultado.get("especialidad");
            
            // Convertimos la notaMedia a Double
            Double notaMedia = (Double) resultado.get("notaMedia");
            
            // Si no hay ganador para esta especialidad o este tiene mejor nota
            if (!ganadoresPorEspecialidad.containsKey(especialidad) || 
                // Comparamos las notas
                (Double) ganadoresPorEspecialidad.get(especialidad).get("notaMedia") < notaMedia) {
                
                // Guardamos el ganador de esta especialidad
                ganadoresPorEspecialidad.put(especialidad, resultado);
            }
        }
        
        // Devolvemos solo los ganadores en un ArrayList
        return new ArrayList<>(ganadoresPorEspecialidad.values());
    }
}