package org.dyache.Javgemu.repository;

import org.dyache.Javgemu.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long> {

    List<SubscribeEntity> findBySubscriber_Id(Long subscriberId);

    boolean existsBySubscriber_IdAndTarget_Id(Long subscriberId, Long targetId);

    void deleteBySubscriber_IdAndTarget_Id(Long subscriberId, Long targetId);


}
