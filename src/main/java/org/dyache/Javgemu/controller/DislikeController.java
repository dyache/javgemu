package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.CountResponseDto;
import org.dyache.Javgemu.dto.DislikeDto;
import org.dyache.Javgemu.service.DislikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dislikes")
@RequiredArgsConstructor
public class DislikeController {

    private final DislikeService dislikeService;

    // Добавить дизлайк
    @PostMapping
    public ResponseEntity<DislikeDto> addDislike(@RequestBody DislikeDto dto, @AuthenticationPrincipal(expression = "email") String email) {
        return ResponseEntity.ok(dislikeService.addDislike(email, dto));
    }

    // Получить список пользователей, поставивших дизлайк конкретному обзору
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<String>> getDislikes(@PathVariable Long reviewId) {
        return ResponseEntity.ok(dislikeService.getDislikesForReview(reviewId));
    }

    // Получить количество дизлайков для обзора
    @GetMapping("/{reviewId}/count")
    public ResponseEntity<CountResponseDto> getDislikeCount(@PathVariable Long reviewId) {
        return ResponseEntity.ok(dislikeService.countDislikes(reviewId));
    }

    // Удалить дизлайк пользователя
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteDislike(@PathVariable Long reviewId, @AuthenticationPrincipal(expression = "email") String email) {
        dislikeService.deleteDislike(email, reviewId);
        return ResponseEntity.ok().body("Дизлайк с обзора " + reviewId + " удалён");
    }

    // Переключить дизлайк (добавить, если нет / удалить, если есть)
    @PostMapping("/toggle")
    public ResponseEntity<DislikeDto> toggleDislike(@RequestBody DislikeDto dto, @AuthenticationPrincipal(expression = "email") String email) {
        return ResponseEntity.ok(dislikeService.toggleDislike(email, dto));
    }
}
