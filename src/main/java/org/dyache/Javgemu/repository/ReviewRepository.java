package org.dyache.Javgemu.repository;

import org.dyache.Javgemu.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByOrderByIdDesc();

    List<ReviewEntity> findByUser_Username(String nickname);

    List<ReviewEntity> findByUser_UsernameOrderByIdDesc(String username);

    @Query("""
        SELECT r
        FROM ReviewEntity r
        WHERE r.user.id IN :userIds
        ORDER BY r.createdAt DESC
    """)
    List<ReviewEntity> findReviewsByUserIds(@Param("userIds") List<Long> userIds);
}
