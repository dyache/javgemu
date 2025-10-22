package org.dyache.Javgemu.repository;

import org.dyache.Javgemu.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByOrderByIdDesc();

    List<ReviewEntity> findByUser_Username(String nickname);

    List<ReviewEntity> findByUser_UsernameOrderByIdDesc(String username);
}
