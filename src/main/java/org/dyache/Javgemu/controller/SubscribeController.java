package org.dyache.Javgemu.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.SubscriptionDto;
import org.dyache.Javgemu.service.SubscribeService;
import org.dyache.Javgemu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscribeController {
   private final UserService userService;
   private final SubscribeService subscribeService;

   @PostMapping("/{userId}/")
   void Subscribe(
           @PathVariable Long userId,
           @AuthenticationPrincipal(expression = "email") String followerEmail
   ){
   Long followerId = userService.getUserByEmail(followerEmail).getId();
   subscribeService.subscribe(userId, followerId);
   }
}
