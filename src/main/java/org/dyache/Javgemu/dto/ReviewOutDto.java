package org.dyache.Javgemu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dyache.Javgemu.entity.ReviewEntity;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOutDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private long likesCount;
    private long dislikesCount;
    private long commentsCount;
    private BigDecimal ratingStars;


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
        return dto;
    }
}
