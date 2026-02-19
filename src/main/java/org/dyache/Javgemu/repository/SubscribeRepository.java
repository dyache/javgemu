package org.dyache.Javgemu.repository;

import org.dyache.Javgemu.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long> {
    @Query("""
                SELECT s.target.id
                FROM SubscribeEntity s
                WHERE s.subscriber.id = :subscriberId
            """)
    List<Long> findTargetUserIdsBySubscriberId(@Param("subscriberId") Long subscriberId);

}
