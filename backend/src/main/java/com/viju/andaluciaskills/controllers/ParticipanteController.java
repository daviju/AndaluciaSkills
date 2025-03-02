package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import com.viju.andaluciaskills.services.ParticipanteService;
import com.viju.andaluciaskills.DTO.ParticipanteDTO;
import com.viju.andaluciaskills.exceptions.participante.*;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/participantes")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Tag(name = "Participantes", description = "API para gestionar los participantes de la plataforma")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;


    // BUSCAR TODOS LOS PARTICIPANTES
    @Operation(summary = "Buscar todos los participantes", description = "Busca todos los participantes de la base de datos")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Participantes encontrados con éxtito"),
        @ApiResponse(responseCode = "404", description = "Participantes no encontrados")
    })

    @GetMapping("/buscarParticipantes")
    public ResponseEntity<List<ParticipanteDTO>> buscarParticipantes() {
        
        List<ParticipanteDTO> participantes = participanteService.findAll();

        if (participantes.isEmpty()) {
            throw new SearchParticipanteNoResultException();
        }

        return ResponseEntity.ok(participantes);
    }


    // BUSCAR PARTICIPANTE POR ID
    @Operation(summary = "Buscar un participante", description = "Busca un participante mediante un ID proporcionado")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Participante encontrado con éxtito"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })

    @GetMapping("/buscarParticipante/{id}")
    public ResponseEntity<ParticipanteDTO> buscarParticipante(@PathVariable Integer id) {
        
        return participanteService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    // CREAR PARTICIPANTE
    @Operation(summary = "Crear un participante", description = "Crea un participante con los datos proporcionados")

    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Participante creado con éxtito",
            content = @Content(schema = @Schema(implementation = ParticipanteDTO.class))
        ),
        @ApiResponse(responseCode = "400", description = "Participante no creado")
    })

    @PostMapping("/crearParticipante")
    public ResponseEntity<ParticipanteDTO> crearParticipante(@Valid @RequestBody ParticipanteDTO participanteDTO) {
        
        if (participanteDTO.getIdParticipante() != null) {
            throw new ParticipanteBadRequestException("No se puede crear un participante con un ID ya existente");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(participanteService.save(participanteDTO));
    }


    // MODIFICAR PARTICIPANTE
    @Operation(summary = "Modificar un participante", description = "Modifica un participante con los datos proporcionados")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Participante modificado con éxtito"),
        @ApiResponse(responseCode = "400", description = "Participante no modificado"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })

    @PutMapping("/modificarParticipante/{id}")
    public ResponseEntity<ParticipanteDTO> modificarParticipante(@PathVariable Integer id, @Valid @RequestBody ParticipanteDTO participanteDTO) {
        
        if(participanteDTO.getIdParticipante() != null && !participanteDTO.getIdParticipante().equals(id)) { // Si el ID proporcionado es distinto al ID del participante
            throw new ParticipanteBadRequestException("El ID del participante no coincide con el ID proporcionado");
        }

        participanteDTO.setIdParticipante(id);

        ParticipanteDTO participanteActualizado = participanteService.update(participanteDTO);

        return ResponseEntity.ok(participanteActualizado);
    }


    // ELIMINAR PARTICIPANTE
    @Operation(summary = "Eliminar un participante", description = "Elimina un participante existente con el ID proporcionado")

    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Participante eliminado con éxtito"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })

    @DeleteMapping("/eliminarParticipante/{id}")
    public ResponseEntity<?> eliminarParticipante(@PathVariable Integer id) {
        
        participanteService.findById(id).orElseThrow(() -> new ParticipanteNotFoundException(id));

        participanteService.delete(id);
        return ResponseEntity.noContent().build();
    }


    // BUSCAR PARTICIPANTES POR ESPECIALIDAD
    @Operation(summary = "Buscar participantes por especialidad", description = "Busca los participantes de una especialidad mediante un ID proporcionado")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Participantes encontrados con éxtito"),
        @ApiResponse(responseCode = "404", description = "Participantes no encontrados")
    })

    @GetMapping("/buscarParticipantesPorEspecialidad/{idEspecialidad}")
    public ResponseEntity<List<ParticipanteDTO>> buscarParticipantesPorEspecialidad(@PathVariable Integer idEspecialidad) {
        
        List<ParticipanteDTO> participantes = participanteService.findByEspecialidad(idEspecialidad);

        if (participantes.isEmpty()) {
            throw new SearchParticipanteNoResultException();
        }

        return ResponseEntity.ok(participantes);
    }
}
