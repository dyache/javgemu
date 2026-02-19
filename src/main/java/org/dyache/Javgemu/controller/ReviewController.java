package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.ReviewCreateDto;
import org.dyache.Javgemu.dto.ReviewOutDto;
import org.dyache.Javgemu.dto.ReviewUpdateDto;
import org.dyache.Javgemu.entity.ReviewEntity;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.service.ReviewService;
import org.dyache.Javgemu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;



    @GetMapping
    public ResponseEntity<List<ReviewOutDto>> getAllReviews(
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReviewOutDto> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }


    @PostMapping
    public ResponseEntity<ReviewOutDto> createReview(@RequestBody ReviewCreateDto dto,
                                                     @AuthenticationPrincipal UserEntity currentUser) {
        System.out.println("maybe creating");
        return ResponseEntity.ok(reviewService.createReview(dto, currentUser.getEmail()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id,
                                          @AuthenticationPrincipal(expression = "email") String email) {
        reviewService.deleteReview(id, email);
        return ResponseEntity.ok().body("Обзор " + id + " удалён");
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ReviewOutDto> updateReview(@PathVariable Long id,
                                                     @RequestBody ReviewUpdateDto dto,
                                                     @AuthenticationPrincipal(expression = "email") String email) {
        return ResponseEntity.ok(reviewService.updateReview(id, dto, email));
    }


    @GetMapping("/by-nickname/{nickname}")
    public ResponseEntity<List<ReviewOutDto>> getReviewsByUsername(@PathVariable String nickname) {
        return ResponseEntity.ok(reviewService.getReviewsByUsername(nickname));
    }


    @GetMapping("/subscriptions")
    public List<ReviewEntity> getReviewsFromSubscriptions(
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        Long userId = userService.getUserByEmail(email).getId();
        return reviewService.getReviewsFromSubscribedUsers(userId);
    }
}
