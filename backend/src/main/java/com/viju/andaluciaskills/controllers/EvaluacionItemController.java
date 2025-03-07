package com.viju.andaluciaskills.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import com.viju.andaluciaskills.services.EvaluacionItemService;
import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.exceptions.evaluacionitem.EvaluacionItemBadRequestException;
import com.viju.andaluciaskills.exceptions.evaluacionitem.EvaluacionItemNotFoundException;
import com.viju.andaluciaskills.exceptions.evaluacionitem.SearchEvaluacionItemNoResultException;

@RestController
@RequestMapping("/api/evaluacionItem")
@Tag(name = "EvaluacionItem", description = "Api para gestionar los items de una evaluacion")
public class EvaluacionItemController {

    @Autowired
    private EvaluacionItemService evaluacionItemService;


    public EvaluacionItemController(EvaluacionItemService evaluacionItemService) {
        this.evaluacionItemService = evaluacionItemService;
    }

    // BUSCAR TODOS LOS ITEMS DE UNA EVALUACION
    @Operation(summary = "Buscar todos los items de una evaluacion", description = "Busca todos los items de una evaluacion mediante un ID proporcioando")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Items encontrados con éxtito"),
        @ApiResponse(responseCode = "404", description = "Items no encontrados")
    })

    @GetMapping
    public ResponseEntity<List<EvaluacionItemDTO>> buscarItems() {
        
        List<EvaluacionItemDTO> items = evaluacionItemService.findAll();

        if (items.isEmpty()) {
            throw new SearchEvaluacionItemNoResultException();
        }

        return ResponseEntity.ok(items);
    }


    // BUSCAR ITEM POR ID
    @Operation(summary = "Buscar un item por su ID", description = "Busca un item especifica mediante un ID proporcioando")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item encontrado con éxtito"),
        @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })

    @GetMapping("/buscarEvaluacionItem/{id}")
    public ResponseEntity<EvaluacionItemDTO> buscarItem(@PathVariable Integer id) {
        return ResponseEntity.ok(
            evaluacionItemService.findById(id)
                .orElseThrow(() -> new EvaluacionItemNotFoundException())
        );
    }


    // CREAR ITEM
    @Operation(summary = "Crear un item de Evaluación", description = "Crea un item con los datos proporcionados")

    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Item creado con éxtito",
            content = @Content(schema = @Schema(implementation = EvaluacionItemDTO.class))
        ),
        @ApiResponse(responseCode = "400", description = "Datos del item inválidos")
    })

    @PostMapping("/crearEvaluacionItem")
    public ResponseEntity<EvaluacionItemDTO> crearItem(@RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        try {
            
            if (evaluacionItemDTO.getEvaluacion_idEvaluacion() == null) {
                throw new IllegalArgumentException("El ID de evaluación no puede ser null");
            }

            if (evaluacionItemDTO.getItem_idItem() == null) {
                throw new IllegalArgumentException("El ID de evaluación no puede ser null");
            }

            if (evaluacionItemDTO.getPrueba_id_prueba() == null) {
                throw new IllegalArgumentException("El ID de evaluación no puede ser null");
            }

            if (evaluacionItemDTO.getValoracion() == null) {
                throw new IllegalArgumentException("El ID de evaluación no puede ser null");
            }

            EvaluacionItemDTO item = evaluacionItemService.save(evaluacionItemDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(item);

        } catch (Exception e) {
            System.err.println("Error guardando item: " + e.getMessage());
            e.printStackTrace();

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al crear el item: " + e.getMessage());
        }
    }


    // CREAR MULTIPLES ITEMS
    @Operation(summary = "Crear multiples items de Evaluación", description = "Crea multiples items con los datos proporcionados")

    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Items creados con éxtito",
            content = @Content(schema = @Schema(implementation = EvaluacionItemDTO.class))
        ),
        @ApiResponse(responseCode = "400", description = "Datos de los items inválidos")
    })

    @PostMapping("/crearEvaluacionItems")
    public ResponseEntity<List<EvaluacionItemDTO>> crearMultiplesItems(@RequestBody List<EvaluacionItemDTO> itemsDTOS) {

        try {
            // Validar cada item
            for (EvaluacionItemDTO itemDTO : itemsDTOS) {
                if (itemDTO.getEvaluacion_idEvaluacion() == null) {
                    throw new IllegalArgumentException("El ID de evaluación no puede ser null");
                }

                if (itemDTO.getItem_idItem() == null) {
                    throw new IllegalArgumentException("El ID de evaluación no puede ser null");
                }

                if (itemDTO.getPrueba_id_prueba() == null) {
                    throw new IllegalArgumentException("El ID de evaluación no puede ser null");
                }

                if (itemDTO.getValoracion() == null) {
                    throw new IllegalArgumentException("El ID de evaluación no puede ser null");
                }
            }

            List<EvaluacionItemDTO> item = evaluacionItemService.saveAll(itemsDTOS);

            return ResponseEntity.status(HttpStatus.CREATED).body(item);

        } catch (Exception e) {
            System.err.println("Error guardando items: " + e.getMessage());
            e.printStackTrace();

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al crear los items: " + e.getMessage());
        }
    }


    // MODIFICAR ITEM EVALUACION
    @Operation(summary = "Modificar un item de evaluación existente", description = "Modifica un item existente con los datos proporcionados")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item modificado con éxtito"),
        @ApiResponse(responseCode = "400", description = "Datos del item inválidos"),
        @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })

    @PutMapping("/modificarEvaluacionItem/{id}")
    public ResponseEntity<EvaluacionItemDTO> modificarEvaluacionItem(@PathVariable Integer id, @RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        
        try {
            if (!id.equals(evaluacionItemDTO.getIdEvaluacionItem())) {
                throw new EvaluacionItemBadRequestException("El ID del item no coincide con el ID proporcionado");
            }
        
            return ResponseEntity.ok(
                evaluacionItemService.findById(id)
                    .map(e -> {
        
                        try {
                            evaluacionItemDTO.setIdEvaluacionItem(id);
                            return evaluacionItemService.save(evaluacionItemDTO);
        
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .orElseThrow(() -> new EvaluacionItemNotFoundException(id))
            );
        
        } catch (Exception e) {
            System.err.println("Error modificando item: " + e.getMessage());
        
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al modificar el item: " + e.getMessage());
        }
    }


    // ELIMINAR ITEM EVALUACION
    @Operation(summary = "Eliminar un item de evaluación existente", description = "Elimina un item existente con el ID proporcionado")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item eliminado con éxtito"),
        @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })

    @DeleteMapping("/eliminarEvaluacionItem/{id}")
    public ResponseEntity<?> eliminarEvaluacionItem(@PathVariable Integer id) {
        
        evaluacionItemService.findById(id).orElseThrow(() -> new EvaluacionItemNotFoundException(id));

        evaluacionItemService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

