package org.dyache.Javgemu.service;

import lombok.RequiredArgsConstructor;
import org.dyache.Javgemu.dto.CommentCreateDto;
import org.dyache.Javgemu.dto.CommentDto;
import org.dyache.Javgemu.dto.CommentUpdateDto;
import org.dyache.Javgemu.entity.CommentEntity;
import org.dyache.Javgemu.entity.ReviewEntity;
import org.dyache.Javgemu.entity.UserEntity;
import org.dyache.Javgemu.exception.ForbiddenException;
import org.dyache.Javgemu.exception.NotFoundException;
import org.dyache.Javgemu.repository.CommentRepository;
import org.dyache.Javgemu.repository.ReviewRepository;
import org.dyache.Javgemu.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public List<CommentDto> getComments(Long reviewId) {
        return commentRepository.findByReviewIdOrderByIdAsc(reviewId).stream().map(this::toDto).toList();
    }

    @Transactional
    public CommentDto addComment(String userEmail, Long reviewId, CommentCreateDto dto) {
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("Review not found"));

        String nickname = (user.getUsername() != null) ? user.getUsername() : "Аноним";

        CommentEntity comment = CommentEntity.builder().review(review).authorNickname(nickname).content(dto.getContent()).createdAt(LocalDateTime.now()).build();

        commentRepository.save(comment);
        return toDto(comment);
    }

    @Transactional
    public void deleteComment(String userEmail, Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий не найден"));

        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ForbiddenException("Пользователь не найден"));

        boolean isAuthor = comment.getAuthorNickname().equals(user.getUsername());

        if (!isAuthor) {
            throw new ForbiddenException("Недостаточно прав для удаления");
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public CommentDto updateComment(String userEmail, Long commentId, CommentUpdateDto dto) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий не найден"));

        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ForbiddenException("Пользователь не найден"));

        if (!comment.getAuthorNickname().equals(user.getUsername())) {
            throw new ForbiddenException("Вы не автор комментария");
        }

        comment.setContent(dto.getContent());
        commentRepository.save(comment);
        return toDto(comment);
    }

    private CommentDto toDto(CommentEntity c) {
        return CommentDto.builder().id(c.getId()).authorNickname(c.getAuthorNickname()).content(c.getContent()).createdAt(c.getCreatedAt()).build();
    }
}
