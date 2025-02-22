package com.viju.andaluciaskills.services.base;

import com.viju.andaluciaskills.DTO.UserDTO;
import java.util.List;
import java.util.Optional;

public interface UserBaseService {
    UserDTO save(UserDTO dto);
    Optional<UserDTO> findById(Integer id);
    List<UserDTO> findAll();
    UserDTO update(UserDTO dto);
    void delete(Integer id);
}