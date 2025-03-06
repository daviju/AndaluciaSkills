package com.viju.andaluciaskills.mapper;

import com.viju.andaluciaskills.DTO.UserDTO;
import com.viju.andaluciaskills.entity.Especialidad;
import com.viju.andaluciaskills.entity.User;
import com.viju.andaluciaskills.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Anotación para indicar que esta clase es un componente
public class UserMapper implements GenericMapper<User, UserDTO> {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public UserDTO toDto(User entity) {
        if (entity == null) return null;

        UserDTO dto = new UserDTO();
        
        dto.setIdUser(entity.getIdUser());
        dto.setRole(entity.getRole());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setDni(entity.getDni());
        
        if (entity.getEspecialidad() != null) {
            dto.setEspecialidad(entity.getEspecialidad().getIdEspecialidad());
            dto.setNombreEspecialidad(entity.getEspecialidad().getNombre());
        }
        
        return dto;
    }

    @Override
    public User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User entity = new User();
        
        entity.setIdUser(dto.getIdUser());
        entity.setRole(dto.getRole());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setNombre(dto.getNombre());
        entity.setApellidos(dto.getApellidos());
        entity.setDni(dto.getDni());

        if (dto.getEspecialidad() != null) {
            Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidad()).orElse(null);
            
            entity.setEspecialidad(especialidad);
        }

        return entity;
    }
}