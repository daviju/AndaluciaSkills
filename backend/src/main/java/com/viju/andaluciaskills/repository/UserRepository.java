package com.viju.andaluciaskills.repository;

import com.viju.andaluciaskills.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Incluimos findByUsername para poder buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
}
