package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.DTO.UserDTO;
import com.viju.andaluciaskills.entity.User;
import com.viju.andaluciaskills.repository.UserRepository;
import com.viju.andaluciaskills.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;

    public UserDTO save(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    public Optional<UserDTO> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO update(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}