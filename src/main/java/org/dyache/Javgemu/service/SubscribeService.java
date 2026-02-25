package org.dyache.Javgemu.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.UserOutDto;
import org.dyache.Javgemu.entity.SubscribeEntity;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.exception.NotFoundException;
import org.dyache.Javgemu.repository.SubscribeRepository;
import org.dyache.Javgemu.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        boolean exists = subscribeRepository
                .existsBySubscriber_IdAndTarget_Id(subscriberId, targetUserId);

        if (exists) {
            return; // уже подписан — молча выходим
        }

        UserEntity subscriber = userRepository.findById(subscriberId)
                .orElseThrow();

        UserEntity target = userRepository.findById(targetUserId)
                .orElseThrow();

        SubscribeEntity subscribe = SubscribeEntity.builder()
                .subscriber(subscriber)
                .target(target)
                .build();

        subscribeRepository.save(subscribe);
    }
}