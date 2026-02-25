package org.dyache.Javgemu.service;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.entity.SubscribeEntity;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.repository.SubscribeRepository;
import org.dyache.Javgemu.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void subscribe(Long targetUserId, Long subscriberId) {

        if (targetUserId.equals(subscriberId)) {
            throw new IllegalStateException("Нельзя подписаться на себя");
        }

        boolean exists = subscribeRepository.existsBySubscriber_IdAndTarget_Id(subscriberId, targetUserId);

        if (exists) {
            return; // уже подписан — молча выходим
        }

        UserEntity subscriber = userRepository.findById(subscriberId).orElseThrow();

        UserEntity target = userRepository.findById(targetUserId).orElseThrow();

        SubscribeEntity subscribe = SubscribeEntity.builder().subscriber(subscriber).target(target).build();

        subscribeRepository.save(subscribe);
    }

    @Transactional
    public void unsubscribe(Long targetUserId, Long subscriberId) {

        if (targetUserId.equals(subscriberId)) {
            throw new IllegalStateException("Нельзя отписаться от себя");
        }

        subscribeRepository
                .deleteBySubscriber_IdAndTarget_Id(subscriberId, targetUserId);
    }

    @Transactional(readOnly = true)
    public boolean isSubscribed(Long subscriberId, Long targetId) {
        return subscribeRepository.existsBySubscriber_IdAndTarget_Id(subscriberId, targetId);
    }
}