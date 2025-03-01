package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.UserDTO;
import com.viju.andaluciaskills.DTO.UserRegisterDTO;
import com.viju.andaluciaskills.entity.Especialidad;
import com.viju.andaluciaskills.entity.User;
import com.viju.andaluciaskills.repository.EspecialidadRepository;
import com.viju.andaluciaskills.repository.UserRepository;
import com.viju.andaluciaskills.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // SAVE
    // Metodo para guardar un usuario en la BD
    public UserDTO save(UserDTO dto) {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) { // Comprobamos si el usuario ya existe en la BD
            throw new IllegalArgumentException("El usuario ya existe");
        }

        if (dto.getPassword() != null && !dto.getPassword().startsWith("$2a$")) { // Comprobamos si la contraseña no está encriptada
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            System.out.println("Password encriptado");
        }

        User user = userMapper.toEntity(dto); // Convertimos el DTO a Entity
        User savedUser = userRepository.save(user); // Guardamos el usuario en la BD
        return userMapper.toDto(savedUser); // Convertimos el Entity a DTO y lo devolvemos
    }


    // FIND BY ID
    // Metodo para buscar un usuario por su ID
    public Optional<UserDTO> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    // FIND BY USERNAME
    // Metodo para buscar un usuario por su username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // FIND ALL
    // Metodo para buscar todos los usuarios
    public List<UserDTO> findAll() {
        return userMapper.toDtoList(userRepository.findAll()); // Convertimos la lista de Entity a DTO con el Mapper y la devolvemos 
    }

    // FIND ALL EXPERTOS
    // Metodo para buscar todos los usuarios con el rol de experto
    public List<UserDTO> findAllExpertos() {
        return userMapper.toDtoList(userRepository.findByRole("ROLE_EXPERTO"));
    }

    // FIND EXPERTO BY ID
    // Metodo para buscar un experto por su ID
    public Optional<UserDTO> findExpertoById(Integer id) {
        return userRepository.findByIdUserAndRole(id, "ROLE_EXPERTO")
                .map(userMapper::toDto);
    }


    // UPDATE
    // Metodo para actualizar un usuario
    public UserDTO update(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }


    // DELETE
    // Metodo para eliminar un usuario por su ID
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }


    // REGISTRAR USUARIO
    // Metodo para registrar un nuevo usuario
    public User registerUser(UserRegisterDTO dto) {
        // Verificamos si el usuario ya existe
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        // Verificamos que la contraseña no esté vacía
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        // Log para debugging y control
        System.out.println("Contraseña a hashear: '" + dto.getPassword() + "'");
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        System.out.println("Hash generado: " + hashedPassword);

        // Buscar la especialidad
        Especialidad especialidad = especialidadRepository
            .findById(dto.getEspecialidad_idEspecialidad())
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        // Creamos un usuario nuevo
        User user = new User();
        user.setUsername(dto.getUsername()); // Asignamos el Username del DTO al usuario
        user.setPassword(hashedPassword); // Asignamos la contraseña hasheada
        user.setNombre(dto.getNombre()); // Asignamos el nombre del DTO al usuario
        user.setApellidos(dto.getApellidos()); // Asignamos los apellidos del DTO al usuario
        user.setDni(dto.getDni()); // Asignamos el DNI del DTO al usuario
        user.setRole(dto.getRole()); // Asignamos el rol del DTO al usuario
        user.setEspecialidad(especialidad); // Asignamos la especialidad al usuario

        return userRepository.save(user); // Guardamos el usuario en la BD
    }
}