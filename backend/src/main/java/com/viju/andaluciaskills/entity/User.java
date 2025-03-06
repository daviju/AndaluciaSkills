package com.viju.andaluciaskills.entity;

import java.util.Collection;
import java.util.List;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data // Lombok annotation to generate all the getters, setters, equals, hash, and toString methods
@Entity // Esta clase esta manejada por JPA
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    

    @NotBlank(message = "El rol es obligatorio") // Verificamos que no sea null, que no este vacio y que al menos tenga un carácter
    @Column(nullable = false)
    private String role;


    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(nullable = false, unique = true)
    private String username;


    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false)
    private String password;


    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;


    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(nullable = false)
    private String apellidos;


    @NotBlank(message = "El DNI es obligatorio")
    // Aseguramos que el campo cumple con un formato especifico antes de guardar
    @Pattern( 
        regexp = "^[0-9]{8}[A-HJ-NP-TV-Z]$", // Expresión regular para validar el DNI
        message = "El DNI debe tener 8 números seguidos de una letra válida" // Mensaje de error si no se cumple la expresión regular
    )


    @Column(nullable = false, unique = true)
    private String dni;


    @ManyToOne 
    @JoinColumn(name = "especialidad_id_especialidad")
    private Especialidad especialidad;


    // Getters y Setters
    public Integer getEspecialidadId() {
        return especialidad != null ? especialidad.getIdEspecialidad() : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Devuelve los roles que tiene un usuario
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }   
}