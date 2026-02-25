package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.ReviewOutDto;
import org.dyache.Javgemu.service.ReviewService;
import org.dyache.Javgemu.service.SubscribeService;
import org.dyache.Javgemu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscribeController {
    private final UserService userService;
    private final SubscribeService subscribeService;
    private final ReviewService reviewService;

    @PostMapping("/{targetUserId}/")
    public ResponseEntity<Void> subscribe(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        Long subscriberId = userService.getUserByEmail(email).getId();
        subscribeService.subscribe(targetUserId, subscriberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/subscriptions")
    public List<ReviewOutDto> getReviewsFromSubscriptions(@AuthenticationPrincipal(expression = "email") String email) {
        Long userId = userService.getUserByEmail(email).getId();
        return reviewService.getReviewsFromSubscribedUsers(userId);
    }

    @GetMapping("/status/{targetUserId}")
    public Map<String, Boolean> isSubscribed(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        Long subscriberId = userService.getUserByEmail(email).getId();
        boolean subscribed =
                subscribeService.isSubscribed(subscriberId, targetUserId);

        return Map.of("subscribed", subscribed);
    }

    @DeleteMapping("/{targetUserId}/")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        Long subscriberId = userService.getUserByEmail(email).getId();
        subscribeService.unsubscribe(targetUserId, subscriberId);
        return ResponseEntity.ok().build();
    }

}
