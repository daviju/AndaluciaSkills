package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.UserDTO;
import com.viju.andaluciaskills.entity.User;
import com.viju.andaluciaskills.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserBaseService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO save(UserDTO dto) {
        User user = convertToEntity(dto);
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public Optional<UserDTO> findById(Integer id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(UserDTO dto) {
        User user = convertToEntity(dto);
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}