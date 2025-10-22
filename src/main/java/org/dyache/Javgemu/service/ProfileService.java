package org.dyache.Javgemu.service;

import org.dyache.Javgemu.dto.ProfileResponseDto;
import org.dyache.Javgemu.dto.ProfileUpdateDto;
import org.dyache.Javgemu.dto.ReviewOutDto;
import org.dyache.Javgemu.dto.UserOutDto;
import org.dyache.Javgemu.entity.ReviewEntity;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.exception.NotFoundException;
import org.dyache.Javgemu.repository.ReviewRepository;
import org.dyache.Javgemu.repository.UserRepository;
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


        List<ReviewEntity> reviews = reviewRepository.findByUser_UsernameOrderByIdDesc(user.getUsername());

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
            user.setUsername(data.getNickname());
        }
        if (data.getBio() != null) {
            user.setBio(data.getBio());
        }

        userRepository.save(user);
    }
}
