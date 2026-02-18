package org.dyache.Javgemu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dyache.Javgemu.entity.ReviewEntity;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOutDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("likes_count")
    private long likesCount;

    @JsonProperty("dislikes_count")
    private long dislikesCount;

    @JsonProperty("comments_count")
    private long commentsCount;
    private BigDecimal ratingStars;


    @JsonProperty("created_at")
    private String createdAt; // 👈 Add this if your entity has a createdAt field

    public static ReviewOutDto fromEntity(ReviewEntity review) {
        ReviewOutDto dto = new ReviewOutDto();
        dto.setId(review.getId());
        dto.setTitle(review.getTitle());
        dto.setContent(review.getContent());
        dto.setRatingStars(review.getRatingStars());
        dto.setNickname(
                review.getUser() != null ? review.getUser().getUsername() : "Аноним"
        );
        dto.setLikesCount(review.getLikes() != null ? review.getLikes().size() : 0);
        dto.setDislikesCount(review.getDislikes() != null ? review.getDislikes().size() : 0);
        dto.setCommentsCount(review.getComments() != null ? review.getComments().size() : 0);
        dto.setCreatedAt(
                review.getCreatedAt() != null ? review.getCreatedAt().toString() : null
        );
        return dto;
    }
}