package org.dyache.Javgemu.service;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.ReviewCreateDto;
import org.dyache.Javgemu.dto.ReviewOutDto;
import org.dyache.Javgemu.dto.ReviewUpdateDto;
import org.dyache.Javgemu.entity.ReviewEntity;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.exception.NotFoundException;
import org.dyache.Javgemu.repository.ReviewRepository;
import org.dyache.Javgemu.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SubscribeService subscribeService;

    public List<ReviewOutDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewOutDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ReviewOutDto getReviewById(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Обзор не найден"));
        return ReviewOutDto.fromEntity(review);
    }

    public ReviewOutDto createReview(ReviewCreateDto dto, String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        ReviewEntity review = new ReviewEntity();
        review.setTitle(dto.getTitle());
        review.setContent(dto.getContent());
        review.setCreatedAt(LocalDateTime.now());
        review.setUser(user);

        // NEW: rating (0..5, halves allowed)
        review.setRatingStars(normalizeHalfStar(dto.getRatingStars()));

        reviewRepository.save(review);
        return ReviewOutDto.fromEntity(review);
    }

    public ReviewOutDto updateReview(Long id, ReviewUpdateDto dto, String userEmail) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Обзор не найден"));

        if (!review.getUser().getEmail().equals(userEmail)) {
            throw new NotFoundException("Вы не автор этого обзора");
        }

        if (dto.getTitle() != null) review.setTitle(dto.getTitle());
        if (dto.getContent() != null) review.setContent(dto.getContent());

        // NEW: update rating if provided
        if (dto.getRatingStars() != null) {
            review.setRatingStars(normalizeHalfStar(dto.getRatingStars()));
        }

        reviewRepository.save(review);
        return ReviewOutDto.fromEntity(review);
    }

    public void deleteReview(Long id, String userEmail) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Обзор не найден"));

        if (!review.getUser().getEmail().equals(userEmail)) {
            throw new NotFoundException("Вы не автор этого обзора");
        }

        reviewRepository.delete(review);
    }

    public List<ReviewOutDto> getReviewsByUsername(String nickname) {
        if (!userRepository.existsByUsername(nickname)) {
            throw new RuntimeException("User not found: " + nickname);
        }

        return reviewRepository.findByUser_Username(nickname)
                .stream()
                .map(ReviewOutDto::fromEntity)
                .toList();
    }

    private BigDecimal normalizeHalfStar(BigDecimal v) {
        if (v == null) return null;

        BigDecimal min = BigDecimal.ZERO;
        BigDecimal max = new BigDecimal("5.0");

        if (v.compareTo(min) < 0) v = min;
        if (v.compareTo(max) > 0) v = max;

        // round to nearest 0.5
        return v.multiply(BigDecimal.valueOf(2))
                .setScale(0, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(2), 1, RoundingMode.UNNECESSARY);
    }


    public List<ReviewEntity> getReviewsFromSubscribedUsers(Long subscriberId) {
        List<Long> subscribedUserIds =
                subscribeService.getSubscribedUserIds(subscriberId);

        if (subscribedUserIds.isEmpty()) {
            return List.of();
        }

        return reviewRepository.findReviewsByUserIds(subscribedUserIds);
    }
}
