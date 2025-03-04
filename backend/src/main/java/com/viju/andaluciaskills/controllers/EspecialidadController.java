package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.viju.andaluciaskills.services.EspecialidadService;
import com.viju.andaluciaskills.DTO.EspecialidadDTO;
import com.viju.andaluciaskills.exceptions.especialidad.*;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
@Tag(name = "Especialidades", description = "API para gestionar las Especialidades de AndaluciaSkills")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;


    // OBTENER ESPECIALIDADES
    @Operation(summary = "Obtener todas las especialidades", description = "Devuelve una lista con todas las especialidades")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida con éxito"),
            @ApiResponse(responseCode = "404", description = "No se encontraron especialidades")
    })

    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> obtenerEspecialidades() {
        List<EspecialidadDTO> especialidades = especialidadService.findAll();

        if (especialidades.isEmpty()) {
            throw new SearchEspecialidadNotFoundException();
        }

        return ResponseEntity.ok(especialidades);
    }


    // BUSCAR ESPECIALIDAD POR ID
    @Operation(summary = "Buscar una especialidad por su ID", description = "Busca una especialidad especifica mediante un ID proporcioando")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidad encontrada con éxtito"),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })

    @GetMapping("/buscarespecialidad/{id}")
    public ResponseEntity<EspecialidadDTO> getEspecialidadByID(@PathVariable Integer id) {
        return ResponseEntity.ok(especialidadService.findById(id)
                .orElseThrow(() -> new EspecialidadNotFoundException()));
    }


    // CREAR ESPECIALIDAD
    @Operation(summary = "Crear una nueva especialidad", description = "Crea una nueva especialidad con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Especialidad creada con éxito",
            content = @Content(schema = @Schema(implementation = EspecialidadDTO.class))
        ),
        
        @ApiResponse(responseCode = "400", description = "Datos de la especialidad inválidos")   
    })

    @PostMapping("/crearespecialidad")
    public ResponseEntity<EspecialidadDTO> crearEspecialidad(@Valid @RequestBody EspecialidadDTO especialidadDTO) {
        
        if (especialidadDTO.getIdEspecialidad() != null) {
            throw new EspecialidadBadRequestException();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadService.save(especialidadDTO));
    }


    // MODIFICAR ESPECIALIDAD EXISTENTE
    @Operation(summary = "Modificar una especialidad existente", description = "Modifica una especialidad existente con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad modificada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos de especialidad inválidos"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })

    @PutMapping("/modificarespecialidad/{id}")
    public ResponseEntity<EspecialidadDTO> modificarEspecialidad(@PathVariable Integer id, @Valid @RequestBody EspecialidadDTO especialidadDTO) {
        
        if (!id.equals(especialidadDTO.getIdEspecialidad())) {
            throw new EspecialidadBadRequestException();
        }

        return ResponseEntity.ok(especialidadService.findById(id)
                .map(e -> {
                    especialidadDTO.setIdEspecialidad(id);
                    return especialidadService.save(especialidadDTO);
                })
                .orElseThrow(() -> new EspecialidadNotFoundException(id))
        );
    }


    // ELIMINAR ESPECIALIDAD
    @Operation(summary = "Eliminar una especialidad existente", description = "Elimina una especialidad existente con el ID proporcionado")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Especialidad eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    
    @DeleteMapping("/eliminarespecialidad/{id}")
    public ResponseEntity<?> borrarEspecialidad(@PathVariable Integer id) {
        
        especialidadService.findById(id).orElseThrow(() -> new EspecialidadNotFoundException(id));

        especialidadService.delete(id);

        return ResponseEntity.noContent().build();
        // noContent devuelve una respuesta 204 sin contenido, sirve para cuando quieres borrar algo
    }
}
