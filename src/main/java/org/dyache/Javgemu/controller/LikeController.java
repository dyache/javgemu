package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.CountResponseDto;
import org.dyache.Javgemu.dto.LikeDto;
import org.dyache.Javgemu.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    private String mockCurrentUserEmail() {
        return "test@example.com";
    }
    @PostMapping
    public ResponseEntity<LikeDto> addLike(
            @RequestBody LikeDto dto,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        return ResponseEntity.ok(likeService.addLike(email, dto));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<List<String>> getLikes(@PathVariable Long reviewId) {
        return ResponseEntity.ok(likeService.getLikesForReview(reviewId));
    }

    @GetMapping("/{reviewId}/count")
    public ResponseEntity<CountResponseDto> getLikeCount(@PathVariable Long reviewId) {
        return ResponseEntity.ok(likeService.countLikes(reviewId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteLike(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        likeService.deleteLike(email, reviewId);
        return ResponseEntity.ok().body("Лайк с обзора " + reviewId + " удалён");
    }

    @PostMapping("/toggle")
    public ResponseEntity<LikeDto> toggleLike(
            @RequestBody LikeDto dto,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        return ResponseEntity.ok(likeService.toggleLike(email, dto));
    }
}
