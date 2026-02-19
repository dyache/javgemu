package org.dyache.Javgemu.service;

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
    public void subscribe(Long userId, Long subscriberId){

        UserEntity subscriber = userRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        SubscribeEntity sub = new SubscribeEntity();
        sub.setSubscriber(subscriber);
        sub.setUserId(user);
        subscribeRepository.save(sub);

    }

    public List<Long> getSubscribedUserIds(Long subscriberId) {
        return subscribeRepository.findTargetUserIdsBySubscriberId(subscriberId);
    }

}
