package org.dyache.Javgemu.service;

import org.dyache.Javgemu.dto.*;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.entity.ReviewEntity;
import org.dyache.Javgemu.exception.NotFoundException;
import org.dyache.Javgemu.repository.UserRepository;
import org.dyache.Javgemu.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ProfileService(UserRepository userRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }


    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        List<ReviewEntity> reviews = reviewRepository.findByUserNicknameOrderByIdDesc(user.getNickname());
        List<ReviewOutDto> reviewDtos = reviews.stream()
                .map(ReviewOutDto::fromEntity)
                .collect(Collectors.toList());

        return new ProfileResponseDto(UserOutDto.fromEntity(user), reviewDtos);
    }


    @Transactional
    public void updateProfile(String email, ProfileUpdateDto data) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (data.getNickname() != null && !data.getNickname().isBlank()) {
            user.setNickname(data.getNickname());
        }
        if (data.getBio() != null) {
            user.setBio(data.getBio());
        }

        userRepository.save(user);
    }
}
