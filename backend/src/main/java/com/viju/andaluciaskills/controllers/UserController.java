package com.viju.andaluciaskills.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import com.viju.andaluciaskills.services.UserService;
import com.viju.andaluciaskills.DTO.UserDTO;
import com.viju.andaluciaskills.exceptions.user.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para la gestión de usuarios y expertos")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(
        summary = "Listar todos los usuarios",
        description = "Obtiene una lista de todos los usuarios registrados en el sistema"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios recuperada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron usuarios")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> result = userService.findAll();
        if (result.isEmpty()) { 
            throw new SearchUserNoResultException();
        }
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "Buscar usuario por ID",
        description = "Recupera un usuario específico mediante su ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/BuscasUser/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(
            userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException())
        );
    }

    @Operation(
        summary = "Crear nuevo usuario",
        description = "Crea un nuevo usuario con los datos proporcionados"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Usuario creado correctamente",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de usuario inválidos o ID ya existente"
        )
    })
    @PostMapping("/CrearUser")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto) {
        if (dto.getIdUser() != null) {
            throw new UserBadRequestException("No se puede crear un participante con un ID ya existente");
        }
        // Encriptar la contraseña antes de guardar
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.save(dto));
    }

    @PutMapping("/ModificarUser/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Integer id, 
            @RequestBody UserDTO dto) {
        
        if (!id.equals(dto.getIdUser())) {
            throw new UserBadRequestException("El ID de la ruta no coincide con el ID del objeto");
        }
        
        return ResponseEntity.ok(
            userService.findById(id)
                .map(e -> {
                    dto.setIdUser(id);
                    return userService.save(dto);
                })
                .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    @DeleteMapping("/BorrarUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userService.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
        userService.delete(id);
        return ResponseEntity.noContent().build();
    } 

    @Operation(
        summary = "Listar todos los expertos",
        description = "Obtiene una lista de todos los expertos registrados"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de expertos recuperada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontraron expertos")
    })
    @GetMapping("/BuscarExpertos")
    public ResponseEntity<List<UserDTO>> getExpertos() {
        List<UserDTO> result = userService.findAllExpertos();
        if (result.isEmpty()) { 
            throw new SearchUserNoResultException();
        }
        return ResponseEntity.ok(result);
    }

    @Operation(
        summary = "Buscar experto por ID",
        description = "Recupera un experto específico mediante su ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Experto encontrado"),
        @ApiResponse(responseCode = "404", description = "Experto no encontrado")
    })
    @GetMapping("/BuscarExperto/{id}")
    public ResponseEntity<UserDTO> getExperto(@PathVariable Integer id) {
        return ResponseEntity.ok(
            userService.findExpertoById(id)
                .orElseThrow(() -> new UserNotFoundException())
        );
    }

    @Operation(
        summary = "Crear nuevo experto",
        description = "Crea un nuevo experto con los datos proporcionados"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Experto creado correctamente",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos inválidos o contraseña vacía"
        )
    })
    @PostMapping("/CrearExperto")
    public ResponseEntity<UserDTO> createExperto(@Valid @RequestBody UserDTO dto) {
        System.out.println("Datos recibidos en el backend:");
        System.out.println("Username: " + dto.getUsername());
        System.out.println("Password (sin hashear): " + dto.getPassword());
        System.out.println("Nombre: " + dto.getNombre());
        System.out.println("Role: " + dto.getRole());
        System.out.println("Especialidad ID: " + dto.getEspecialidad());
        
        if (dto.getIdUser() != null) {
            throw new UserBadRequestException("No se puede crear un experto con un ID ya existente");
        }
        
        // Verificar que la contraseña no está vacía
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new UserBadRequestException("La contraseña no puede estar vacía");
        }

        // NO hashear aquí la contraseña, dejar que lo haga el servicio
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.save(dto));
    }

    @PutMapping("/ModificarExperto/{id}")
    public ResponseEntity<UserDTO> updateExperto(
            @PathVariable Integer id, 
            @RequestBody UserDTO dto) {
        
        if (!id.equals(dto.getIdUser())) {
            throw new UserBadRequestException("El ID de la ruta no coincide con el ID del objeto");
        }
        
        return ResponseEntity.ok(
            userService.findById(id)
                .map(e -> {
                    dto.setIdUser(id);
                    return userService.save(dto);
                })
                .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    @DeleteMapping("/BorrarExperto/{id}")
    public ResponseEntity<?> deleteExperto(@PathVariable Integer id) {
        userService.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
