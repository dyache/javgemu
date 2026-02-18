package org.dyache.Javgemu.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReviewUpdateDto {
    private String title;
    private String content;
    private BigDecimal ratingStars;
}
