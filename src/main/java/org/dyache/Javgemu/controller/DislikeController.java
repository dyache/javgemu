package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.DislikeDto;
import org.dyache.Javgemu.dto.CountResponseDto;
import org.dyache.Javgemu.service.DislikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dislikes")
@RequiredArgsConstructor
public class DislikeController {

    private final DislikeService dislikeService;

    // TODO: заменить после внедрения JWT
    private String mockCurrentUserEmail() {
        return "test@example.com";
    }

    @PostMapping("/")
    public ResponseEntity<DislikeDto> addDislike(@RequestBody DislikeDto dto) {
        return ResponseEntity.ok(dislikeService.addDislike(mockCurrentUserEmail(), dto));
    }

    @GetMapping("/{reviewId}/dislikes")
    public ResponseEntity<List<String>> getDislikes(@PathVariable Long reviewId) {
        return ResponseEntity.ok(dislikeService.getDislikesForReview(reviewId));
    }

    @GetMapping("/{reviewId}/dislikes/count")
    public ResponseEntity<CountResponseDto> getDislikeCount(@PathVariable Long reviewId) {
        return ResponseEntity.ok(dislikeService.countDislikes(reviewId));
    }

    @DeleteMapping("/{reviewId}/dislikes")
    public ResponseEntity<Void> deleteDislike(@PathVariable Long reviewId) {
        dislikeService.deleteDislike(mockCurrentUserEmail(), reviewId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/toggle")
    public ResponseEntity<DislikeDto> toggleDislike(@RequestBody DislikeDto dto) {
        return ResponseEntity.ok(dislikeService.toggleDislike(mockCurrentUserEmail(), dto));
    }
}
