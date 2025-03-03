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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;

import java.util.List;

import com.viju.andaluciaskills.services.ItemService;
import com.viju.andaluciaskills.DTO.ItemDTO;
import com.viju.andaluciaskills.exceptions.item.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Items", description = "API para gestionar los items de las pruebas")
public class ItemController {

    @Autowired
    private ItemService itemService;


    // CONSEGUIR TODOS LOS ITEMS
    @Operation(summary = "Buscar todos los items", description = "Busca todos los items de la base de datos")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Items encontrados con éxtito"),
        @ApiResponse(responseCode = "404", description = "Items no encontrados")
    })

    @GetMapping
    public ResponseEntity<List<ItemDTO>> buscarItems() {

        List<ItemDTO> items = itemService.findAll();

        if (items.isEmpty()) {
            throw new SearchItemNoResultException();
        }

        return ResponseEntity.ok(items);
    }


    // BUSCAR ITEM POR ID
    @Operation(summary = "Buscar un item por su ID", description = "Busca un item especifica mediante un ID proporcioando")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item encontrado con éxtito"),
        @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })

    @GetMapping("/buscarItem/{id}")
    public ResponseEntity<ItemDTO> buscarItem(@PathVariable Integer id) {

        return ResponseEntity.ok(
            itemService.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id))
        );
    }


    // CREAR ITEM
    @Operation(summary = "Crear un item", description = "Crea un item con los datos proporcionados")

    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Item creado con éxtito",
            content = @Content(schema = @Schema(implementation = ItemDTO.class))
        ),
        @ApiResponse(responseCode = "400", description = "Datos del item inválidos")
    })

    @PostMapping("/crearItem")
    public ResponseEntity<ItemDTO> crearItem(@RequestBody ItemDTO itemDTO) {
        
        if (itemDTO.getIdItem() != null) {
            throw new ItemBadRequestException("No se puede crear un item con un ID ya existente");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.save(itemDTO));
    }


    // MODIFICAR ITEM
    @Operation(summary = "Modificar un item", description = "Modifica un item con los datos proporcionados")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item modificado con éxtito"),
        @ApiResponse(responseCode = "400", description = "Datos del item inválidos"),
        @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })

    @PutMapping("/modificarItem/{id}")
    public ResponseEntity<ItemDTO> modificarItem(@PathVariable Integer id, @RequestBody ItemDTO itemDTO) {

        if (!id.equals(itemDTO.getIdItem())) {
            throw new ItemBadRequestException("El ID del item no coincide con el ID proporcionado");
        }

        return ResponseEntity.ok(
            itemService.findById(id)
                .map(e -> {
                    itemDTO.setIdItem(id);
                    return itemService.save(itemDTO);
                })
                .orElseThrow(() -> new ItemNotFoundException(id))
        );
    }


    // ELIMINAR ITEM
    @Operation(summary = "Eliminar un item", description = "Elimina un item existente con el ID proporcionado")

    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Item eliminado con éxtito"),
        @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })

    @DeleteMapping("/eliminarItem/{id}")
    public ResponseEntity<?> eliminarItem(@PathVariable Integer id) {

        itemService.findById(id).orElseThrow(() -> new ItemNotFoundException(id));

        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }


    // BUSCAR ITEM POR PRUEBA
    @Operation(summary = "Buscar un item por su prueba", description = "Busca un item especifica mediante un ID de prueba proporcioando")

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Items encontrado con éxtito"),
        @ApiResponse(responseCode = "404", description = "Items no encontrados")
    })

    @GetMapping("/buscarItemPorPrueba/{idPrueba}")
    public ResponseEntity<List<ItemDTO>> buscarItemPorPrueba(@PathVariable Integer idPrueba) {

        try {
            List<ItemDTO> items = itemService.findByPruebaId(idPrueba);
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
