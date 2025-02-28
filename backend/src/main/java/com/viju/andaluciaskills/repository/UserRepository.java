package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.User;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Incluimos findByUsername para poder buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
    List<User> findByRole(String role);
    Optional<User> findByIdUserAndRole(Integer id, String role);
    Optional<User> findByNombreAndApellidos(String nombre, String apellidos);
}
