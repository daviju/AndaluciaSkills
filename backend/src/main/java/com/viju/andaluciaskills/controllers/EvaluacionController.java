package com.viju.andaluciaskills.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import com.viju.andaluciaskills.services.EvaluacionService;
import com.viju.andaluciaskills.DTO.EvaluacionDTO;
import com.viju.andaluciaskills.exceptions.evaluacion.*;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
@Tag(name = "Evaluaciones", description = "Api para gestionar las evaluaciones de los participantes")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    
    // OBTENER EVALUACIONES
    @Operation(summary = "Obtener todas las evaluaciones", description = "Devuelve una lista con todas las evaluaciones")
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron evaluaciones")
    })

    @GetMapping
    public ResponseEntity<List<EvaluacionDTO>> obtenerEvaluaciones() {
        List<EvaluacionDTO> evaluaciones = evaluacionService.findAll();

        if (evaluaciones.isEmpty()) {
            throw new SearchEvaluacionNotFoundException();
        }

        return ResponseEntity.ok(evaluaciones);
    }


    // BUSCAR EVALUACION POR ID
    @Operation(summary = "Buscar una evaluacion por su ID", description = "Busca una evaluacion especifica mediante un ID proporcioando")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluacion encontrada con éxtito"),
        @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada")
    })

    @GetMapping("/buscareevaluacion/{id}")
    public ResponseEntity<EvaluacionDTO> buscarEvaluacionPorID(@PathVariable Integer id) {
        
        return ResponseEntity.ok(
            evaluacionService.findById(id)
                .orElseThrow(() -> new EvaluacionNotFoundException(id))
        );
    }


    // CREAR EVALUACION
    @Operation(summary = "Crear una nueva evaluacion", description = "Crea una nueva evaluacion con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evaluacion creada con éxtito"),
        @ApiResponse(responseCode = "400", description = "Datos de la evaluacion inválidos")
    })

    @PostMapping("/crearevaluacion")
    public ResponseEntity<EvaluacionDTO> crearEvaluacion(@RequestBody EvaluacionDTO evaluacionDTO) {
        
        if (evaluacionDTO.getIdEvaluacion() != null) {
            throw new EvaluacionBadRequestException("No se puede crear una evaluacion con un ID ya existente");
        }

        try {
            EvaluacionDTO evaluacionCreada = evaluacionService.save(evaluacionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionCreada);

        } catch (Exception e) {
            throw new EvaluacionBadRequestException("Error al crear la evaluacion: " + e.getMessage());
        }
    }


    // MODIFICAR EVALUACION
    @Operation(summary = "Modificar una evaluacion existente", description = "Modifica una evaluacion existente con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluacion modificada con éxtito"),
        @ApiResponse(responseCode = "400", description = "Datos de la evaluacion inválidos"),
        @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada")
    })

    @PutMapping("/modificarevaluacion/{id}")
    public ResponseEntity<EvaluacionDTO> modificarEvaluacion(@PathVariable Integer id, @RequestBody EvaluacionDTO evaluacionDTO) {
        
        if (!id.equals(evaluacionDTO.getIdEvaluacion())) {
            throw new EvaluacionBadRequestException("El ID de la evaluacion no coincide con el proporcionado");
        }

        return ResponseEntity.ok(evaluacionService.findById(id)
                .map(e -> {
                    evaluacionDTO.setIdEvaluacion(id);
                    return evaluacionService.save(evaluacionDTO);
                })
                .orElseThrow(() -> new EvaluacionNotFoundException(id))
        );
    }


    // ELIMINAR EVALUACION
    @Operation(summary = "Eliminar una evaluacion existente", description = "Elimina una evaluacion existente con el ID proporcionado")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evaluacion eliminada con éxtito"),
        @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada")
    })

    @DeleteMapping("/eliminarevaluacion/{id}")
    public ResponseEntity<?> eliminarEvaluacion(@PathVariable Integer id) {
        evaluacionService.findById(id).orElseThrow(() -> new EvaluacionNotFoundException(id));

        evaluacionService.delete(id);
        return ResponseEntity.noContent().build();
    }


    // OBTENER GANADORES DE LA EVALUACION
    @Operation(summary = "Obtener los ganadores de una evaluacion", description = "Devuelve una lista con los ganadores de una evaluacion con sus puntuaciones")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ganadores obtenidos con éxtito"),
        @ApiResponse(responseCode = "204", description = "Ganadores no encontrados"),
        @ApiResponse(responseCode = "500", description = "Error al procesar la solicitud")
    })

    @GetMapping("/ganadores")
    public ResponseEntity<List<Map<String, Object>>> obtenerGanadores() {

        try {
            List<Map<String, Object>> ganadores = evaluacionService.obtenerGanadores();

            if (ganadores.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(ganadores);

        } catch (Exception e) {
            System.err.println("Error al obtener los ganadores: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(Map.of(
                        "error", "Error al obtener los ganadores",
                        "message", e.getMessage()
                    )));

            /*
             * Map.of(...) → Se usa para crear un objeto JSON con claves y valores.
             * List.of(...) → Se usa si se quiere devolver una lista de errores en lugar de un solo objeto.
             */
        }
    }
}

