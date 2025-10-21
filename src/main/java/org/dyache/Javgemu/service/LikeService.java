package org.dyache.Javgemu.service;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.LikeDto;
import org.dyache.Javgemu.dto.CountResponseDto;
import org.dyache.Javgemu.entity.LikeEntity;
import org.dyache.Javgemu.entity.ReviewEntity;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.repository.LikeRepository;
import org.dyache.Javgemu.repository.ReviewRepository;
import org.dyache.Javgemu.repository.UserRepository;
import org.dyache.Javgemu.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public LikeDto addLike(String userEmail, LikeDto dto) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));

        ReviewEntity review = reviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new NotFoundException("Review not found"));

        if (likeRepository.existsByUserIdAndReviewId(user.getId(), review.getId())) {
            throw new IllegalStateException("Like already exists");
        }

        LikeEntity like = LikeEntity.builder()
                .user(user)
                .review(review)
                .build();

        likeRepository.save(like);
        return new LikeDto(review.getId());
    }

    @Transactional
    public void deleteLike(String userEmail, Long reviewId) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));

        LikeEntity like = likeRepository.findByUserIdAndReviewId(user.getId(), reviewId)
                .orElseThrow(() -> new NotFoundException("Like not found"));

        likeRepository.delete(like);
    }

    public List<String> getLikesForReview(Long reviewId) {
        return likeRepository.findUserEmailsByReviewId(reviewId);
    }

    public CountResponseDto countLikes(Long reviewId) {
        long count = likeRepository.countByReviewId(reviewId);
        return new CountResponseDto(count);
    }

    @Transactional
    public LikeDto toggleLike(String userEmail, LikeDto dto) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
        ReviewEntity review = reviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new NotFoundException("Review not found"));

        var existing = likeRepository.findByUserIdAndReviewId(user.getId(), review.getId());
        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            throw new IllegalStateException("Like removed");
        }

        LikeEntity like = LikeEntity.builder().user(user).review(review).build();
        likeRepository.save(like);
        return new LikeDto(review.getId());
    }
}
