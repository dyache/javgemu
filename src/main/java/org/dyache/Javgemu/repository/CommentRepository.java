package org.dyache.Javgemu.repository;

import org.dyache.Javgemu.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByReviewIdOrderByIdAsc(Long reviewId);
}
