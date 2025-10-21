package org.dyache.Javgemu.service;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.DislikeDto;
import org.dyache.Javgemu.dto.CountResponseDto;
import org.dyache.Javgemu.entity.DislikeEntity;
import org.dyache.Javgemu.entity.ReviewEntity;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.repository.DislikeRepository;
import org.dyache.Javgemu.repository.LikeRepository;
import org.dyache.Javgemu.repository.ReviewRepository;
import org.dyache.Javgemu.repository.UserRepository;
import org.dyache.Javgemu.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DislikeService {

    private final DislikeRepository dislikeRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public DislikeDto addDislike(String userEmail, DislikeDto dto) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
        ReviewEntity review = reviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new NotFoundException("Review not found"));


        likeRepository.findByUserIdAndReviewId(user.getId(), review.getId())
                .ifPresent(likeRepository::delete);


        if (dislikeRepository.existsByUserIdAndReviewId(user.getId(), review.getId())) {
            throw new IllegalStateException("Dislike already exists");
        }

        DislikeEntity dislike = DislikeEntity.builder()
                .user(user)
                .review(review)
                .build();
        dislikeRepository.save(dislike);
        return new DislikeDto(review.getId());
    }

    @Transactional
    public void deleteDislike(String userEmail, Long reviewId) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
        DislikeEntity dislike = dislikeRepository.findByUserIdAndReviewId(user.getId(), reviewId)
                .orElseThrow(() -> new NotFoundException("Dislike not found"));

        dislikeRepository.delete(dislike);
    }

    public List<String> getDislikesForReview(Long reviewId) {
        return dislikeRepository.findUserEmailsByReviewId(reviewId);
    }

    public CountResponseDto countDislikes(Long reviewId) {
        long count = dislikeRepository.countByReviewId(reviewId);
        return new CountResponseDto(count);
    }

    @Transactional
    public DislikeDto toggleDislike(String userEmail, DislikeDto dto) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
        ReviewEntity review = reviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new NotFoundException("Review not found"));


        likeRepository.findByUserIdAndReviewId(user.getId(), review.getId())
                .ifPresent(likeRepository::delete);

        var existing = dislikeRepository.findByUserIdAndReviewId(user.getId(), review.getId());
        if (existing.isPresent()) {
            dislikeRepository.delete(existing.get());
            throw new IllegalStateException("Dislike removed");
        }

        DislikeEntity dislike = DislikeEntity.builder().user(user).review(review).build();
        dislikeRepository.save(dislike);
        return new DislikeDto(review.getId());
    }
}
