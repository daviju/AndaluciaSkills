package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.User;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Encontrar todos los Usuarios con un ROLE
    List<User> findByRole(String role);

    /*
     * Usamos OPTIONAL porque la búsqueda puede no devolver resultados
     * Así evitamos el NullPointerException
     */

    // Encontrar un Usuario por su id y su ROLE
    Optional<User> findByIdUserAndRole(Integer id, String role);

    // Encontrar un Usuario por su nombre y apellidos
    Optional<User> findByNombreAndApellidos(String nombre, String apellidos);

    // Encontrar un Usuario por su username
    Optional<User> findByUsername(String username);
}
