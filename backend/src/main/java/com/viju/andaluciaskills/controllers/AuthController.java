package com.viju.andaluciaskills.controllers;

import com.viju.andaluciaskills.DTO.AuthRequestDTO;
import com.viju.andaluciaskills.DTO.AuthResponseDTO;
import com.viju.andaluciaskills.DTO.UserRegisterDTO;
import com.viju.andaluciaskills.entity.User;
import com.viju.andaluciaskills.services.UserService;
import com.viju.andaluciaskills.security.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "API para la gestión de autenticación de usuarios")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Operation(
        summary = "Iniciar sesión de usuario",
        description = "Permite a un usuario iniciar sesión usando sus credenciales y devuelve un token JWT"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Inicio de sesión exitoso",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Credenciales inválidas"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"
        )
    })

    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            User user = userService.findByUsername(authRequestDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
                
            boolean matches = passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword());
            System.out.println("¿Coinciden las contraseñas?: " + matches);
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );
            
            String token = jwtTokenProvider.generateToken(authentication);
            
            // Ajusta esto según los campos disponibles en tu clase User y AuthResponseDTO
            return ResponseEntity.ok(new AuthResponseDTO(
                token, 
                user.getUsername(), 
                user.getRole(), 
                user.getApellidos(), 
                user.getNombre(), 
                user.getEspecialidadId(), 
                user.getIdUser()
            ));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                .body(new AuthResponseDTO(null, null, null, null, null, null, null)); // Devuelve un token nulo
        }
    }

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un nuevo usuario en el sistema con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuario registrado correctamente"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de registro inválidos o usuario ya existe",
            content = @Content(schema = @Schema(implementation = String.class))
        )
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            userService.registerUser(userRegisterDTO);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}