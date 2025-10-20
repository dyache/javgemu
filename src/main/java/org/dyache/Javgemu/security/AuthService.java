package org.dyache.Javgemu.security;


import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.*;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.repository.UserRepository;
import org.dyache.Javgemu.service.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public TokenResponse register(RegisterRequest request) {
        if (request.getNickname() == null || request.getNickname().isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Nickname обязателен при регистрации");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Пользователь уже существует");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(hashedPassword)
                .nickname(request.getNickname())
                .isAdmin(request.isAdmin())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.isAdmin());
        return new TokenResponse(token, "bearer");
    }

    public TokenResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Пользователь не найден"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(UNAUTHORIZED, "Неверный пароль");
        }

        String token = jwtService.generateToken(user.getEmail(), user.isAdmin());
        return new TokenResponse(token, "bearer");
    }
}
