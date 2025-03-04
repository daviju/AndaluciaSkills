package com.viju.andaluciaskills.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ContentDisposition;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import com.viju.andaluciaskills.services.EvaluacionService;
import com.viju.andaluciaskills.services.ItemService;
import com.viju.andaluciaskills.services.EvaluacionItemService;
import com.viju.andaluciaskills.services.PruebaService;
import com.viju.andaluciaskills.DTO.EvaluacionDTO;
import com.viju.andaluciaskills.DTO.EvaluacionItemDTO;
import com.viju.andaluciaskills.DTO.ItemDTO;
import com.viju.andaluciaskills.DTO.PruebaDTO;
import com.viju.andaluciaskills.exceptions.prueba.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/pruebas")
@RequiredArgsConstructor
@Tag(name = "Pruebas", description = "API para gestionar pruebas y sus evaluaciones")
public class PruebaController {

    @Autowired
    private PruebaService pruebaService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private EvaluacionItemService evaluacionItemService;


    // BUSCAR TODAS LAS PRUEBAS
    @Operation(summary = "Buscar todas las pruebas", description = "Devuelve una lista con todas las pruebas")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pruebas encontradas con éxtito"),
        @ApiResponse(responseCode = "404", description = "Pruebas no encontradas")
    })

    @GetMapping
    public ResponseEntity<List<PruebaDTO>> buscarPruebas() {

        return ResponseEntity.ok(pruebaService.findAll());
    }


    // BUSCAR PRUEBA POR ID
    @Operation(summary = "Buscar una prueba por su ID", description = "Busca una prueba mediante un ID proporcionado")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prueba encontrada con éxtito"),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada")
    })

    @GetMapping("/buscarPrueba/{id}")
    public ResponseEntity<PruebaDTO> buscarPrueba(@PathVariable Integer id) {

        return ResponseEntity.ok(pruebaService.findById(id)
            .orElseThrow(() -> new PruebaNotFoundException()));
    }


    // BUSCAR PRUEBAS POR ESPECIALIDAD
    @Operation(summary = "Buscar pruebas por especialidad", description = "Busca las pruebas de una especialidad mediante un ID proporcionado")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pruebas encontradas con éxtito"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })

    @GetMapping("/buscarPruebasPorEspecialidad/{idEspecialidad}")
    public ResponseEntity<List<PruebaDTO>> buscarPruebasPorEspecialidad(@PathVariable Integer idEspecialidad) {

        try {
            List<PruebaDTO> pruebas = pruebaService.findByEspecialidadId(idEspecialidad);

            return ResponseEntity.ok(pruebas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    // CREAR PRUEBA
    @Operation(summary = "Crear una nueva prueba", description = "Crea una nueva prueba con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Prueba creada con éxtito",
            content = @Content(schema = @Schema(implementation = PruebaDTO.class))
        ),
        @ApiResponse(responseCode = "500", description = "Error al crear la prueba")
    })

    @PostMapping("/crearPrueba")
    public ResponseEntity<PruebaDTO> crearPrueba(@RequestBody PruebaDTO pruebaDTO) {

        try {
            PruebaDTO prueba = pruebaService.save(pruebaDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(prueba);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // CREAR UNA PRUEBA CON ITEMS
    @Operation(summary = "Crear una nueva prueba con items", description = "Crea una nueva prueba con los items proporcionados")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prueba creada con éxtito"),
        @ApiResponse(responseCode = "500", description = "Error al crear la prueba")
    })

    @PostMapping("/crearPruebaConItems")
    public ResponseEntity<PruebaDTO> crearPruebaConItems(@RequestBody Map<String, Object> pruebaConItems) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            PruebaDTO pruebaDTO = mapper.convertValue(pruebaConItems.get("prueba"), PruebaDTO.class);

            List<ItemDTO> items = mapper.convertValue(
                pruebaConItems.get("items"),
                new TypeReference<List<ItemDTO>>() {}
            );
            
            PruebaDTO pruebaCreada = pruebaService.crearPruebaItem(pruebaDTO, items);

            return ResponseEntity.status(HttpStatus.CREATED).body(pruebaCreada);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // CREAR ITEMS DE UNA PRUEBA
    @Operation(summary = "Crear items de una prueba", description = "Crea items de una prueba con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Items creados con éxtito"),
        @ApiResponse(responseCode = "500", description = "Error al crear los items")
    })

    @PostMapping("/crearItems")
    public ResponseEntity<List<ItemDTO>> crearItems(@RequestBody List<ItemDTO> items) {
        try {
            List<ItemDTO> itemsCreados = itemService.saveAll(items);

            return ResponseEntity.status(HttpStatus.CREATED).body(itemsCreados);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // MODIFICAR PRUEBA
    @Operation(summary = "Modificar una prueba", description = "Modifica una prueba con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prueba modificada con éxtito"),
        @ApiResponse(responseCode = "400", description = "Datos de la prueba inválidos"),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada")
    })

    @PutMapping("/modificarPrueba/{id}")
    public ResponseEntity<PruebaDTO> modificarPrueba(@PathVariable Integer id, @RequestBody PruebaDTO pruebaDTO) {
        
        if(!id.equals(pruebaDTO.getIdPrueba())) {
            throw new PruebaBadRequestException("El ID de la prueba no coincide con el proporcionado");
        }

        return ResponseEntity.ok(
            pruebaService.findById(id)
                .map(e -> {
                    pruebaDTO.setIdPrueba(id);
                    return pruebaService.save(pruebaDTO);
                })
                .orElseThrow(() -> new PruebaNotFoundException(id))
        );
    }


    // ELIMINAR PRUEBA
    @Operation(summary = "Eliminar una prueba", description = "Elimina una prueba existente con el ID proporcionado")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prueba eliminada con éxtito"),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada")
    })

    @DeleteMapping("/eliminarPrueba/{id}")
    public ResponseEntity<PruebaDTO> eliminarPrueba(@PathVariable Integer id) {
        
        pruebaService.findById(id)
            .orElseThrow(() -> new PruebaNotFoundException(id));

        pruebaService.delete(id);
        return ResponseEntity.ok().build();
    }


    // ACTUALIZAR VALORACIONES DE UNA EVALUACION
    @Operation(summary = "Actualizar valoraciones de una evaluacion", description = "Actualiza las valoraciones de una evaluacion con los datos proporcionados")    

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Valoraciones actualizadas con éxtito"),
        @ApiResponse(responseCode = "500" , description = "Error al actualizar las valoraciones")
    })

    @PostMapping("/evaluaciones/{IdEvaluacion}/valoraciones")
    public ResponseEntity<EvaluacionDTO> actualizarValoraciones (@PathVariable Integer IdEvaluacion, @RequestBody List<EvaluacionItemDTO> evaluacionItemDTOs) {
        
        try {
            // Guardamos los items de la evaluacion
            for (EvaluacionItemDTO evaluacionItemDTO : evaluacionItemDTOs) {
                evaluacionItemService.save(evaluacionItemDTO);
            }

            // Actualizamos la nota final automaticamente gracias al metodo del service
            EvaluacionDTO evaluacionActualizada = evaluacionService.actualizarNotaFinalAutomatica(IdEvaluacion);

            return ResponseEntity.ok(evaluacionActualizada);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    // CREAR EVALUACION
    @Operation(summary = "Crear una nueva evaluacion", description = "Crea una nueva evaluacion con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evaluacion creada con éxtito"),
        @ApiResponse(responseCode = "400", description = "Ya existe una evaluacion para la prueba proporcionada"),
        @ApiResponse(responseCode = "500", description = "Error al crear la evaluacion")
    })

    @PostMapping("/evaluacion")
    public ResponseEntity<EvaluacionDTO> crearEvaluacion(@RequestBody Map<String, Object> evaluacion) {
        
        try {
            // Convertimos los valores a Integer
            Integer IdPrueba = ((Number) evaluacion.get("idPrueba")).intValue();
            Integer IdParticipante = ((Number) evaluacion.get("idParticipante")).intValue();
            Integer IdUser = ((Number) evaluacion.get("idUser")).intValue();

            Double notaFinal = 0.0;

            // Verificamos si ya existe una evaluación para esta prueba y participante
            boolean existeEvaluacion = pruebaService.existeEvaluacion(IdPrueba, IdParticipante);

            if (existeEvaluacion) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una evaluacion para la prueba proporcionada");
            }

            // Si no existe, creamos la evaluacion y la devolvemos
            EvaluacionDTO evaluacionCreada = evaluacionService.crearEvaluacion(IdPrueba, IdParticipante, IdUser, notaFinal);
            return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionCreada);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al crear la evaluacion");
        }
    }


    // EXISTE EVALUACION?
    @Operation(summary = "Verificar si una evaluacion existe", description = "Verifica si una evaluacion existe con los datos proporcionados")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluacion encontrada con éxtito"),
        @ApiResponse(responseCode = "500", description = "Error al realizar la verificacion")
    })

    @GetMapping("/evaluacion/existe/{IdPrueba}/{IdParticipante}")
    public ResponseEntity<Boolean> existeEvaluacion(@PathVariable Integer IdPrueba, @PathVariable Integer IdParticipante) {
        
        try {

            boolean existeEvaluacion = pruebaService.existeEvaluacion(IdPrueba, IdParticipante);
            return ResponseEntity.ok(existeEvaluacion);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    
    // PDF PLANTILLA DE EVALUACION
    @Operation(summary = "Generar PDF con la plantilla de evaluacion", description = "Genera un PDF con la plantilla de evaluacion")

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "PDF generado con éxtito"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })

    @GetMapping("/plantillaevaluacion/{idPrueba}")
    public ResponseEntity<byte[]> generarPlantillaEvaluacion(@PathVariable Integer idPrueba) {
        
        try {
            byte[] plantilla = pruebaService.generarPlantillaEvaluacion(idPrueba);
            
            HttpHeaders headers = new HttpHeaders();
            
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("plantillaevaluacion.pdf").build());
            
            return new ResponseEntity<>(plantilla, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

