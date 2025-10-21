package org.dyache.Javgemu.repository;

import org.dyache.Javgemu.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

    Optional<LikeEntity> findByUserIdAndReviewId(Long userId, Long reviewId);

    long countByReviewId(Long reviewId);

    @Query("SELECT l.user.email FROM Like l WHERE l.review.id = :reviewId")
    List<String> findUserEmailsByReviewId(Long reviewId);
}
