package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.LikeDto;
import org.dyache.Javgemu.dto.CountResponseDto;
import org.dyache.Javgemu.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;


    private String mockCurrentUserEmail() {
        return "test@example.com";
    }

    @PostMapping("/")
    public ResponseEntity<LikeDto> addLike(@RequestBody LikeDto dto) {
        return ResponseEntity.ok(likeService.addLike(mockCurrentUserEmail(), dto));
    }

    @GetMapping("/{reviewId}/likes")
    public ResponseEntity<List<String>> getLikes(@PathVariable Long reviewId) {
        return ResponseEntity.ok(likeService.getLikesForReview(reviewId));
    }

    @GetMapping("/{reviewId}/likes/count")
    public ResponseEntity<CountResponseDto> getLikeCount(@PathVariable Long reviewId) {
        return ResponseEntity.ok(likeService.countLikes(reviewId));
    }

    @DeleteMapping("/{reviewId}/likes")
    public ResponseEntity<Void> deleteLike(@PathVariable Long reviewId) {
        likeService.deleteLike(mockCurrentUserEmail(), reviewId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/toggle")
    public ResponseEntity<LikeDto> toggleLike(@RequestBody LikeDto dto) {
        return ResponseEntity.ok(likeService.toggleLike(mockCurrentUserEmail(), dto));
    }
}
