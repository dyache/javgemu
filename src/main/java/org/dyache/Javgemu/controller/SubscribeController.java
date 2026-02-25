package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.ReviewOutDto;
import org.dyache.Javgemu.service.ReviewService;
import org.dyache.Javgemu.service.SubscribeService;
import org.dyache.Javgemu.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscribeController {
    private final UserService userService;
    private final SubscribeService subscribeService;
    private final ReviewService reviewService;

    @GetMapping("/subscriptions")
    public List<ReviewOutDto> getReviewsFromSubscriptions(@AuthenticationPrincipal(expression = "email") String email) {
        Long userId = userService.getUserByEmail(email).getId();
        return reviewService.getReviewsFromSubscribedUsers(userId);
    }
}
