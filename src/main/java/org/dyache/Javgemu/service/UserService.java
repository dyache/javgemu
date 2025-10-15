package org.dyache.Javgemu.service;


import org.dyache.Javgemu.dto.UserOutDto;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.exception.NotFoundException;
import org.dyache.Javgemu.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserOutDto> getAllUsers() {
        return userRepo.findAll().stream()
                .map(UserOutDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteUserByEmail(String email) {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        userRepo.delete(user);
    }

    public UserOutDto getUserProfile(String nickname) {
        UserEntity user = userRepo.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return UserOutDto.fromEntity(user);
    }
}
