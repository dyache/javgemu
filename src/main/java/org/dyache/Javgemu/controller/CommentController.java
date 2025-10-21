package org.dyache.Javgemu.controller;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.CommentDto;
import org.dyache.Javgemu.dto.CommentCreateDto;
import org.dyache.Javgemu.dto.CommentUpdateDto;
import org.dyache.Javgemu.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // TODO: заменить после внедрения JWT
    private String mockCurrentUserEmail() {
        return "test@example.com";
    }

    @GetMapping("/{reviewId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long reviewId) {
        return ResponseEntity.ok(commentService.getComments(reviewId));
    }

    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<CommentDto> addComment(
            @PathVariable Long reviewId,
            @RequestBody CommentCreateDto dto
    ) {
        return ResponseEntity.ok(commentService.addComment(mockCurrentUserEmail(), reviewId, dto));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(mockCurrentUserEmail(), commentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDto dto
    ) {
        return ResponseEntity.ok(commentService.updateComment(mockCurrentUserEmail(), commentId, dto));
    }
}
