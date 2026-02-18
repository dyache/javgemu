package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.CommentDto;
import org.dyache.Javgemu.dto.CommentCreateDto;
import org.dyache.Javgemu.dto.CommentUpdateDto;
import org.dyache.Javgemu.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Получить комментарии к обзору
    @GetMapping("/{reviewId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long reviewId) {
        return ResponseEntity.ok(commentService.getComments(reviewId));
    }

    // Добавить комментарий
    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<CommentDto> addComment(
            @PathVariable Long reviewId,
            @RequestBody CommentCreateDto dto,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        return ResponseEntity.ok(commentService.addComment(email, reviewId, dto));
    }

    // Удалить комментарий
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        commentService.deleteComment(email, commentId);
        return ResponseEntity.ok().body("Комментарий " + commentId + " удалён");
    }

    // Обновить комментарий
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDto dto,
            @AuthenticationPrincipal(expression = "email") String email
    ) {
        return ResponseEntity.ok(commentService.updateComment(email, commentId, dto));
    }
}
