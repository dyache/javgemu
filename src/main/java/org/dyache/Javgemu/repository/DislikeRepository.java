package org.dyache.Javgemu.repository;

import org.dyache.Javgemu.entity.DislikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface DislikeRepository extends JpaRepository<DislikeEntity, Long> {

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

    Optional<DislikeEntity> findByUserIdAndReviewId(Long userId, Long reviewId);

    long countByReviewId(Long reviewId);

    @Query("SELECT d.user.email FROM DislikeEntity d WHERE d.review.id = :reviewId")
    List<String> findUserEmailsByReviewId(Long reviewId);
}
